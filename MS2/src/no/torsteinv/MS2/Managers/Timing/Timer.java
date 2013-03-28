package no.torsteinv.MS2.Managers.Timing;

public class Timer {
	private static long delta = 0;
	public static long getDelta(){
		return delta;
	}
	static long lastframe = 0;
	public static void updateDelta(){
		if(lastframe == 0)lastframe = System.currentTimeMillis();
		long t = System.currentTimeMillis();
		delta = t - lastframe;
		lastframe = t;
	}
}
