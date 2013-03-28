package no.torsteinv.MS2.AbstractMenu.Buttons;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

public class ToggleButton extends StandardButton{

	public ToggleButton(Point Pos, String name, Dimension size, String ToolTip) {
		super(Pos, name, size, ToolTip);
	}

	public ToggleButton(Point Pos, String name, String ToolTip) {
		super(Pos, name, ToolTip);
	}

	public ToggleButton(Point Pos, String name, Dimension size,
			boolean Active, String ToolTip) {
		super(Pos, name, size, ToolTip);
		this.Active = Active;
	}

	public ToggleButton(Point Pos, String name, boolean Active, String ToolTip) {
		super(Pos, name, ToolTip);
		this.Active = Active;
	}

	public ToggleButton(Point Pos, String name, Dimension size,
			String ToolTip, Color c) {
		super(Pos, name, size, ToolTip);
		this.c = c;
	}

	public ToggleButton(Point Pos, String name, String ToolTip, Color c) {
		super(Pos, name, ToolTip);
		this.c = c;
	}

	public ToggleButton(Point Pos, String name, Dimension size,
			boolean Active, String ToolTip, Color c) {
		super(Pos, name, size, ToolTip);
		this.Active = Active;
		this.c = c;
	}

	public ToggleButton(Point Pos, String name, boolean Active,
			String ToolTip, Color c) {
		super(Pos, name, ToolTip);
		this.Active = Active;
		this.c = c;
	}

}
