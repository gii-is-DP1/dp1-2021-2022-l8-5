package org.springframework.dwarf.board;

import java.util.Comparator;

public class BoardCellActionsComparator implements Comparator<BoardCell>{

	@Override
	public int compare(BoardCell o1, BoardCell o2) {
		return o1.getMountaincards().get(0).getCardType().compareTo(o2.getMountaincards().get(0).getCardType());
	}

}
