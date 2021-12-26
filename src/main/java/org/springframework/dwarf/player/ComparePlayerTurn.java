package org.springframework.dwarf.player;

import java.util.Comparator;

public class ComparePlayerTurn implements Comparator<Player>{

	@Override
	public int compare(Player o1, Player o2) {
		return o1.getGameTurn().compareTo(o2.getGameTurn());
	}
}
