package no.torsteinv.MS2.Game.MS2.Vehicles;

import java.awt.Color;
import java.util.HashMap;

import no.torsteinv.MS2.Entities.Entity;
import no.torsteinv.MS2.Entities.Movables.CommandType;
import no.torsteinv.MS2.Entities.Movables.Vehicles.Vehicle;
import no.torsteinv.MS2.Game.Engine.Others.PaintState;
import no.torsteinv.MS2.Game.MS2.PaintStates.MainPaintStateList;

public class Explorer extends Vehicle {
	public Explorer(float x, float y, int player) {
		super(x, y, player, "Explorer", 500);
	}

	public Explorer(HashMap<String, Object> Properties,
			HashMap<String, Entity> Entities) {
		super(Properties, Entities);
	}

	@Override
	public Class<? extends Entity> getIdentification() {
		return getClass();
	}

	@Override
	public void updateOthers() {

	}

	@Override
	public PaintState getAssosiatedInterface() {
		return MainPaintStateList.Explorer;
	}

	@Override
	public Color getMapColor() {
		return Color.LIGHT_GRAY;
	}

	@Override
	public CommandType CommandAt(int i, int j, boolean leftClick) {
		return CommandType.Goto;
	}
}
