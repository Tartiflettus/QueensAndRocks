package gameElements;

public class Eval1 implements Eval {

	@Override
	public float getEval(Player player, Board b) {
		int caseAccessiblesActu = b.numberOfAccessible2(player);
		int caseAccessiblesAutre = b.numberOfAccessible2(b.getGame().otherPlayer(player));
		int caseImprenableActu = nbrCasesAutourReine(player, b);
		int caseImprenableAutre = nbrCasesAutourReine(b.getGame().otherPlayer(player), b);
		return caseAccessiblesActu - caseAccessiblesAutre + caseImprenableActu - caseImprenableAutre;
	}

	public int nbrCasesAutourReine(Player p, Board b) {
		int nbCases = 0;
		Square piece;
		for (int i = 0; i < b.getSize(); i++) {
			for (int j = 0; j < b.getSize(); j++) {
				piece = b.getPiece(i, j);
				if (piece.isQueen()) {
					if (piece.getPlayer().getNumber() == p.getNumber()) {
						nbCases += calculCases(i, j, b);
					}
				}
			}
		}
		return nbCases;
	}

	public int calculCases(int i, int j, Board b) {
		int nbCases = 0;
		nbCases += casesLigne(i, j, b);
		nbCases += casesCol(i, j, b);
		nbCases += casesDiagLeft(i, j, b);
		nbCases += casesDiagRight(i, j, b);
		return nbCases;
	}

	private int casesDiagRight(int i, int j, Board b) {
		int nbCases = 0;
		int lig = i + 1, col = j + 1;
		while (lig < b.getSize() && col < b.getSize()) {
			if (!b.getPiece(lig, col).blocksPassageway()) {
				nbCases++;
			} else {
				break;
			}
			lig++;
			col++;
		}
		lig = i - 1;
		col = j - 1;
		while (lig >= 0 && col >= 0) {
			if (!b.getPiece(lig, col).blocksPassageway()) {
				nbCases++;
			} else {
				break;
			}
			lig--;
			col--;
		}
		return nbCases;
	}

	private int casesDiagLeft(int i, int j, Board b) {
		int nbCases = 0;
		int lig = i + 1, col = j - 1;
		while (lig < b.getSize() && col >= 0) {
			if (!b.getPiece(lig, col).blocksPassageway()) {
				nbCases++;
			} else {
				break;
			}
			lig++;
			col--;
		}
		lig = i - 1;
		col = j + 1;
		while (lig >= 0 && col < b.getSize()) {
			if (!b.getPiece(lig, col).blocksPassageway()) {
				nbCases++;
			} else {
				break;
			}
			lig--;
			col++;
		}
		return nbCases;
	}

	private int casesCol(int i, int j, Board b) {
		int nbCases = 0;
		for (int k = j - 1; k >= 0; k--) {
			if (!b.getPiece(i, k).blocksPassageway()) {
				nbCases++;
			} else {
				break;
			}
		}
		for (int k = j + 1; k < b.getSize(); k++) {
			if (!b.getPiece(i, k).blocksPassageway()) {
				nbCases++;
			} else {
				break;
			}
		}
		return nbCases;
	}

	private int casesLigne(int i, int j, Board b) {
		int nbCases = 0;
		for (int k = i - 1; k >= 0; k--) {
			if (!b.getPiece(k, j).blocksPassageway()) {
				nbCases++;
			} else {
				break;
			}
		}
		for (int k = i + 1; k < b.getSize(); k++) {
			if (!b.getPiece(k, j).blocksPassageway()) {
				nbCases++;
			} else
				break;
		}
		return nbCases;
	}

}
