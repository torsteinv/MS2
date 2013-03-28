package no.torsteinv.MS2.ParticleSystem.Physics;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import no.torsteinv.MS2.Entities.Entity;
import no.torsteinv.MS2.Main.Main;
import no.torsteinv.MS2.Managers.Timing.Timer;
import no.torsteinv.MS2.ParticleSystem.Emitter;
import no.torsteinv.MS2.ParticleSystem.EmitterType;
import no.torsteinv.MS2.ParticleSystem.Particle;
import no.torsteinv.MS2.ParticleSystem.ParticleSystemList;

public class Physics {
	public static final AcceleratingVector Gravity = new AcceleratingVector(
			(float) Math.toRadians(359.99999), 9.14f);
	public static final AcceleratingVector NegativeGravity = new AcceleratingVector(
			(float) Math.toRadians(179.99999), 9.14f);

	public static HashMap<Entity,Emitter> visibleEmitters = new HashMap<Entity,Emitter>();
	
	
	static float PREV_HOR = 0;
	static float PREV_VER = 0;
	public static void update() {
		if(PREV_HOR != Main.HorisontalAlignment || PREV_VER != Main.VerticalAlignment)
			updateVisibleEmitter();
		addAll();
		//updateHashMap();
		updateAcceleratingVectors();
		updateVectorsToCompile();
		updateEmitterLifeTime();
		animateParticles();
		removeAll();
		PREV_HOR = Main.HorisontalAlignment;
		PREV_VER = Main.VerticalAlignment;
	}
	
	private static void updateVisibleEmitter() {
		Rectangle r = new Rectangle((int)Main.HorisontalAlignment - 300,(int)Main.VerticalAlignment - 300,600 + 300,600 + 300);
		for(Entry<Entity, Emitter> e : visibleEmitters.entrySet())
			if(r.contains(new Point(e.getValue().x,e.getValue().y)))
				visibleEmitters.put(e.getKey(),e.getValue());
	}

	private static CopyOnWriteArrayList<Entity> EntitiesCache;

	//Non-functional
	@SuppressWarnings("unused")
	private static void updateHashMap() {
		if(EntitiesCache == null)
			EntitiesCache = Main.Entities;
		for (Entity e : Main.Entities) {
			for (Entity e2 : visibleEmitters.keySet())
				if (e2.equals(EntitiesCache.get(Main.Entities.indexOf(e))))
					e2 = e;
			Emitter em = visibleEmitters.get(EntitiesCache.get(Main.Entities
					.indexOf(e)));
			if(em == null)continue;
			em.x = (int) (e.getX() + 25);
			em.y = (int) (e.getY() + 25);
			
		}
		EntitiesCache = Main.Entities;
	}
	

	private static void updateEmitterLifeTime() {
		for (Emitter e : visibleEmitters.values())
			if (e.lifetime != -1) {
				if (e.lifetime <= 0)
					try {
						Main.removeEmitter(e);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				else
					e.lifetime -= Timer.getDelta();
			}
	}

	private static void removeAll() {
		if(Timer.getDelta() > 1000)
			ParticleSystemList.Particles.clear();
		for (Particle p : ParticleSystemList.Particles)
			if (new Random().nextInt(100) == 13)
				ParticleSystemList.remove(p);
	}

	private static void addAll() {
		for (int in = 0;in < visibleEmitters.values().size();in++)
			for (int i = 0; i < ((Emitter)visibleEmitters.values().toArray()[in]).spread; i++)
				add((Emitter)visibleEmitters.values().toArray()[in]);
	}

	private static void animateParticles() {
		for (Particle p : ParticleSystemList.Particles) {
			float xv = (float) (Math.cos(p.finalVector.angle) * (p.finalVector.magnitude));
			// * (Timer.getDelta() / 1000)));
			float yv = (float) (Math.sin(p.finalVector.angle) * (p.finalVector.magnitude));
			// * (Timer.getDelta() / 1000)));
			p.x += xv;
			p.y += yv;
		}
	}

	private static void updateVectorsToCompile() {
		for (Particle p : ParticleSystemList.Particles)
			MakeOne(p);
	}

	private static void updateAcceleratingVectors() {
		for (Particle p : ParticleSystemList.Particles)
			for (Vector v : p.Vectors)
				if (v instanceof AcceleratingVector)
					((AcceleratingVector) v).magnitude += ((AcceleratingVector) v).acceleration
							* (Timer.getDelta() / 1000);
	}

	private static void add(Emitter e) {
		Vector PushForce = new Vector(
				(float) ((new Random()).nextFloat() * Math.toRadians(360)),
				e.force);
		ParticleSystemList.add(new Particle(e.generateTexture(), e.x, e.y,
				e.type == EmitterType.Smoke ? Gravity
						: e.type == EmitterType.Stream ? NegativeGravity
								: new Vector(0, 0), PushForce));
	}

	private static void MakeOne(Particle p) {
		p.finalVector = compile(convertArray(p.Vectors.toArray()));
	}

	private static Vector[] convertArray(Object[] obj) {
		Vector[] vecs = new Vector[obj.length];
		for (int i = 0; i < obj.length; i++)
			vecs[i] = (Vector) obj[i];
		return vecs;
	}

	public static Vector compile(Vector[] vectors) {
		Vector current = vectors[0];
		for (int i = 1; i < vectors.length; i++)
			current = Vector.add(current, vectors[i]);
		return current;
	}
}
