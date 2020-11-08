package _03_Conways_Game_of_Life;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import _02_Pixel_Art.Pixel;

public class WorldPanel extends JPanel implements MouseListener, ActionListener {
	private static final long serialVersionUID = 1L;
	private int cellsPerRow;
	private int cellSize;

	private Timer timer;

	// 1. Create a 2D array of Cells. Do not initialize it.

	Cell[][] cells; 

	public WorldPanel(int w, int h, int cpr) {
		setPreferredSize(new Dimension(w, h));
		addMouseListener(this);
		timer = new Timer(500, this);
		this.cellsPerRow = cpr;

		// 2. Calculate the cell size.
		cellSize = h / cellsPerRow;

		// 3. Initialize the cell array to the appropriate size.
		cells = new Cell[cellsPerRow][cellsPerRow];
		// 3. Iterate through the array and initialize each cell.
		// Don't forget to consider the cell's dimensions when
		// passing in the location.
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				cells[i][j] = new Cell(j * cellSize, i * cellSize, cellSize);
			}
		}
	}

	public void randomizeCells() {
		// 4. Iterate through each cell and randomly set each
		// cell's isAlive memeber to true of false
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				Random rand = new Random();
				if (rand.nextInt(2) == 0) {
					cells[i][j].isAlive = false;
				}
				if (rand.nextInt(2) == 1) {
					cells[i][j].isAlive = true;
				}

			}
		}
		repaint();
	}

	public void clearCells() {
		// 5. Iterate through the cells and set them all to dead.
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				cells[i][j].isAlive = false;
			}
		}
		repaint();
	}

	public void startAnimation() {
		timer.start();
	}

	public void stopAnimation() {
		timer.stop();
	}

	public void setAnimationDelay(int sp) {
		timer.setDelay(sp);
	}

	@Override
	public void paintComponent(Graphics g) {
		// 6. Iterate through the cells and draw them all
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				cells[i][j].draw(g);
			}
		}

		// draws grid
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
	}

	// advances world one step
	public void step() {
		// 7. iterate through cells and fill in the livingNeighbors array
		// . using the getLivingNeighbors method.
		int[][] livingNeighbors = new int[cellsPerRow][cellsPerRow];
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				livingNeighbors[i][j] = getLivingNeighbors(i, j);
			}
		}
		// 8. check if each cell should live or die
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				cells[i][j].liveOrDie(livingNeighbors[i][j]);
			}
		}

		repaint();
	}

	// 9. Complete the method.
	// It returns an int of 8 or less based on how many
	// living neighbors there are of the
	// cell identified by x and y
	public int getLivingNeighbors(int x, int y) {
	
		int living = 0;
		if ((x > 0 && x < cells.length-1) && (y > 0 && y < cells.length-1)) {
			living+=leftNeighbor(x, y);
			living+=xcenter(x, y);
			living+=rightNeighbor(x, y);
		} else if (x == 0 && y > 0 && y < cells.length - 1) {
			living+=xcenter(x, y);
			living+=rightNeighbor(x, y);
		} else if (x == cells.length - 1 && y > 0 && y < cells.length - 1) {
			living+=leftNeighbor(x, y);
			living+=xcenter(x, y);
		} else if (y == 0 && x > 0 && x < cells.length - 1) {
			living+=ycenter(x, y);
			living+=lowerNeighbor(x, y);
		} else if (y == cells.length - 1 && x > 0 && x < cells.length - 1) {
			living+=topNeighbor(x, y);
			living+=topNeighbor(x, y);
		} else if (x == 0 && y == 0) {
			living+=topleft(x, y);
		} else if (x == cells.length - 1 && y == 0) {
			living+=topright(x, y);
		} else if (x == 0 && y == cells.length - 1) {
			living+=bottomleft(x, y);
		} else if (x == cells.length - 1 && y == cells.length - 1) {
			living+=bottomright(x, y);
		}
		if ( living <= 8) {
			return living;
		}
		return 0; 
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// 10. Use e.getX() and e.getY() to determine
		// which cell is clicked. Then toggle
		// the isAlive variable for that cell.
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells.length; j++) {
				Cell cl = cells[i][j];
				if (e.getX() > cl.getX() && e.getX() < (cl.getX()+cellSize) && e.getY() > cl.getY() && e.getY() < (cl.getY()+cellSize)) {
					cl.isAlive = true;
				}
			}
		}

		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		step();
	}
	public int leftNeighbor(int x, int y  ) {
		int count = 0;
		for (int i = -1; i < 2; i++) {
			if (cells[x - 1][y + i].isAlive) {
				count++;
			}
		} 
		
		return count;
	}

	public int xcenter(int x, int y  ) {
		int count = 0;
		for (int i = -1; i <= 1; i += 2) {
			if (cells[x][y + i].isAlive) {
				count++;
			}
		}
		return count;
	}

	public int rightNeighbor(int x, int y  ) {
		int count = 0;
		for (int i = -1; i < 2; i++) {
			if (cells[x + 1][y + i].isAlive) {
				count++;
			}
		}
		return count;
	}

	public int topNeighbor(int x, int y  ) {
		int count = 0;
		for (int i = -1; i < 2; i++) {
			if (cells[x + i][y - 1].isAlive) {
				count++;
			}
		}
		return count;
	}

	public int ycenter(int x, int y  ) {
		int count = 0;
		for (int i = -1; i < 2; i += 2) {
			if (cells[x + i][y].isAlive) {
				count++;
			}
		}
		return count;
	}

	public int lowerNeighbor(int x, int y  ) {
		int count = 0;
		for (int i = -1; i < 2; i++) {
			if (cells[x + i][y + 1].isAlive) {
				count++;
			}
		}
		return count;
	}

	public int topleft(int x, int y  ) {
		int count = 0;
		for (int i = 0; i < 2; i++) {
			if (cells[x + i][y + 1].isAlive) {
				count++;
			}
		}
		if (cells[x + 1][y].isAlive) {
			count++;
		}
		return count;
	}
	
	public int topright(int x, int y  ) {
		int count = 0;
		for (int i = 0; i < 2; i++) {
			if (cells[x - i][y + 1].isAlive) {
				count++;
			}
		}
		if (cells[x - 1][y].isAlive) {
			count++;
		}
		return count; 
	}
	
	public int bottomleft(int x, int y  ) {
		int count = 0;
		for (int i = 0; i < 2; i++) {
			if (cells[x + i][y - 1].isAlive) {
				count++;
			}
		}
		if (cells[x + 1][y].isAlive) {
			count++;
		}
		return count ;
	}
	
	public int bottomright(int x, int y  ) {
		int count = 0;
		for (int i = 0; i < 2; i++) {
			if (cells[x - i][y - 1].isAlive) {
				count++;
			}
		}
		if (cells[x - 1][y].isAlive) {
			count++;
		}
		return count ;
	}

}
