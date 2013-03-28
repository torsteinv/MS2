package no.torsteinv.MS2.Entities.Buildings;

import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.HashMap;

import no.torsteinv.MS2.Annotations.CostMethod;
import no.torsteinv.MS2.Entities.Entity;
import no.torsteinv.MS2.Entities.Placeable;
import no.torsteinv.MS2.Entities.Selectable;
import no.torsteinv.MS2.Entities.Items.BaseItem;
import no.torsteinv.MS2.Entities.Movables.Command;
import no.torsteinv.MS2.Game.Engine.Others.PlacementRestriction;
import no.torsteinv.MS2.Game.MS2.Items.MainItemList;
import no.torsteinv.MS2.Main.Main.PlaceStateTypes;

public abstract class Building extends Entity implements Selectable, Placeable {
	private static final int BuildingWidth = 50;
	private static final int BuildingHeight = 50;

	@Override
	public abstract Color getMapColor();

	@Override
	public abstract Image getTexture();

	public Building(float x, float y, int player) {
		super(x, y, player);
		this.Properties.put("Hitbox", new Rectangle((int) x, (int) y,
				BuildingWidth, BuildingHeight));

		this.Properties.put("CommandCenter", new Command());

		this.Properties.put("Selected", false);
	}

	public Building() {
		super(0, 0, 0);
	}

	public Building(HashMap<String, Object> properties,
			HashMap<String, Entity> entities) {
		super(properties, entities);
	}

	@CostMethod
	public static HashMap<BaseItem, Integer> cost() {
		HashMap<BaseItem, Integer> hm = new HashMap<BaseItem, Integer>();
		hm.put(MainItemList.Iron_Machine_Base, 1);
		hm.put(MainItemList.Steel, 10);
		hm.put(MainItemList.Igniter_Mechanism, 1);
		hm.put(MainItemList.Brick, 20);
		return hm;
	}

	@Override
	public Command getCommandCenter() {
		return (Command) this.Properties.get("CommandCenter");
	}

	@Override
	public Rectangle getSelectionBox() {
		return (Rectangle) this.Properties.get("Hitbox");
	}

	@Override
	public boolean isSelected() {
		return (boolean) this.Properties.get("Selected");
	}

	@Override
	public void setSelected(boolean Selected) {
		this.Properties.put("Selected", Selected);
	}

	@Override
	public PlaceStateTypes getPlaceType() {
		return PlaceStateTypes.GameEntity;
	}

	@Override
	public PlacementRestriction getRestrictions() {
		return PlacementRestriction.NON_OVERLAP_GAME;
	}

	@Override
	public void OnPlaceBought(int x, int y) {
	}
}
