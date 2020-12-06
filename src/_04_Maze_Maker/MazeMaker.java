package _04_Maze_Maker;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MazeMaker {

	private static int width;
	private static int height;

	private static Maze maze;

	private static Random randGen = new Random();
	private static Stack<Cell> uncheckedCells = new Stack<Cell>();
	static ArrayList<Cell> cells = new ArrayList<Cell>();

	public static Maze generateMaze(int w, int h) {
		width = w;
		height = h;
		maze = new Maze(width, height);

		// 4. select a random cell to start
		//int x = randGen.nextInt(maze.getWidth());
		//int y = randGen.nextInt(maze.getHeight());
		int x = 1;
		int y = 1;
		// 5. call selectNextPath method with the randomly selected cell
		selectNextPath(maze.getCell(x, y));

		return maze;
	}

	// 6. Complete the selectNextPathMethod
	private static void selectNextPath(Cell currentCell) {
		// A. mark cell as visited

		currentCell.setBeenVisited(true);
		// B. Get an ArrayList of unvisited neighbors using the current cell and the
		// method below
		ArrayList<Cell> list = getUnvisitedNeighbors(currentCell);

		// C. if has unvisited neighbors,
		if (list.size() > 0) {
			// C1. select one at random.
			Cell chosen = list.get(randGen.nextInt(list.size()));
			// C2. push it to the stack
			uncheckedCells.push(chosen);
			// C3. remove the wall between the two cells

			removeWalls(currentCell, chosen);
			// C4. make the new cell the current cell and mark it as visited
			currentCell = chosen;
			currentCell.setBeenVisited(true);
			// C5. call the selectNextPath method with the current cell
			selectNextPath(currentCell);
		}

		// D. if all neighbors are visited
		else {
			// D1. if the stack is not empty
			if (uncheckedCells.isEmpty() == false) {
				// D1a. pop a cell from the stack
				currentCell = uncheckedCells.pop();
				// D1b. make that the current cell

				// D1c. call the selectNextPath method with the current cell
				
				selectNextPath(currentCell);
			}
			
		}

	}

	// 7. Complete the remove walls method.
	// This method will check if c1 and c2 are adjacent.
	// If they are, the walls between them are removed.
	private static void removeWalls(Cell c1, Cell c2) {
		if (c1.getY() == c2.getY()) {
			if (c1.getX() > c2.getX()) {
				c1.setWestWall(false);
				c2.setEastWall(false);
			} else {
				c1.setEastWall(false);
				c2.setWestWall(false);
			}
		} else {
			if (c1.getY() > c2.getY()) {
				c1.setNorthWall(false);
				c2.setSouthWall(false);
			} else {
				c1.setSouthWall(false);
				c2.setNorthWall(false);
			}
		}

	}

	// 8. Complete the getUnvisitedNeighbors method
	// Any unvisited neighbor of the passed in cell gets added
	// to the ArrayList
	private static ArrayList<Cell> getUnvisitedNeighbors(Cell c) {
		cells = new ArrayList<Cell>();

		if (c.getX() > 0 && c.getX() < width - 1 && c.getY() > 0 && c.getY() < height - 1) {
			System.out.println("centers");
			top(c);
			right(c);
			left(c);
			bottom(c);
		} else if (c.getY() == 0 && c.getX() > 0 && c.getX() < width - 1) {
			right(c);
			left(c);
			bottom(c);
		} else if (c.getY() == height - 1 && c.getX() > 0 && c.getX() < width - 1) {
			top(c);
			right(c);
			left(c);
		} else if (c.getX() == 0 && c.getY() > 0 && c.getY() < height - 1) {
			top(c);
			right(c);
			bottom(c);
		} else if (c.getX() == width - 1 && c.getY() > 0 && c.getY() < height - 1) {
			top(c);
			left(c);
			bottom(c);
		} else if (c.getX() == 0 && c.getY() == 0) {
			right(c);
			bottom(c);
		} else if (c.getX() == width - 1 && c.getY() == 0) {
			bottom(c);
			left(c);
		} else if (c.getX() == 0 && c.getY() == height - 1) {
			top(c);
			right(c);
		} else if (c.getX() == width - 1 && c.getY() == height - 1) {
			left(c);
			top(c);
		}
		return cells;

	}

	private static void right(Cell c) {
		if (maze.getCell(c.getX() + 1, c.getY()).hasBeenVisited() == false) {
			cells.add(maze.getCell(c.getX() + 1, c.getY()));
			System.out.println("right");
		}
	}

	private static void left(Cell c) {
		if (maze.getCell(c.getX() - 1, c.getY()).hasBeenVisited() == false) {
			cells.add(maze.getCell(c.getX() - 1, c.getY()));
			System.out.println("left");
		}
	}

	private static void top(Cell c) {
		if (maze.getCell(c.getX(), c.getY() - 1).hasBeenVisited() == false) {
			cells.add(maze.getCell(c.getX(), c.getY() - 1));
			System.out.println("top");
		}
	}

	private static void bottom(Cell c) {
		if (maze.getCell(c.getX(), c.getY() + 1).hasBeenVisited() == false) {
			cells.add(maze.getCell(c.getX(), c.getY() + 1));
			System.out.println("bottom");
		}
	}

}
