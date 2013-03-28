package no.torsteinv.MS2.AbstractMenu.Buttons;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import no.torsteinv.MS2.AbstractMenu.AbstractElement;

public class StandardButton extends AbstractElement {
	public boolean Active = true;
	public Color c = Color.GRAY;

	public StandardButton(Point Pos, String name, Dimension size, String ToolTip) {
		super(Pos, name, size, ToolTip);
	}

	public StandardButton(Point Pos, String name, String ToolTip) {
		super(Pos, name, ToolTip);
	}

	public StandardButton(Point Pos, String name, Dimension size,
			boolean Active, String ToolTip) {
		super(Pos, name, size, ToolTip);
		this.Active = Active;
	}

	public StandardButton(Point Pos, String name, boolean Active, String ToolTip) {
		super(Pos, name, ToolTip);
		this.Active = Active;
	}

	public StandardButton(Point Pos, String name, Dimension size,
			String ToolTip, Color c) {
		super(Pos, name, size, ToolTip);
		this.c = c;
	}

	public StandardButton(Point Pos, String name, String ToolTip, Color c) {
		super(Pos, name, ToolTip);
		this.c = c;
	}

	public StandardButton(Point Pos, String name, Dimension size,
			boolean Active, String ToolTip, Color c) {
		super(Pos, name, size, ToolTip);
		this.Active = Active;
		this.c = c;
	}

	public StandardButton(Point Pos, String name, boolean Active,
			String ToolTip, Color c) {
		super(Pos, name, ToolTip);
		this.Active = Active;
		this.c = c;
	}
	
	public StandardButton setColor(Color c){
		this.c = c;
		return this;
	}
	
	public StandardButton setActive(boolean Active){
		this.Active = Active;
		return this;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Active ? new Color(c.getRed(), c.getGreen(), c.getBlue()
				+ (this.HOVERED ? (255 - c.getBlue() > 100 ? 100 : 255 - c
						.getBlue()) : 0)) : c.darker());
		g.fill3DRect(getPos().x, getPos().y, getSize().width, getSize().height,
				true);

		g.setColor(Active ? Color.WHITE : Color.GRAY);
		g.drawString(getText(), getPos().x
				+ ((int) (getSize().width - getText().length() * 5.5f) / 2),
				getPos().y + (getSize().height + 5) / 2);
	}

}
