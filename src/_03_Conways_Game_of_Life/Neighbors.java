package _03_Conways_Game_of_Life;

public class Neighbors {
	Cell[][] cells;
	public int living = 0;

	public Neighbors(Cell[][] cells) {
		this.cells = cells;
	}

	public int leftNeighbor(int x, int y) {
		int count = 0;
		for (int i = -1; i < 2; i++) {
			if (cells[x - 1][y + i].isAlive) {
				count++;
			}
		}
		return count + this.living;
	}

	public int xcenter(int x, int y) {
		int count = 0;
		for (int i = -1; i <= 1; i += 2) {
			if (cells[x][y + i].isAlive) {
				count++;
			}
		}
		return count + this.living;
	}

	public int rightNeighbor(int x, int y) {
		int count = 0;
		for (int i = -1; i < 2; i++) {
			if (cells[x + 1][y + i].isAlive) {
				count++;
			}
		}
		return count + this.living;
	}

	public int topNeighbor(int x, int y) {
		int count = 0;
		for (int i = -1; i < 2; i++) {
			if (cells[x + i][y - 1].isAlive) {
				count++;
			}
		}
		return count + this.living;
	}

	public int ycenter(int x, int y) {
		int count = 0;
		for (int i = -1; i < 2; i += 2) {
			if (cells[x + i][y].isAlive) {
				count++;
			}
		}
		return count + this.living;
	}

	public int lowerNeighbor(int x, int y) {
		int count = 0;
		for (int i = -1; i < 2; i++) {
			if (cells[x + i][y + 1].isAlive) {
				count++;
			}
		}
		return count + this.living;
	}

	public int topleft(int x, int y) {
		int count = 0;
		for (int i = 0; i < 2; i++) {
			if (cells[x + i][y + 1].isAlive) {
				count++;
			}
		}
		if (cells[x + 1][y].isAlive) {
			count++;
		}
		return count + this.living;
	}
	
	public int topright(int x, int y) {
		int count = 0;
		for (int i = 0; i < 2; i++) {
			if (cells[x - i][y + 1].isAlive) {
				count++;
			}
		}
		if (cells[x - 1][y].isAlive) {
			count++;
		}
		return count + this.living; 
	}
	
	public int bottomleft(int x, int y) {
		int count = 0;
		for (int i = 0; i < 2; i++) {
			if (cells[x + i][y - 1].isAlive) {
				count++;
			}
		}
		if (cells[x + 1][y].isAlive) {
			count++;
		}
		return count + this.living;
	}
	
	public int bottomright(int x, int y) {
		int count = 0;
		for (int i = 0; i < 2; i++) {
			if (cells[x - i][y - 1].isAlive) {
				count++;
			}
		}
		if (cells[x - 1][y].isAlive) {
			count++;
		}
		return count + this.living;
	}
}
