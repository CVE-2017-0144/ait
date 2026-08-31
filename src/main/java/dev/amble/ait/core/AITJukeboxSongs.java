package dev.amble.ait.core;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.JukeboxSong;

import dev.amble.ait.AITMod;

public class AITJukeboxSongs {

    public static final ResourceKey<JukeboxSong> TWO_THOUSAND = of("two_thousand");
    public static final ResourceKey<JukeboxSong> STAGE_4 = of("stage_4");
    public static final ResourceKey<JukeboxSong> WONDERFUL_TIME_IN_SPACE = of("wonderful_time_in_space");
    public static final ResourceKey<JukeboxSong> VENUS = of("venus");
    public static final ResourceKey<JukeboxSong> GOOD_MAN = of("good_man");
    public static final ResourceKey<JukeboxSong> AIT_THEME = of("ait_theme");
    public static final ResourceKey<JukeboxSong> EARTH = of("earth");
    public static final ResourceKey<JukeboxSong> CRASH = of("crash");

    private static ResourceKey<JukeboxSong> of(String name) {
        return ResourceKey.create(Registries.JUKEBOX_SONG, AITMod.id(name));
    }
}
