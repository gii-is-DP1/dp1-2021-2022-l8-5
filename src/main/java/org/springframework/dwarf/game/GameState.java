package org.springframework.dwarf.game;


import org.jpatterns.gof.CompositePattern.Component;
import org.jpatterns.gof.StatePattern;


@StatePattern
@Component
public class GameState {
	
    @StatePattern.State
    interface GamePhase {
        void phaseResolution(Game game);
        GamePhaseEnum getPhaseName();
    }

}
