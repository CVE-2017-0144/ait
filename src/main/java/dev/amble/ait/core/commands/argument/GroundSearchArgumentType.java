package dev.amble.ait.core.commands.argument;

import com.mojang.brigadier.context.CommandContext;
import dev.amble.ait.core.util.SafePosSearch;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.StringRepresentableArgument;
import net.minecraft.util.StringRepresentable;

public class GroundSearchArgumentType extends StringRepresentableArgument<SafePosSearch.Kind> {

    public static final StringRepresentable.EnumCodec<SafePosSearch.Kind> CODEC = StringRepresentable
            .fromEnum(SafePosSearch.Kind::values);

    protected GroundSearchArgumentType() {
        super(CODEC, SafePosSearch.Kind::values);
    }

    public static GroundSearchArgumentType groundSearch() {
        return new GroundSearchArgumentType();
    }

    public static SafePosSearch.Kind getGroundSearch(CommandContext<CommandSourceStack> context,
            String id) {
        return context.getArgument(id, SafePosSearch.Kind.class);
    }
}
