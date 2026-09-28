package dev.amble.ait.client.config;

import dev.amble.ait.config.AITServerConfig;
import dev.isxander.yacl3.api.ButtonOption;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class AITConfigScreen {

    public static Screen create(Screen parent) {
        return YetAnotherConfigLib.create(
                AITServerConfig.INSTANCE, (defaults, config, builder) -> builder
                        .title(Component.translatable("text.ait.config.title"))
                        .category(ConfigCategory.createBuilder()
                                .name(Component.translatable("text.ait.config.categories"))
                                .option(ButtonOption.createBuilder()
                                        .name(Component.translatable("category.ait.config.client"))
                                        .action((yaclScreen, buttonOption) -> Minecraft.getInstance().setScreen(
                                                AITClientConfig.INSTANCE.generateGui().generateScreen(yaclScreen))
                                        ).build())
                                .option(ButtonOption.createBuilder()
                                        .name(Component.translatable("category.ait.config.server"))
                                        .action((yaclScreen, buttonOption) -> Minecraft.getInstance().setScreen(
                                                AITServerConfig.INSTANCE.generateGui().generateScreen(yaclScreen))
                                        ).build())
                                .build())
        ).generateScreen(parent);
    }
}
