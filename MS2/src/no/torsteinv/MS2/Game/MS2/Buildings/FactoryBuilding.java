package no.torsteinv.MS2.Game.MS2.Buildings;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;

import no.torsteinv.MS2.Entities.Entity;
import no.torsteinv.MS2.Entities.Buildings.ClickableBuilding;
import no.torsteinv.MS2.Entities.Factory.FactoryElement;
import no.torsteinv.MS2.Entities.Movables.CommandType;
import no.torsteinv.MS2.Game.Engine.Others.PaintState;
import no.torsteinv.MS2.Game.MS2.PaintStates.MainPaintStateList;
import no.torsteinv.MS2.Main.Main;
import no.torsteinv.MS2.Main.Loading.MainResourceLoading;
import no.torsteinv.MS2.Wrappers.ContainerBuildingWrapper;

public class FactoryBuilding extends ContainerBuildingWrapper implements
		ClickableBuilding {

	public FactoryBuilding(int X, int Y, int Player) throws IOException {
		super(X, Y, Player);
		this.Properties.put("Texture",
				MainResourceLoading.images.get("FactoryBuilding"));
	}

	public FactoryBuilding(HashMap<String, Object> Properties,
			HashMap<String, Entity> Entities) {
		super(Properties, Entities);
	}

	@Override
	public Color getMapColor() {
		return Color.RED;
	}

	@Override
	public Image getTexture() {
		return (Image) this.Properties.get("Texture");
	}

	@Override
	public void OnClick() {
		Main.setPaintState(MainPaintStateList.Factory);
		Main.Interfaced = this;
	}

	@Override
	public PaintState getAssosiatedInterface() {
		return MainPaintStateList.Factory;
	}

	@Override
	public CommandType CommandAt(int i, int j, boolean leftClick) {
		return CommandType.None;
	}

	@Override
	public Class<? extends Entity> getIdentification() {
		return getClass();
	}

	public FactoryElement getFactoryElementAtPos(int i, int j) {
		for (Entity e : Main.Entities)
			if (e instanceof FactoryElement && e.getX() == i && e.getY() == j
					&& e.Entities.get("Home").equals(this))
				return (FactoryElement) e;
		return null;
	}
}
