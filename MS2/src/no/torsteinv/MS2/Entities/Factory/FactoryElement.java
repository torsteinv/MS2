package no.torsteinv.MS2.Entities.Factory;

import java.awt.Color;
import java.awt.Image;
import java.util.HashMap;

import no.torsteinv.MS2.Entities.Container;
import no.torsteinv.MS2.Entities.Entity;
import no.torsteinv.MS2.Entities.Placeable;
import no.torsteinv.MS2.Game.MS2.Buildings.FactoryBuilding;
import no.torsteinv.MS2.Main.Main;

public abstract class FactoryElement extends Entity implements Placeable,
		Container {

	public FactoryElement(float x, float y, int player) {
		super(x, y, player);
	}

	public FactoryElement(HashMap<String, Object> Properties,
			HashMap<String, Entity> Entities) {
		super(Properties, Entities);
	}

	public abstract void transitAll();

	public static FactoryElement FindFactoryElementAt(float x, float y) {
		for (Entity p : Main.Entities)
			if (p instanceof FactoryElement
					&& ((FactoryElement) p).getCanvasX() == x
					&& ((FactoryElement) p).getCanvasY() == y)
				return (FactoryElement) p;
		return null;
	}

	public boolean PositionByDirection(int CX, int CY, int d) {
		if (d == 0 && (CX == getCanvasX() && CY == getCanvasY() + 1))
			return true;
		else if (d == 1 && (CX == getCanvasX() && CY == getCanvasY() - 1))
			return true;
		else if (d == 2 && (CX == getCanvasX() - 1 && CY == getCanvasY()))
			return true;
		else if (d == 3 && (CX == getCanvasX() + 1 && CY == getCanvasY()))
			return true;
		return false;
	}

	@Override
	public abstract Image getTexture();

	public abstract Color decodeColor();

	public int getCanvasX() {
		return ((Float) this.Properties.get("X")).intValue();
	}

	public int getCanvasY() {
		return ((Float) this.Properties.get("Y")).intValue();
	}

	public int getFrom() {
		return (int) this.Properties.get("From");
	}

	public FactoryBuilding getHome() {
		return (FactoryBuilding) this.Entities.get("Home");
	}

	public void setFrom(int i) {
		this.Properties.put("From", i);
	}

	@Override
	public Color getMapColor() {
		return null;
	}

}
