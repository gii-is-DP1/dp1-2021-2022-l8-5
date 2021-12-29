package org.springframework.dwarf.game;

import java.util.List;
import java.util.stream.Collectors;

import org.jpatterns.gof.StatePattern;
import org.jpatterns.gof.CompositePattern.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dwarf.game.GameState.GamePhase;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.worker.Worker;
import org.springframework.dwarf.worker.WorkerService;

@StatePattern.ConcreteState
@org.springframework.stereotype.Component
public class ActionResolution implements GamePhase{
    
    @Autowired
    private GameService gameService;
    
    @Autowired
    private WorkerService workerService;

	@Override
	public void phaseResolution(Game game) {
		
		Integer gameId = game.getId();
		List<Player> players = gameService.searchPlayersByGame(gameId);	//Todos los jugadores del game
		
		for(int i=0; i<players.size(); i++) {
			List<Worker> workers = workerService.findByPlayerIdAndGameId(gameId, players.get(i).getId())
												.stream().collect(Collectors.toList());
			for(int j=0; j<workers.size(); j++) {
				Worker w = workers.get(j);		//Worker j del player i;
				Integer wXPos = w.getXposition();
				Integer wYPos = w.getYposition();
				
				//Comprobaciones de posiciones y resolucion de acciones de las cartas pendiente (Strategy)
				
			}
		}
		
		game.setPhase(new MineralExtraction());
	}

	@Override
	public GamePhaseEnum getPhaseName() {
		return GamePhaseEnum.ACTION_RESOLUTION;
	}
}