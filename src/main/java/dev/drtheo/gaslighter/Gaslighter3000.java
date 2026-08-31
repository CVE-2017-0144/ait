package dev.drtheo.gaslighter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import dev.drtheo.gaslighter.api.FakeBlockEvents;
import dev.drtheo.gaslighter.api.Twitter;
import dev.drtheo.gaslighter.impl.FakeChunkSection;
import it.unimi.dsi.fastutil.shorts.ShortOpenHashSet;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.state.BlockState;

public class Gaslighter3000 {

    private final ServerLevel world;
    private final Map<ChunkPos, ChunkHolder> lookup = new HashMap<>();

    public Gaslighter3000(ServerLevel world) {
        this.world = world;
    }

    public void spreadLies(BlockPos pos, BlockState state) {
        FakeBlockEvents.PLACED.invoker().onPlace(this.world, state, pos);

        this.lookup.computeIfAbsent(new ChunkPos(pos), chunkPos
                -> new ChunkHolder(world, chunkPos)).spreadLies(pos, state);
    }

    public void touchGrass(BlockPos pos) {
        ChunkHolder holder = this.lookup.get(new ChunkPos(pos));

        if (holder == null)
            return;

        holder.touchGrass(pos);
    }

    public BlockState getAgenda(BlockPos pos) {
        ChunkHolder holder = this.lookup.get(new ChunkPos(pos));

        if (holder == null)
            return this.world.getBlockState(pos);

        return holder.getAgenda(pos);
    }

    public void touchGrass() {
        this.lookup.values().forEach(ChunkHolder::touchGrass);
    }

    public void tweet() {
        for (ChunkHolder holder : lookup.values()) {
            holder.tweet();
        }
    }

    public void tweet(ServerPlayer player) {
        for (ChunkHolder holder : lookup.values()) {
            holder.tweet(player);
        }
    }

    static class ChunkHolder {

        private final ServerLevel world;
        private final ChunkPos pos;

        private final ShortSet[] blockUpdatesBySection;
        private final FakeChunkSection[] sections;

        public ChunkHolder(ServerLevel world, ChunkPos pos) {
            this.world = world;
            this.pos = pos;

            this.blockUpdatesBySection = new ShortSet[world.getSectionsCount()];
            this.sections = new FakeChunkSection[this.blockUpdatesBySection.length];
        }

        public void spreadLies(BlockPos pos, BlockState state) {
            int i = this.world.getSectionIndex(pos.getY());

            if (this.sections[i] == null)
                this.sections[i] = new FakeChunkSection(this.world);

            this.sections[i].setBlockState(pos.getX() & 0xF, pos.getY() & 0xF, pos.getZ() & 0xF, state);

            if (this.world instanceof Twitter twitter)
                twitter.ait$setFake(pos, true);

            this.markForBlockUpdate(pos, i);
        }

        public void touchGrass(BlockPos pos) {
            int i = this.world.getSectionIndex(pos.getY());

            if (this.sections[i] == null)
                return;

            this.sections[i].setBlockState(pos.getX() & 0xF, pos.getY() & 0xF, pos.getZ() & 0xF,
                    this.world.getBlockState(pos));

            if (this.world instanceof Twitter twitter)
                twitter.ait$setFake(pos, false);

            this.markForBlockUpdate(pos, i);
            FakeBlockEvents.REMOVED.invoker().onRemove(this.world, pos);
        }

        public void touchGrass() {
            for (int i = 0; i < this.sections.length; i++) {
                ShortSet updates = this.blockUpdatesBySection[i];

                if (updates == null)
                    continue;

                int coord = this.world.getSectionYFromSectionIndex(i);
                SectionPos csp = SectionPos.of(this.pos, coord);

                for (short update : updates) {
                    this.touchGrass(csp.relativeToBlockPos(update));
                }
            }
        }

        public BlockState getAgenda(BlockPos pos) {
            int i = this.world.getSectionIndex(pos.getY());

            if (this.sections[i] == null)
                return this.world.getBlockState(pos);

            return this.sections[i].getBlockState(pos.getX() & 0xF, pos.getY() & 0xF, pos.getZ() & 0xF);
        }

        private void markForBlockUpdate(BlockPos pos, int i) {
            if (this.blockUpdatesBySection[i] == null)
                this.blockUpdatesBySection[i] = new ShortOpenHashSet();

            this.blockUpdatesBySection[i].add(SectionPos.sectionRelativePos(pos));
        }

        private void unmarkForBlockUpdate(BlockPos pos, int i) {
            if (this.blockUpdatesBySection[i] == null)
                return;

            this.blockUpdatesBySection[i].remove(SectionPos.sectionRelativePos(pos));
        }

        private void makeTweetPackets(Consumer<ClientboundSectionBlocksUpdatePacket> consumer) {
            for (int i = 0; i < this.blockUpdatesBySection.length; ++i) {
                ShortSet shortSet = this.blockUpdatesBySection[i];

                if (shortSet == null)
                    continue;

                int j = this.world.getSectionYFromSectionIndex(i);
                SectionPos chunkSectionPos = SectionPos.of(this.pos, j);

                consumer.accept(new ClientboundSectionBlocksUpdatePacket(chunkSectionPos, shortSet, this.sections[i]));
            }
        }

        public void tweet() {
            Collection<ServerPlayer> list = PlayerLookup.tracking(this.world, this.pos);

            if (list.isEmpty())
                return;

            this.makeTweetPackets(packet -> sendPacketToPlayers(list, packet));
        }

        public void tweet(ServerPlayer player) {
            this.makeTweetPackets(packet -> player.connection.send(packet));
        }

        private static void sendPacketToPlayers(Collection<ServerPlayer> players, Packet<?> packet) {
            players.forEach(player -> player.connection.send(packet));
        }
    }
}
