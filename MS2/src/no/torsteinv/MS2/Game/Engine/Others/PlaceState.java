package no.torsteinv.MS2.Game.Engine.Others;

import java.awt.Image;

import no.torsteinv.MS2.Entities.Placeable;
import no.torsteinv.MS2.Main.Main.PlaceStateTypes;

public class PlaceState {

	public int ID = -1;
	public PlaceStateTypes Type = PlaceStateTypes.GameEntity;
	public Image Icon;
	public Placeable instance = null;
	public PlacementRestriction placementRestricion = PlacementRestriction.NON_OVERLAP_GAME;

	public PlaceState(int ID, PlaceStateTypes Type, Placeable e, Image Icon) {
		this.ID = ID;
		this.Type = Type;
		this.Icon = Icon;
		this.instance = e;
	}

	public PlaceState(int ID, PlaceStateTypes Type, Placeable instance) {
		this.ID = ID;
		this.Type = Type;
		this.Icon = instance.getTexture();
		this.instance = instance;
	}

	public PlaceState(int ID, PlaceStateTypes Type, Placeable instance,
			PlacementRestriction pr) {
		this.ID = ID;
		this.Type = Type;
		this.Icon = instance.getTexture();
		this.instance = instance;
		this.placementRestricion = pr;
	}

	public PlaceState(int ID, PlaceStateTypes Type, Placeable instance,
			Image Icon, PlacementRestriction pr) {
		this.ID = ID;
		this.Type = Type;
		this.Icon = Icon;
		this.instance = instance;
		this.placementRestricion = pr;
	}

}
