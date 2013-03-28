package no.torsteinv.MS2.AbstractMenu.InputForms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import no.torsteinv.MS2.AbstractMenu.AbstractElement;

public class StandardInputForm extends AbstractElement {
	public boolean originalText = true;

	public StandardInputForm(Point Pos, String name, Dimension size,
			String toolTip) {
		super(Pos, name, size, toolTip);
	}

	public StandardInputForm(Point Pos, String name, String toolTip) {
		super(Pos, name, toolTip);
	}

	public StandardInputForm(Point Pos, String name, Dimension size,
			boolean Hidden, String toolTip) {
		super(Pos, name, size, toolTip);
		this.HIDDEN = Hidden;
	}

	public StandardInputForm(Point Pos, String name, boolean Hidden,
			String toolTip) {
		super(Pos, name, toolTip);
		this.HIDDEN = Hidden;
	}

	int clock1 = 0;

	int clock2 = 0;
	@Override
	public void paint(Graphics g) {
		this.clock2++;
		if (this.clock1 == 100 && this.FOCUS) {
			g.setColor(Color.WHITE);
			g.drawLine(getPos().x + 10, getPos().y, getPos().x + 20, getPos().y);
		}
		this.clock1 += this.clock1 == 100 ? -this.clock1 : 1;
		g.setColor(Color.WHITE);
		g.fillRect(getPos().x, getPos().y, getSize().width, getSize().height);

		g.setColor(Color.BLACK);

		if(this.FOCUS && this.clock2 % 20 < 10)
			g.drawLine(11 + g.getFontMetrics().stringWidth(getText()) + getPos().x, getPos().y + 2,11 + g.getFontMetrics().stringWidth(getText()) +  + getPos().x, getSize().height + getPos().y - 3);
		
		g.drawRect(getPos().x, getPos().y, getSize().width, getSize().height);

		g.drawString(
				g.getFontMetrics().stringWidth(getText()) > getSize().width ? getText()
						.substring((getText().length() - getSize().width / 6))
						: getText(), getPos().x + 10, getPos().y
						+ (getSize().height + 6) / 2);
	}
}
