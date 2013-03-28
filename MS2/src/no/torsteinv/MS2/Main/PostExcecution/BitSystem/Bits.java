package no.torsteinv.MS2.Main.PostExcecution.BitSystem;

import java.util.ArrayList;

public class Bits {
	boolean[] data;
	int loadedBits = 0;

	public Bits(boolean[] data) {
		this.data = data;
		loadedBits = data.length;
	}

	public boolean isIncluded(int player, boolean isConverted) {
		try {
			return data[player];
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	}

	public int getData() {
		int product = 0;
		for (int i = 0; i < data.length; i++)
			product += data[i] ? Math.pow(i, 2) : 0;
		return product;
	}

	public int getLoadedBits() {
		return loadedBits;
	}

	public Integer[] getPlayers() {
		ArrayList<Integer> l = new ArrayList<Integer>();
		for(int i = 0;i < loadedBits;i++)
			if(data[i])l.add(i);
		return l.toArray(new Integer[]{});
	}
}
