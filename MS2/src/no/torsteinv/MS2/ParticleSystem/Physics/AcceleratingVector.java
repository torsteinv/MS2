package no.torsteinv.MS2.ParticleSystem.Physics;

import java.awt.Point;

public class AcceleratingVector extends Vector {

	public float start = 0;
	public float acceleration = 0;
	public AcceleratingVector(Point p1, Point p2) {
		super(p1, p2);
		start = System.currentTimeMillis();
		acceleration = magnitude;
		magnitude = acceleration * ((System.currentTimeMillis() - start) / 1000);
	}

	public AcceleratingVector(float angle, float magnitude) {
		super(angle, magnitude);
		start = System.currentTimeMillis();
		acceleration = magnitude;
		magnitude = acceleration * ((System.currentTimeMillis() - start) / 1000);
	}
	
	

}
