package org.springframework.dwarf.game;

import org.jpatterns.gof.StatePattern;

@StatePattern.State
public interface GamePhase {
	
    void phaseResolution(Game game);
    GamePhaseEnum getPhaseName();
}
