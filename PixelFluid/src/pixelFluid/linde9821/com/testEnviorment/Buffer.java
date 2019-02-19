package pixelFluid.linde9821.com.testEnviorment;

import pixelFluid.linde9821.com.simulation.grid.Grid;

public class Buffer {

	Double[][] grid = new Double[1200][800];

	public Buffer() {

	}

	public Double[][] getPoly(Grid g) {
		for (int i = 0; i < 799; i++) {
			for (int j = 0; j < 1199; j++) {
				grid[j][i] = g.getAlphaVal(j, i);
			}
		}

		Double[][] tempGrid = new Double[1200][800];

		for (int y = 0; y < 799; y++) {
			for (int x = 0; x < 1199; x++) {
				double vel = grid[x][y];

				for (int xx = -1; xx < 1; xx++) {
					for (int yy = -1; yy < 1; yy++) {
						if (xx == 0 && yy == 0) {
							continue; // You are not neighbor to yourself
						} else {
							vel += g.getAlphaVal(xx + x, yy + y);
						}
					}
				}
				
				tempGrid[x][y] = vel;
			}

		}

		return tempGrid;
	}
}
