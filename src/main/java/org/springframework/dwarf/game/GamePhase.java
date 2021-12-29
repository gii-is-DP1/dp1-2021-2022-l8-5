package org.springframework.dwarf.game;

import org.jpatterns.gof.StatePattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dwarf.worker.WorkerService;
import org.springframework.stereotype.Component;

@StatePattern.State
@Component
public interface GamePhase {
	
    void phaseResolution(Game game);
    GamePhaseEnum getPhaseName();
}
