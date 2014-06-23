package no.torsteinv.MS2.Entities.NatureObjects;

import java.awt.Image;
import java.util.HashMap;

import no.torsteinv.MS2.Entities.Entity;
import no.torsteinv.MS2.Entities.Movables.CommandType;
import no.torsteinv.MS2.Game.Engine.Others.PaintState;
import no.torsteinv.MS2.Game.MS2.PaintStates.MainPaintStateList;
import no.torsteinv.MS2.Main.Loading.MainResourceLoading;

public class Mountain extends NatureObject {
	public static Image Mountain = null;
	public static Image MountainIron = null;
	public static Image MountainUran = null;
	public MountainType type = MountainType.Pure;

	public Mountain(float x, float y, MountainType type) {
		super(x, y);
		this.Properties.put("Type", type);
	}

	public Mountain(HashMap<String, Object> Properties,
			HashMap<String, Entity> Entities) {
		super(Properties, Entities);
	}

	@Override
	public Image getTexture() {
		return this.type.img;
	}

	public static void loadImages() {
		Mountain = MainResourceLoading.images.get("Mountain");
		MountainIron = MainResourceLoading.images.get("MountainIron");
		MountainUran = MainResourceLoading.images.get("MountainUran");
	}

	@Override
	public PaintState getAssosiatedInterface() {
		return MainPaintStateList.Game;
	}

	@Override
	public CommandType CommandAt(int i, int j, boolean leftClick) {
		return CommandType.None;
	}

	@Override
	public Class<? extends Entity> getIdentification() {
		return getClass();
	}
}
