package _03_Conways_Game_of_Life;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

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
		cellSize = w * h;

		// 3. Initialize the cell array to the appropriate size.
		cells = new Cell[cellsPerRow][cellsPerRow];
		// 3. Iterate through the array and initialize each cell.
		// Don't forget to consider the cell's dimensions when
		// passing in the location.
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				cells[i][j] = new Cell(w, h, cellSize);
			}
		}
	}

	public void randomizeCells() {
		// 4. Iterate through each cell and randomly set each
		// cell's isAlive memeber to true of false
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				Random rand = new Random();
				if (rand.nextInt(1) == 0) {
					cells[i][j].isAlive = false;
				}
				if (rand.nextInt(1) == 1) {
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
				g.drawRect(i * cellSize, j * cellSize, cellSize, cellSize);
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
				if (livingNeighbors[i][j] >= 2 && livingNeighbors[i][j] <= 3) {
					cells[i][j].isAlive = true;
				} else {
					cells[i][j].isAlive = false;
				}
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
		for (int i = -1; i < 2; i++) {
			if (cells[x - 1][y + i].isAlive) {
				living++;
			}
		}
		for (int i = -1; i < 2; i += 2) {
			if (cells[x][y + i].isAlive) {
				living++;
			}
		}
		for (int i = -1; i < 2; i++) {
			if (cells[x + 1][y + i].isAlive) {
				living++;
			}
		}
		if (living <= 8) {
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
		int distance = 500 / (cellsPerRow);
		for (int i = 1; i < cells.length; i++) {
			if (e.getX() <= 500/ (distance * i) && e.getX() >= 500/ (distance * (i - 1))) {
				cells[e.getX() / (distance * i)][e.getY() / (distance * i)].isAlive = true;
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
}
