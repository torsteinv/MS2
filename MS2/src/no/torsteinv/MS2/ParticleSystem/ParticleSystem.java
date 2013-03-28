package no.torsteinv.MS2.ParticleSystem;

import java.awt.Color;

import no.torsteinv.MS2.Entities.Entity;
import no.torsteinv.MS2.Main.Main;

public class ParticleSystem {
	public static final Emitter UranMountain = new Emitter(100, 100, 1, 1, -1,
			EmitterType.Still, Color.YELLOW);

	public static Emitter addEmitter(Emitter e, int x, int y, Entity key) {
		e.x = x;
		e.y = y;
		try {
			Main.addEmitter(e,key);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return e;
	}
}
