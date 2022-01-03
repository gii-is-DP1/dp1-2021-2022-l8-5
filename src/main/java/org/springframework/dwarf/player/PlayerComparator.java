package org.springframework.dwarf.player;

import java.util.Comparator;

public class PlayerComparator implements Comparator<Player>{

	@Override
	public int compare(Player o1, Player o2) {
		return o1.getTurn().compareTo(o2.getTurn());
	}

}
