package no.torsteinv.MS2.AbstractMenu.Buttons;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class InvisibleButton extends StandardButton {
	public InvisibleButton(Point Pos, Dimension size) {
		super(Pos, "", size, "");
	}

	public InvisibleButton(Rectangle dimensions) {
		super(dimensions.getLocation(), "", dimensions.getSize(), "");
	}

	public InvisibleButton(int x, int y, int width, int height) {
		super(new Point(x, y), "", new Dimension(width, height), "");
	}

	@Override
	public void paint(Graphics g) {
		/*g.setColor(Color.cyan);
		g.drawLine(getPos().x, getPos().y, getPos().x + getSize().width,
				getPos().y + getSize().height);
		g.drawLine(getPos().x, getPos().y + getSize().height, getPos().x + getSize().width,
				getPos().y);
				*/
	}
}
