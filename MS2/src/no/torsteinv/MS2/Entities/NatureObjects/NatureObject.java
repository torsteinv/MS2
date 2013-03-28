package no.torsteinv.MS2.Entities.NatureObjects;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.HashMap;

import no.torsteinv.MS2.Entities.Entity;
import no.torsteinv.MS2.Entities.Selectable;
import no.torsteinv.MS2.Entities.Movables.Command;

public abstract class NatureObject extends Entity implements Selectable{
	public NatureObject(float x,float y) {
		super(x,y,200);
		Properties.put("CommandCenter", new Command());
		Properties.put("Hitbox", new Rectangle((int) x, (int) y, 50, 50));
		Properties.put("Selected", false);
	}
	
	public NatureObject(HashMap<String, Object> Properties,
			HashMap<String, Entity> Entities) {
		super(Properties,Entities);
	}
	
	@Override
	public Color getMapColor() {
		return null;
	}
	
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
}
