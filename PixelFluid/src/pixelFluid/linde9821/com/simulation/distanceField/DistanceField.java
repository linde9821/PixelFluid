package pixelFluid.linde9821.com.simulation.distanceField;

import pixelFluid.linde9821.com.simulation.Vector;
import pixelFluid.linde9821.com.simulation.grid.Grid;
import pixelFluid.linde9821.com.simulation.particel.Position;

public class DistanceField {

	private Cell[][] distanceField = new Cell[1200][800];
	private Grid grid;

	public DistanceField(Grid grid) {
		iniDistanceField();
		this.grid = grid;
	}

	public Vector getNormal(int index) {
		return new Vector(1, 0); // values only for normal gravity
	}

	public int getIndex(Position p) {
		for (int i = 0; i < 800; i++) {
			for (int j = 0; j < 1200; j++) {
				if (grid.getPositionAt(j, i) == p)
					return (i + j);
			}
		}

		return -1;
	}

	public double getDistance(int index) {
		return 0.0;
	}

	private void iniDistanceField() {
		for (int i = 0; i < 800; i++) {
			for (int j = 0; j < 1200; j++) {
				distanceField[j][i] = new Cell(j + i);

				int[] d = new int[4];

				d[0] = j; // distance to left
				d[1] = i; // distance to up
				d[2] = 1200 - d[0]; // distance to right
				d[3] = 800 - d[1]; // distance to bot

				distanceField[j][i].setDistance(findMin(d));
			}
		}
	}

	private int findMin(int[] ar) {
		int min = ar[0];
		for (int i = 1; i < 4; i++) {
			if (ar[i - 1] > ar[i])
				min = ar[i];
		}

		return min;
	}
}
