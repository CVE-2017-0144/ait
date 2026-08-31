package dev.amble.lib.duck;

import java.util.Map;
import net.minecraft.client.model.geom.ModelPart;

/**
 * Duck interface for accessing ModelPart's private children map.
 * Cast ModelPart instances to this interface to access the children.
 */
public interface ModelPartDuck {
	Map<String, ModelPart> amblekit$getChildren();
}

