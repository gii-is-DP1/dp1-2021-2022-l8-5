package org.springframework.dwarf.game;

public interface GameState {

    void phaseResolution();
    void setGame(Game game);
    
}