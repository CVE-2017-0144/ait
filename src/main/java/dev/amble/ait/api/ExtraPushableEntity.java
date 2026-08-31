package dev.amble.ait.api;

import dev.amble.lib.platform.util.TriState;

public interface ExtraPushableEntity {
    void ait$setPushBehaviour(TriState pushable);
    TriState ait$pushBehaviour();
}
