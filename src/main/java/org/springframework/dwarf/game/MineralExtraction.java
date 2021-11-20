package org.springframework.dwarf.game;

public class MineralExtraction implements GameState{
    private Game game;

    @Override
    public void phaseResolution() {
        // TODO Auto-generated method stud
    }

    @Override
    public void setGame(Game game) {
        this.game = game;
        this.game.setCurrentPhase(GamePhase.MINERAL_EXTRACTION);
    }
    
}
