package no.torsteinv.MS2.Entities;

import java.awt.Rectangle;

import no.torsteinv.MS2.Entities.Movables.Command;
import no.torsteinv.MS2.Entities.Movables.CommandType;
import no.torsteinv.MS2.Game.Engine.Others.PaintState;

public interface Selectable {
	/**
	 * public Command CommandCenter = new Command(); public Rectangle
	 * SelectionBox; public boolean Selected = false;
	 */
	public Command getCommandCenter();

	/**
	 * public Command CommandCenter = new Command(); public Rectangle
	 * SelectionBox; public boolean Selected = false;
	 */
	public Rectangle getSelectionBox();

	/**
	 * public Command CommandCenter = new Command(); public Rectangle
	 * SelectionBox; public boolean Selected = false;
	 */
	public boolean isSelected();

	/**
	 * public Command CommandCenter = new Command(); public Rectangle
	 * SelectionBox; public boolean Selected = false;
	 */
	public void setSelected(boolean Selected);

	public PaintState getAssosiatedInterface();

	public CommandType CommandAt(int i, int j);

}
