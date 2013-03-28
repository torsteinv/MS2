package no.torsteinv.MS2.ParticleSystem.Physics;

import java.awt.Point;

public class Vector {
	public float angle = 0;
	public float magnitude = 0;
	public Vector(Point p1,Point p2){
		magnitude = (float) Math.hypot(p2.x - p1.x, p2.y - p1.y);
		angle = (float) Math.atan2(p2.y - p1.y, p2.x - p1.x);
	}
	public Vector(float angle,float magnitude){
		this.magnitude = magnitude;
		this.angle = angle;
	}
	public void setAngle(float angle){
		this.angle = angle;
	}
	public void setMagnitude(float magnitude){
		this.magnitude = magnitude;
	}
	public float getAngle(){
		return angle;
	}
	public float getMagnitude(){
		return magnitude;
	}
	public static Vector add(Vector v1,Vector v2){
		float x1 = v1.magnitude * (float)Math.cos(v1.angle);
		float x2 = v2.magnitude * (float)Math.cos(v2.angle);
		
		float y1 = v1.magnitude * (float)Math.sin(v1.angle);
		float y2 = v2.magnitude * (float)Math.sin(v2.angle);
		
		float X_sum = x1 + x2;
		float Y_sum = y1 + y2;
		
		float magnitude = (float)Math.abs(Math.hypot(X_sum, Y_sum));
		float baseAngle = (float)Math.atan2(Math.abs(Y_sum), Math.abs(X_sum));
		
		int AngleAddtion = X_sum < 0 ? (Y_sum < 0 ? 2 : 1) : (Y_sum < 0 ? 3 : 0);
		float angle = (float) (baseAngle + (AngleAddtion * Math.toRadians(90)));
		return new Vector(angle,magnitude);
	}
}

