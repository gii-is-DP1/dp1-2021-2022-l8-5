package org.springframework.dwarf.game;

import java.util.List;
import java.util.Random;

import org.springframework.dwarf.board.Board;
import org.springframework.dwarf.board.BoardCell;
import org.springframework.dwarf.mountain_card.MountainCard;
import org.springframework.dwarf.mountain_card.MountainDeck;

public class MineralExtraction implements GameState{
    private Game game;
    
    private GameService gameService;

    @Override
    public void phaseResolution() {
    	
    	//Sacar el mazo de la partida
    	//Coger 2 cartas aleatorias del mazo y eliminarlas de este
    	
        MountainDeck mountaindeck = gameService.searchDeckByGameId(game.getId()).get();
        List<MountainCard> mountaincards = mountaindeck.getMountainCards();
        Random random = new Random();
        
        int index1 = random.nextInt(mountaincards.size());
        MountainCard mountaincard1 = mountaincards.remove(index1);
        
        int index2 = random.nextInt(mountaincards.size());
        MountainCard mountaincard2 = mountaincards.remove(index2);
        
        
        //Sacar las boardcells relacionadas con el board de este game, mediante board_id
        //Comprobar xposition e yposition de mountaincard1 y mountaincard2, y asignarselas a las boardcells
        //cuyas xposition e yposition coincidan
        
        
    	Board board = gameService.findBoardByGameId(game.getId()).get();
    	
    	List<BoardCell> boardcells = board.getBoardCells();
    	
    	for (BoardCell b : boardcells) {
    		List<MountainCard> cellcards = b.getMountaincards();
    		if (mountaincard1.getXPosition().equals(b.getXposition()) &&
    				mountaincard1.getYPosition().equals(b.getYposition())) {
    			cellcards.add(0, mountaincard1);;
    			b.setMountaincards(cellcards);
    		}
    		if (mountaincard2.getXPosition().equals(b.getXposition()) &&
    				mountaincard2.getYPosition().equals(b.getYposition())) {
    			cellcards.add(0, mountaincard2);
    			b.setMountaincards(cellcards);
    		}
    	}
       
    }

    @Override
    public void setGame(Game game) {
        this.game = game;
        this.game.setCurrentPhase(GamePhase.MINERAL_EXTRACTION);
    }
    
}
