package no.torsteinv.MS2.Entities.NatureObjects;

import java.awt.Image;
import java.util.HashMap;

import no.torsteinv.MS2.Entities.Entity;
import no.torsteinv.MS2.Entities.Movables.CommandType;
import no.torsteinv.MS2.Game.Engine.Others.PaintState;
import no.torsteinv.MS2.Game.MS2.PaintStates.MainPaintStateList;
import no.torsteinv.MS2.Painting.MainPainter;

public class IceLakes extends NatureObject {

	public IceLakes(float x, float y) {
		super(x, y);
	}

	public IceLakes(HashMap<String, Object> Properties,
			HashMap<String, Entity> Entities) {
		super(Properties, Entities);
	}

	@Override
	public Image getTexture() {
		return MainPainter.IceLakes;
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
