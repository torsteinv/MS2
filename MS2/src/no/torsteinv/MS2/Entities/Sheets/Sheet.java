package no.torsteinv.MS2.Entities.Sheets;

import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.HashMap;

import no.torsteinv.MS2.Entities.Entity;
import no.torsteinv.MS2.Entities.Placeable;
import no.torsteinv.MS2.Entities.Selectable;
import no.torsteinv.MS2.Entities.Movables.Command;
import no.torsteinv.MS2.Main.Main.PlaceStateTypes;

public abstract class Sheet extends Entity implements Selectable, Placeable{

	public Sheet(int x, int y, int width, int height, int player) {
		super(x, y, player);
		Properties.put("Done", false);
		Properties.put("CommandCenter", new Command());
		Properties.put("Hitbox", new Rectangle((int) x, (int) y, width, height));
		Properties.put("Selected", false);
	}
	
	public Sheet(HashMap<String, Object> Properties,
			HashMap<String, Entity> Entities) {
		super(Properties,Entities);
	}

	public abstract Color getMapColor();

	public abstract Color gameColor();

	public abstract Image getTexture();

	@Override
	public Command getCommandCenter() {
		return (Command) Properties.get("CommandCenter");
	}

	@Override
	public Rectangle getSelectionBox() {
		return (Rectangle) Properties.get("Hitbox");
	}

	@Override
	public boolean isSelected() {
		return (boolean) Properties.get("Selected");
	}

	@Override
	public void setSelected(boolean Selected) {
		Properties.put("Selected", Selected);
	}
	
	@Override
	public PlaceStateTypes getPlaceType(){
		return PlaceStateTypes.Sheet;
	}
}
