package no.torsteinv.MS2.AbstractMenu.Labels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import no.torsteinv.MS2.AbstractMenu.AbstractElement;



public class StandardLabel extends AbstractElement {

	public StandardLabel(Point Pos, String name, Dimension size) {
		super(Pos, name, size,"");
	}
	public StandardLabel(Point Pos, String name) {
		super(Pos, name,"");
	}
	@Override
	public void paint(Graphics g) {
		g.setColor(new Color(100,100,100));
		g.fill3DRect(getPos().x, getPos().y - 20, getSize().width, getSize().height, true);
		g.setColor(Color.BLACK);
		g.drawString(getText(),getPos().x + 5,getPos().y - 5);
	}
	
}
