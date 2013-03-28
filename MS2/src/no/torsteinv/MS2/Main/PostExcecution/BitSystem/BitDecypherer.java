package no.torsteinv.MS2.Main.PostExcecution.BitSystem;

import java.util.Arrays;

public class BitDecypherer {
	public static final int ALL = -200;

	public static Bits Decypher(int i) {
		int loadedBits = 1;
		for (int in = 1; in < i; in += in)
			loadedBits++;
		return Decypher(i, loadedBits);
	}

	public static Bits Decypher(int i, int loadedBits) {
		if (i == ALL) {
			return new Bits(new boolean[] { true, true, true, true, true, true,
					true, true, true, true, true, true, true, true, true, true,
					true, true, true, true, true, true, true, true, true, true,
					true, true, true, true, true, true, true, true, true, true,
					true, true, true, true, true, true, true, true, true, true,
					true, true, true, true, true, true, true, true, true, true,
					true, true, true, true, true, true, true, true, true, true,
					true, true, true, true, true, true, true, true, true, true,
					true, true, true, true, true, true, true, true, true, true,
					true, true, true, true, true, true, true, true, true, true,
					true, true, true, true });
		} else if (i == 0) {
			return new Bits(new boolean[] { true });
		}
		boolean[] data = new boolean[loadedBits];
		int prev = i;
		for (int in = loadedBits; in <= 0; in--) {
			int remainder = prev % 2;
			int nprev = (int) prev / 2;
			data[in] = remainder != 0;
			prev = nprev;
		}
		return new Bits(data);
	}

	public static Bits createBitSet(boolean... data) {
		return new Bits(data);
	}

	public static Bits createBitSet(int loadedBits, int... players) {
		if (loadedBits == ALL) {
			return new Bits(new boolean[] { true, true, true, true, true, true,
					true, true, true, true, true, true, true, true, true, true,
					true, true, true, true, true, true, true, true, true, true,
					true, true, true, true, true, true, true, true, true, true,
					true, true, true, true, true, true, true, true, true, true,
					true, true, true, true, true, true, true, true, true, true,
					true, true, true, true, true, true, true, true, true, true,
					true, true, true, true, true, true, true, true, true, true,
					true, true, true, true, true, true, true, true, true, true,
					true, true, true, true, true, true, true, true, true, true,
					true, true, true, true });
		}
		boolean[] product = new boolean[loadedBits];
		for (int i = 0; i < loadedBits; i++)
			product[i] = Arrays.asList(players).contains(new Integer(i));
		return createBitSet(product);
	}

	/*
	 * UNUSED
	 * 
	 * private static boolean[] convert(Object[] array) { boolean[] product =
	 * new boolean[array.length]; for(int i = 0;i < array.length;i++) product[i]
	 * = (Boolean)array[i]; return product; }
	 */
}
