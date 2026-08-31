package dev.amble.ait.data.codec;

import java.util.List;
import net.minecraft.Util;
import com.mojang.serialization.Codec;
import org.joml.Vector3f;

public class MoreCodec {

    public static final Codec<Vector3f> VECTOR3F = Codec.FLOAT
            .listOf()
            .comapFlatMap(
                    coordinates -> Util.fixedSize(coordinates, 3).map(coords -> new Vector3f(coords.get(0), coords.get(1), coords.get(2))),
                    vec -> List.of(vec.x(), vec.y(), vec.z())
            );
}
