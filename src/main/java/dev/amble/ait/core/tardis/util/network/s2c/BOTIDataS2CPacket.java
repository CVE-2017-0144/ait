package dev.amble.ait.core.tardis.util.network.s2c;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.blockentities.ExteriorBlockEntity;
import dev.amble.ait.core.tardis.Tardis;

public class BOTIDataS2CPacket implements FabricPacket {
    public static final PacketType<BOTIDataS2CPacket> TYPE = PacketType.create(AITMod.id("send_boti_data"), BOTIDataS2CPacket::new);

    private final BlockPos botiPos;
    public final CompoundTag chunkData;

    public BOTIDataS2CPacket(BlockPos botiPos, LevelChunk chunk, BlockPos targetPos) {
        this.botiPos = botiPos;
        this.chunkData = new CompoundTag();
        CompoundTag blockStates = new CompoundTag();
        CompoundTag blockEntities = new CompoundTag();
        Level world = chunk.getLevel();
        int targetY = targetPos.getY();
        int baseY = targetY & ~15;
        int sectionIndex = chunk.getSectionIndex(targetY);
        LevelChunkSection section = chunk.getSection(sectionIndex);
        ChunkPos chunkPos = chunk.getPos();

        try {
            List<BlockState> paletteList = new ArrayList<>();
            Map<BlockState, Integer> stateToIndex = new HashMap<>();
            paletteList.add(Blocks.AIR.defaultBlockState()); // Index 0 = air
            stateToIndex.put(Blocks.AIR.defaultBlockState(), 0);
            BlockState[][][] sectionStates = new BlockState[16][16][16];
            for (int y = 0; y < 16; y++) {
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        BlockState state = section.getBlockState(x, y, z);
                        sectionStates[x][y][z] = state;
                        if (state != null && !state.isAir() && !stateToIndex.containsKey(state)) {
                            stateToIndex.put(state, paletteList.size());
                            paletteList.add(state);
                        }
                    }
                }
            }

            // Build palette NBT
            ListTag palette = new ListTag();
            for (BlockState state : paletteList) {
                CompoundTag stateNbt = (CompoundTag) BlockState.CODEC.encodeStart(NbtOps.INSTANCE, state)
                        .result().orElseThrow(() -> new IllegalStateException("Failed to encode state " + state));
                palette.add(stateNbt);
            }

            int paletteSize = palette.size();
            int bitsPerEntry = Math.max(1, (int) Math.ceil(Math.log(paletteSize) / Math.log(2))); // Allow 1 bit for small palettes
            int entriesPerLong = 64 / bitsPerEntry;
            int dataLength = (int) Math.ceil(4096.0 / entriesPerLong); // 16x16x16 = 4096 entries

            // Build data array
            long[] data = new long[dataLength];
            for (int y = 0; y < 16; y++) {
                for (int z = 0; z < 16; z++) {
                    for (int x = 0; x < 16; x++) {
                        int index = y * 256 + z * 16 + x;
                        int longIndex = index / entriesPerLong;
                        int offset = (index % entriesPerLong) * bitsPerEntry;
                        BlockState state = sectionStates[x][y][z];
                        int paletteIndex = stateToIndex.getOrDefault(state, 0); // Default to air
                        data[longIndex] |= ((long) paletteIndex & ((1L << bitsPerEntry) - 1)) << offset;
                    }
                }
            }

            // Collect block entity data
            for (int y = 0; y < 16; y++) {
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        BlockPos worldPos = new BlockPos(chunkPos.getMinBlockX() + x, baseY + y, chunkPos.getMinBlockZ() + z);
                        BlockEntity be = chunk.getBlockEntity(worldPos);
                        if (be != null) {
                            CompoundTag blockEntityNbt = be.saveWithFullMetadata();
                            String key = x + "_" + y + "_" + z;
                            blockEntities.put(key, blockEntityNbt);
                        }
                    }
                }
            }

            blockStates.put("palette", palette);
            blockStates.putLongArray("data", data);
            blockStates.putInt("bitsPerEntry", bitsPerEntry);
            this.chunkData.put("block_states", blockStates);
            if (!blockEntities.isEmpty()) {
                this.chunkData.put("block_entities", blockEntities);
            }
        } catch (Exception e) {
            System.out.println("Exception in packet construction: " + e.getMessage());
            AITMod.LOGGER.atTrace();
            ListTag palette = new ListTag();
            palette.add(BlockState.CODEC.encodeStart(NbtOps.INSTANCE, Blocks.STONE.defaultBlockState())
                    .result().orElseThrow(() -> new IllegalStateException("Failed to encode stone state")));
            long[] fullData = new long[256];
            java.util.Arrays.fill(fullData, 0);
            blockStates.put("palette", palette);
            blockStates.putLongArray("data", fullData);
            System.out.println("Using fallback stone data due to serialization failure");
            this.chunkData.put("block_states", blockStates);
        }
    }
    public BOTIDataS2CPacket(BlockPos botiPos, CompoundTag chunkData) {
        this.botiPos = botiPos;
        this.chunkData = chunkData;
    }
    public BOTIDataS2CPacket(FriendlyByteBuf buf) {
        this.botiPos = buf.readBlockPos();
        this.chunkData = buf.readNbt();
    }
    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBlockPos(botiPos);
        buf.writeNbt(chunkData);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

    @SuppressWarnings("unchecked")
    public <T> boolean handle(LocalPlayer source, PacketSender response) {
        Minecraft client = Minecraft.getInstance();
        Level world = client.level;

        if (world == null) return false;

        BlockEntity exterior = world.getBlockEntity(this.botiPos);

        if (exterior instanceof ExteriorBlockEntity exteriorBlockEntity) {
            if (!exteriorBlockEntity.isLinked()) return false;
            Tardis tardis = exteriorBlockEntity.tardis().get();
            // tardis.stats().updateChunkModel(exteriorBlockEntity, this.chunkData);
        }
        return true;
    }
}
