package org.springframework.dwarf.game;

public class ActionResolution implements GameState{
    private Game game;

    @Override
    public void phaseResolution() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setGame(Game game) {
        this.game = game;
        this.game.setCurrentPhase(GamePhase.ACTION_RESOLUTION);
    }
    
}