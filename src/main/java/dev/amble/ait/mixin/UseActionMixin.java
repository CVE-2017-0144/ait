package dev.amble.ait.mixin;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.item.UseAnim;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;
import dev.amble.ait.api.AITUseActions;

@Mixin(UseAnim.class)
public class UseActionMixin implements AITUseActions {

    @Shadow
    @Final
    @Mutable
    private static UseAnim[] $VALUES;

    private static final UseAnim SONIC = register("SONIC");

    @Invoker("<init>")
    private static UseAnim init(String name, int ordinal) {
        throw new AssertionError();
    }

    @Override
    public UseAnim ait$sonic() {
        return SONIC;
    }

    @Unique private static UseAnim register(String name) {
        UseAnim result = init(name, UseAnim.values().length);

        List<UseAnim> actions = new ArrayList<>(List.of($VALUES));
        actions.add(result);

        $VALUES = actions.toArray(new UseAnim[0]);
        return result;
    }
}
