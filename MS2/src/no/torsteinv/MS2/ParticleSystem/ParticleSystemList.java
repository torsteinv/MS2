package no.torsteinv.MS2.ParticleSystem;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class ParticleSystemList implements Iterable<Particle>{
	public static CopyOnWriteArrayList<Particle> Particles = new CopyOnWriteArrayList<Particle>();
	public static void add(Particle p){
		Particles.add(p);
	}
	public static void remove(Particle p){
		Particles.remove(p);
	}
	public static Particle get(int index){
		return Particles.get(index);
	}
	public Iterator<Particle> iterator() {
		return Particles.iterator();
	}
}
