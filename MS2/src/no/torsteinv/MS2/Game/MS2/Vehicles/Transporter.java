package no.torsteinv.MS2.Game.MS2.Vehicles;

import java.awt.Color;
import java.util.HashMap;

import no.torsteinv.MS2.Entities.Container;
import no.torsteinv.MS2.Entities.Entity;
import no.torsteinv.MS2.Entities.Movables.CommandType;
import no.torsteinv.MS2.Game.Engine.Others.PaintState;
import no.torsteinv.MS2.Game.MS2.PaintStates.MainPaintStateList;
import no.torsteinv.MS2.Main.Main;
import no.torsteinv.MS2.Wrappers.ContainerVehicleWrapper;

public class Transporter extends ContainerVehicleWrapper {
	public Transporter(float x, float y, int player) {
		super(x, y, player, "Transporter", 250);
	}

	public Transporter(HashMap<String, Object> Properties,
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
		return MainPaintStateList.Transporter;
	}

	@Override
	public Color getMapColor() {
		return Color.LIGHT_GRAY;
	}

	@Override
	public CommandType CommandAt(int i, int j, boolean leftClick) {
		return Main.getEntityAt(i, j) instanceof Container ? (leftClick ? CommandType.DropOff : CommandType.Take)
				: CommandType.Goto;
	}
}
