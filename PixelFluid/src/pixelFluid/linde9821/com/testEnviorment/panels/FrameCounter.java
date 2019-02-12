package pixelFluid.linde9821.com.testEnviorment.panels;

public class FrameCounter extends Thread {
	private long period = 10000; // ms

	private double currentFramerate = -1;
	private int counter = -1;

	private long lastUpdate = -1;

	private String status = "not activ";

	public void run() {
		lastUpdate = System.currentTimeMillis();

		while (true) {
			if (period <= (System.currentTimeMillis() - lastUpdate)) {
				calcFrameRate();
				lastUpdate = System.currentTimeMillis();
				counter = 0;

				if (currentFramerate <= 2)
					status = "not activ";
				else
					status = "";
			}
		}
	}

	public void increaseCounter() {
		counter += 1;
	}

	public double getCurrentFramerate() {
		return currentFramerate;
	}

	public String getStatus() {
		return status;
	}

	public void calcFrameRate() {
		try {
			currentFramerate = counter / (System.currentTimeMillis() - lastUpdate);
		} catch (ArithmeticException e) {
		}
	}

}
