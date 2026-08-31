package dev.amble.ait.core.likes;

import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import dev.amble.ait.core.tardis.ServerTardis;
import dev.amble.lib.api.Identifiable;

public interface Opinion extends Identifiable {
    int loyalty();
    Type type();

    default boolean likes() {
        return loyalty() > 0;
    }
    default void apply(ServerTardis tardis, ServerPlayer target) {
        tardis.loyalty().addLevel(target, loyalty());
    }

    enum Type {
        ITEM {
            @Override
            public Opinion get(ResourceLocation id) {
                return ItemOpinionRegistry.getInstance().get(id);
            }
        },
        ;

        public abstract Opinion get(ResourceLocation id);
    };

    static Optional<Opinion> find(ResourceLocation id) {
        for (Type type : Type.values()) {
            Opinion opinion = type.get(id);
            if (opinion != null) {
                return Optional.of(opinion);
            }
        }

        return Optional.empty();
    }
}
