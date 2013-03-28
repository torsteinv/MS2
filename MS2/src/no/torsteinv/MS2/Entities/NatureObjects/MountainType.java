package no.torsteinv.MS2.Entities.NatureObjects;

import java.awt.Image;
import java.util.Random;

public enum MountainType {
	Pure(Mountain.Mountain),
	Iron(Mountain.MountainIron),
	Uran(Mountain.MountainUran);
	public static MountainType[] types = {Pure,Iron,Uran};
	public Image img = null;
	MountainType(Image img){
		this.img = img;
	}
	public static MountainType Parse(String str){
		for(MountainType t : types)
			if(t.toString().equals(str.toString()))
				return t;
		return Pure;
	}
	public static MountainType selectRandom(){
		return types[new Random().nextInt(types.length)];
	}
}
