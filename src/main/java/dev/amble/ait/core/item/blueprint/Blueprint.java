package dev.amble.ait.core.item.blueprint;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import dev.amble.ait.core.util.StackUtil;

public class Blueprint {
    private final BlueprintSchema source;
    private final List<ItemStack> requirements;
    private final List<ItemStack> initialRequirements;

    public Blueprint(BlueprintSchema source) {
        this.source = source;

        this.initialRequirements = source.inputs().toStacks();
        this.requirements = StackUtil.cloneList(initialRequirements);
    }

    public Blueprint(CompoundTag nbt) {
        this(BlueprintRegistry.getInstance().get(new ResourceLocation(nbt.getString("id"))));

        this.requirements.clear();
        this.fromNbt(nbt);
    }

    /**
     * attempts to resolve a requirement from the list of requirements by removing it if this stack is a valid requirement
     * @param stack the stack to resolve
     * @return true if the stack was a valid requirement and was removed, false otherwise
     */
    public boolean tryAdd(ItemStack stack) {
        for (ItemStack requirement : requirements) {
            if (ItemStack.isSameItem(requirement, stack)) {
                // now we need to check if the stack has the same amount of items

                int deducted = Math.min(requirement.getCount(), stack.getCount());
                requirement.shrink(deducted);
                stack.shrink(deducted);

                if (requirement.isEmpty())
                    requirements.remove(requirement);

                return true;
            }
        }

        return false;
    }

    public int getCountLeftFor(ItemStack stack) {
        for (ItemStack requirement : requirements) {
            if (ItemStack.isSameItem(requirement, stack)) {
                return requirement.getCount();
            }
        }

        return 0;
    }

    public boolean isComplete() {
        return requirements.isEmpty();
    }

    public ItemStack getOutput() {
        return source.output().copy();
    }

    public Optional<ItemStack> tryCraft() {
        if (!isComplete())
            return Optional.empty();

        return Optional.of(getOutput());
    }

    public List<ItemStack> getRequirements() {
        return requirements;
    }

    /**
     * @return All the items that were inserted into the fabricator
     */
    public List<ItemStack> getInsertedItems() {
        // all the items missing from the initial requirements
        List<ItemStack> inserted = new ArrayList<>(initialRequirements);

        for (ItemStack j : requirements) {
            inserted.stream()
                    .filter(i -> ItemStack.isSameItem(i, j))
                    .forEach(i -> i.shrink(j.getCount()));
        }

        return inserted;
    }

    public CompoundTag toNbt() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("id", source.id().toString());

        ListTag list = new ListTag();
        for (ItemStack stack : requirements) {
            list.add(stack.save(new CompoundTag()));
        }
        nbt.put("requirements", list);

        return nbt;
    }
    protected CompoundTag fromNbt(CompoundTag nbt) {
        ListTag list = nbt.getList("requirements", 10);
        for (int i = 0; i < list.size(); i++) {
            requirements.add(ItemStack.of(list.getCompound(i)));
        }

        return nbt;
    }

    public BlueprintSchema getSource() {
        return source;
    }
}
