package pixelFluid.linde9821.com.simulation.grid;

import java.util.ArrayList;

import pixelFluid.linde9821.com.simulation.particel.Particle;
import pixelFluid.linde9821.com.simulation.particel.Position;

public class Grid {

	private Cell[][] grid = new Cell[1200][800];

	public Grid() {
		iniGrid();
	}

	public void moveParticle(Particle p) {
		double newX = (p.getPosPrev().getX() + p.getPos().getX());
		double newY = (p.getPosPrev().getY() + p.getPos().getY());
		
		p.setPos(new Position(newX, newY));
		
		// not so sure
		if (newY > 800)
			newY = 799;
		else if (newY < 0)
			newY = 0;
		
		if (newX > 1200)
			newX = 1199;
		else if (newX < 0)
			newX = 0;
		
		grid[(int) newX][(int) newY].setP(p);
	}

	public ArrayList<Particle> possibleNeigbors(Particle p) {
		int x = 0;
		int y = 0;
		ArrayList<Particle> neighbors = new ArrayList<Particle>();

		for (int i = 0; i < 800; i++) {
			for (int j = 0; j < 1200; j++) {
				if (p == grid[j][i].getP()) {
					x = j;
					y = i;
				}
			}
		}

		for (int xx = -1; xx <= 1; xx++) {
			for (int yy = -1; yy <= 1; yy++) {
				if (xx == 0 && yy == 0) {
					continue; // You are not neighbor to yourself
				}
				if (Math.abs(xx) + Math.abs(yy) > 1) {
					continue;
				}
				if (isOnMap(x + xx, y + yy) && isValidNeighbors(x + xx, y + yy)) {
					neighbors.add(grid[x + xx][y + yy].getP());
				}
			}
		}

		return neighbors;
	}

	public boolean isOnMap(int x, int y) {
		return x >= 0 && y >= 0 && x < 1200 && y < 800;
	}
	
	public boolean isValidNeighbors(int x, int y) {
		if (grid[x][y].getP() != null)
			return true;
		
		return false;
	}

	private void iniGrid() {
		for (int i = 0; i < 800; i++) {
			for (int j = 0; j < 1200; j++) {
				grid[j][i] = new Cell(null);
			}
		}
	}

	public Position getPositionAt(int x, int y) {
		if (grid[x][y].getP() != null)
			return new Position(grid[x][y].getP().getPos());
		else
			return null;
	}

}
