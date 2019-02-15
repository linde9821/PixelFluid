package pixelFluid.linde9821.com.simulation.distanceField;

import pixelFluid.linde9821.com.simulation.Vector;
import pixelFluid.linde9821.com.simulation.grid.Grid;
import pixelFluid.linde9821.com.simulation.particel.Position;

public class DistanceField {
	private int width, height;
	private Cell[] distanceField = new Cell[1200 * 800];

	public DistanceField() {
		width = 1200;
		height = 800;
		generateDistancField();
	}
	
	private void generateDistancField() {
		int index = 0;
		int[] d = null;
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				distanceField[index] = new Cell(index);
				
				d = new int[4];
				
				d[0] = j; // distance to left
				d[1] = i; // distance to up
				d[2] = 1200 - d[0]; // distance to right
				d[3] = 800 - d[1]; // distance to bot
				
				distanceField[index].setDistance(findMin(d));
				
				if (i == 0 && j == 0)
					distanceField[index].setNormal(new Vector(1, 1));
				else if (i == 0 && j == 1199)
					distanceField[index].setNormal(new Vector(-1, 1));
				else if (i == 799 && j==0)
					distanceField[index].setNormal(new Vector(1, -1));
				else if(i == 799 && j == 1119)
					distanceField[index].setNormal(new Vector(-1, -1));
				else if (i == 799)
					distanceField[index].setNormal(new Vector(0, -1));
				else if (i == 0)
					distanceField[index].setNormal(new Vector(0, 1));
				else if (j == 0)
					distanceField[index].setNormal(new Vector(1, 0));
				else if (j == 1199)
					distanceField[index].setNormal(new Vector(-1, 0));
				else
					distanceField[index].setNormal(new Vector(0, 0));
				
				
				index ++;
			}
		}
	}

	public Vector getNormal(int index) {
		return distanceField[index].getNormal();
	}

	public int getIndex(Position p) {
		int x = (int) p.getX();
		int y = (int) p.getY();
		
		//check for out of bounce values and look for better implementation
		
		int index = 0;
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (i == y && j == x)
					return index;
				
				index++;
			}
		}

		return -1;
	}

	public double getDistance(int index) {
		return distanceField[index].getDistance();
	}
	

	private int findMin(int[] ar) {
		int min = ar[0];
		
		for (int i = 1; i < 4; i++) {
			if (min > ar[i])
				min = ar[i];
		}

		return min;
	}
}
