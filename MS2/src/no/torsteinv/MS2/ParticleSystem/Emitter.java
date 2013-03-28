package no.torsteinv.MS2.ParticleSystem;

import java.awt.Color;
import java.util.Random;

public class Emitter {
	public int x = 0;
	public int y = 0;
	public int force = 0;
	public int spread = 0;
	public float lifetime = 0;
	public Color[] textures = {};
	public EmitterType type = EmitterType.Smoke;

	public Emitter(int x, int y, int force, int spread,
			float lifetimeInMilliSeconds, EmitterType type, Color... textures) {
		this.x = x;
		this.y = y;
		this.force = force;
		this.textures = textures;
		this.type = type;
		this.spread = spread;
		this.lifetime = lifetimeInMilliSeconds;
	}

	public Color generateTexture() {
		int i = new Random().nextInt(textures.length);
		return textures[i];
	}

	public static Emitter Parse(String str) {
		String[] data = str.split(":");
		int x = Integer.parseInt(data[0]);
		int y = Integer.parseInt(data[1]);
		int force = Integer.parseInt(data[2]);
		int spread = Integer.parseInt(data[3]);
		float lifetime = Float.parseFloat(data[4]);
		EmitterType type = EmitterType.valueOf(data[5]);
		Color[] textures = generateArrayFromString(str.subSequence(
				str.indexOf("::") + 2, str.length()).toString());
		return new Emitter(x, y, force, spread, lifetime, type, textures);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Emitter))
			return false;
		Emitter other = (Emitter) obj;
		if (force != other.force)
			return false;
		if (spread != other.spread)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return x + ":" + y + ":" + force + ":" + spread + ":" + lifetime + ":"
				+ type + ":" + generateStringFromArray(textures);
	}

	private static String generateStringFromArray(Color[] c) {
		String s = "";
		for (Color cl : c)
			s += ":" + cl.getRGB();
		return s;
	}

	private static Color[] generateArrayFromString(String str) {
		String[] data = str.split(":");
		Color[] c = new Color[data.length];
		for (int i = 0; i < data.length; i++)
			c[i] = Color.decode(data[i]);
		return c;
	}
}
