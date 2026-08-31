package dev.amble.ait.core.util;

import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.HoverEvent;
import dev.amble.ait.core.tardis.Tardis;

public class TextUtil {

    public static Component forTardis(Tardis tardis) {
        return forTardis(tardis.getUuid());
    }
    public static Component forTardis(UUID tardis) {
        String id = tardis.toString();

        return ComponentUtils.wrapInSquareBrackets(Component.literal(id.substring(0, 7))).withStyle(style -> style.withColor(ChatFormatting.GREEN)
                .withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, id))
                .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.translatable("message.ait.click_to_copy"))));
    }
}
