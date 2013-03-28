package no.torsteinv.MS2.ParticleSystem;

import java.awt.Color;
import java.util.ArrayList;

import no.torsteinv.MS2.ParticleSystem.Physics.Vector;

public class Particle {
	public Particle(Color c, float x, float y,Vector...Vectors) {
		this.c = c;
		this.x = x;
		this.y = y;
		for(Vector v : Vectors)
			this.Vectors.add(v);
		finalVector = new Vector((float)Math.toRadians(270),1f);
				//Physics.compile(Vectors);
	}
	public Vector finalVector = new Vector(0,0);
	public ArrayList<Vector> Vectors = new ArrayList<Vector>();
	public Color c = Color.BLACK;
	public float x = 0;
	public float y = 0;
}
