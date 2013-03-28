package no.torsteinv.MS2.Entities.Factory.Pipes;

import java.util.HashMap;

import no.torsteinv.MS2.Entities.Entity;
import no.torsteinv.MS2.Entities.Placeable;
import no.torsteinv.MS2.Entities.Selectable;
import no.torsteinv.MS2.Game.Engine.Others.PlacementRestriction;
import no.torsteinv.MS2.Main.Main.PlaceStateTypes;
import no.torsteinv.MS2.Wrappers.ContainerFactoryElementWrapper;

public abstract class Pipe extends ContainerFactoryElementWrapper implements
		Selectable, Placeable {

	public Pipe(HashMap<String, Object> Properties,
			HashMap<String, Entity> Entities) {
		super(Properties, Entities);
	}

	public Pipe(int x, int y, int player) {
		super(x, y, player);
	}

	@Override
	public PlaceStateTypes getPlaceType() {
		return PlaceStateTypes.Pipe;
	}

	@Override
	public PlacementRestriction getRestrictions() {
		return PlacementRestriction.NON_OVERLAP_FACTORY;
	}
	
	@Override
	public void OnPlaceBought(int x, int y) {
		this.Properties.put("From", 2);
	}

}
