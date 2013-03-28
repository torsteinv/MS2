package no.torsteinv.MS2.Entities;

import java.awt.Image;

import no.torsteinv.MS2.Game.Engine.Others.PlacementRestriction;
import no.torsteinv.MS2.Main.Main.PlaceStateTypes;

public interface Placeable {
	public PlacementRestriction getRestrictions();
	public PlaceStateTypes getPlaceType();
	public Image getTexture();
	public void OnPlaceBought(int x,int y);
}
