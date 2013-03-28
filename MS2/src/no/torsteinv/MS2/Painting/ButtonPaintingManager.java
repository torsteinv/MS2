package no.torsteinv.MS2.Painting;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import no.torsteinv.MS2.AbstractMenu.AbstractElement;
import no.torsteinv.MS2.AbstractMenu.Form;
import no.torsteinv.MS2.AbstractMenu.Labels.StandardLabel;
import no.torsteinv.MS2.Main.Main;

public class ButtonPaintingManager {
	public static void paintButtons(Graphics g) {
		for (Form f : Main.Access.Forms)
			for (AbstractElement ae : f.Elements)
				if (Main.getPaintState() == f.BelongTo)
					ae.paint(g);
		for (Form f : Main.Access.Forms)
			for (AbstractElement ae : f.Elements)
				if (Main.getPaintState() == f.BelongTo && ae.HOVERED
						&& !(ae instanceof StandardLabel)) {
					String toolTip = ae.getToolTip();
					if(toolTip.equals(""))
						return;
					FontMetrics fontMetrics = g.getFontMetrics();
					int x = Main.Mouse_X;
					int width = fontMetrics.stringWidth(toolTip) + 10;
					int height = fontMetrics.getHeight() + 15;
					if (x + width > Main.Width) {
						x = Main.Width - width;
					}
					boolean up = Main.Height - Main.Mouse_Y + 15 < 2 * height;
					g.setColor(Color.DARK_GRAY);
					g.fillRect(x - 10, up ? Main.Mouse_Y - height - 5
							: Main.Mouse_Y + 15, width, height);
					g.setColor(Color.WHITE);
					g.drawString(toolTip, x - 5, Main.Mouse_Y
							- (up ? height + 20 : 0) + height / 2 + 20);

				}
	}
}