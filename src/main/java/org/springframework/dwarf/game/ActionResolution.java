package org.springframework.dwarf.game;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.jpatterns.gof.StatePattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dwarf.board.BoardCellService;
import org.springframework.dwarf.board.BoardService;
import org.springframework.dwarf.mountain_card.MountainDeckService;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.worker.Worker;
import org.springframework.dwarf.worker.WorkerService;
import org.springframework.stereotype.Component;

@StatePattern.ConcreteState
@org.springframework.stereotype.Component
public class ActionResolution implements GamePhase{
    
 
    private GameService gameService;
    
    private WorkerService workerService;
    
    private MountainDeckService mountainDeckService;
    
    private BoardCellService boardCellService;
    
    private BoardService boardService;
    

    @Autowired
	public ActionResolution(WorkerService workerService, GameService gameService, MountainDeckService mountainDeckService, BoardCellService boardCellService, BoardService boardService) {
		super();
		this.gameService = gameService;
		this.workerService = workerService;
		this.mountainDeckService = mountainDeckService;
		this.boardCellService = boardCellService;
		this.boardService = boardService;
	}

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
		
		game.setPhase(new MineralExtraction(workerService, gameService, mountainDeckService, boardCellService, boardService));
	}

	@Override
	public GamePhaseEnum getPhaseName() {
		return GamePhaseEnum.ACTION_RESOLUTION;
	}
}