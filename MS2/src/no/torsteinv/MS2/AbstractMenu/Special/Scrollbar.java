package no.torsteinv.MS2.AbstractMenu.Special;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import no.torsteinv.MS2.AbstractMenu.AbstractElement;

public class Scrollbar extends AbstractElement {
	/**
	 * Top = 0
	 */
	public int yAmount = 56;
	public Scrollbar(Point Pos, int height) {
		super(Pos, "", new Dimension(15,height), "");
	}

	public Scrollbar(Point Pos,Dimension size) {
		super(Pos, "", size, "");
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		g.drawRect(getPos().x, getPos().y, getSize().width, getSize().height);
		g.fillRect(getPos().x, getPos().y + this.yAmount, getSize().width , 20);
	}
}
