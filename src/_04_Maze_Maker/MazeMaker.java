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

	public static Maze generateMaze(int w, int h) {
		width = w;
		height = h;
		maze = new Maze(width, height);

		// 4. select a random cell to start
		int x = randGen.nextInt(maze.getWidth());
		int y = randGen.nextInt(maze.getHeight());
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
			if(currentCell == null) {
				System.out.println("current");
			}
			if(chosen == null) {
				System.out.println("chosen");
			}
			removeWalls(currentCell, chosen);
			// C4. make the new cell the current cell and mark it as visited
			currentCell = chosen;
			
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
		// maybe do not check
			if (c1.getY() == c2.getY()) {
				if(c1.getX() > c2.getX()) {
				c1.setWestWall(false);
				c2.setEastWall(false);
				}
			}
		
			if (c1.getY() == c2.getY()) {
				if(c1.getX() < c2.getX()) {
				System.out.println("-_-");
				c1.setEastWall(false);
				c2.setWestWall(false);
				}
			}
		
			if (c1.getX() == c2.getX()) {
				if(c1.getY() > c2.getY()) {
				c1.setNorthWall(false);
				c2.setSouthWall(false);
				}
			}
		
			if (c1.getX() == c2.getX()) {
				if( c1.getY() < c2.getY()) {
				c1.setSouthWall(false);
				c2.setNorthWall(false);
				}
			}
		
	}

	// 8. Complete the getUnvisitedNeighbors method
	// Any unvisited neighbor of the passed in cell gets added
	// to the ArrayList
	private static ArrayList<Cell> getUnvisitedNeighbors(Cell c) {
		ArrayList<Cell> cells = new ArrayList<Cell>();
		if (c.getX() > 0 && c.getX() < width-1 && c.getY() > 0 && c.getY() < height-1) {
			cells.add(top(c));
			cells.add(right(c));
			cells.add(left(c));
			cells.add(bottom(c));
		} else if (c.getX() > 0 && c.getX() < width-1 && c.getY() == 0) { 
			cells.add(right(c));
			cells.add(left(c));
			cells.add(bottom(c));
		} else if (c.getX() > 0 && c.getX() < width-1 && c.getY() == height-1) { 
			cells.add(top(c));
			cells.add(right(c));
			cells.add(left(c));
		} else if (c.getX() == 0 && c.getY() > 0 && c.getY() < height-1) { 
			cells.add(top(c));
			cells.add(right(c));
			cells.add(bottom(c));
		} else if (c.getX() == 0 && c.getY() > 0 && c.getY() < height-1) { 
			cells.add(top(c));
			cells.add(left(c));
			cells.add(bottom(c));
		} else if(c.getX() == 0 && c.getY() == 0) {
			cells.add(right(c));
			cells.add(bottom(c));
		} else if(c.getX() == width-1 && c.getY() == 0) {
			cells.add(bottom(c));
			cells.add(left(c));
		} else if(c.getX() == 0 && c.getY() == height-1) {
			cells.add(top(c));
			cells.add(right(c));
		}else if(c.getX() == width-1 && c.getY() == height-1) {
			cells.add(left(c));
			cells.add(top(c));
		}
		return cells;

	}
	private static Cell right(Cell c) {
		if (!maze.getCell(c.getX() + 1, c.getY()).hasBeenVisited()) {
			return maze.getCell(c.getX() + 1, c.getY());
		}
		return null;
	}
	private static Cell left(Cell c) {
		if (!maze.getCell(c.getX() - 1, c.getY()).hasBeenVisited()) {
			return maze.getCell(c.getX() - 1, c.getY());
		}
		return null;
	}
	private static Cell top(Cell c) {
		if (!maze.getCell(c.getX(), c.getY() - 1).hasBeenVisited()) {
			return maze.getCell(c.getX(), c.getY() - 1);
		}
		return null;
	}
	private static Cell bottom(Cell c) {
		if (!maze.getCell(c.getX(), c.getY() + 1).hasBeenVisited()) {
			return maze.getCell(c.getX(), c.getY() + 1);
		}
		return null;
	}
	
}
