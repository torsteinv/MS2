package no.torsteinv.MS2.Game.MS2.Buildings;

import java.awt.Color;
import java.awt.Image;
import java.util.HashMap;

import no.torsteinv.MS2.Entities.Entity;
import no.torsteinv.MS2.Entities.Buildings.Building;
import no.torsteinv.MS2.Entities.Buildings.ClickableBuilding;
import no.torsteinv.MS2.Entities.Movables.CommandType;
import no.torsteinv.MS2.Game.Engine.Others.PaintState;
import no.torsteinv.MS2.Game.Engine.Others.PlacementRestriction;
import no.torsteinv.MS2.Game.MS2.PaintStates.MainPaintStateList;
import no.torsteinv.MS2.Main.Main;
import no.torsteinv.MS2.Main.Loading.MainResourceLoading;

public class QuarryBuilding extends Building implements ClickableBuilding {

	public QuarryBuilding(int X, int Y, int Player) {
		super(X, Y, Player);
		this.Properties.put("Texture",
				MainResourceLoading.images.get("SandQuarry"));
	}

	public QuarryBuilding(HashMap<String, Object> Properties,
			HashMap<String, Entity> Entities) {
		super(Properties, Entities);
	}

	@Override
	public Color getMapColor() {
		return Color.YELLOW;
	}

	@Override
	public Image getTexture() {
		return (Image) this.Properties.get("Texture");
	}

	@Override
	public void OnClick() {
		Main.setPaintState(MainPaintStateList.SandQuarry);
		Main.Interfaced = this;
	}

	public QuarryBuilding() {
	}

	@Override
	public PaintState getAssosiatedInterface() {
		return MainPaintStateList.SandQuarry;
	}

	@Override
	public CommandType CommandAt(int i, int j, boolean leftClick) {
		return CommandType.None;
	}

	@Override
	public Class<? extends Entity> getIdentification() {
		return getClass();
	}
	
	@Override
	public PlacementRestriction getRestrictions(){
		return PlacementRestriction.NON_OVERLAP_GAME;
	}
}
