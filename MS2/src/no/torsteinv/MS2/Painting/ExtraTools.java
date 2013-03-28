package no.torsteinv.MS2.Painting;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import no.torsteinv.MS2.Main.Main;

public class ExtraTools {
	public static void createTooltip(String text,int x,int y,Graphics paintTo){
		if(text.equals(""))
			return;
		FontMetrics fontMetrics = paintTo.getFontMetrics();
		int width = fontMetrics.stringWidth(text) + 10;
		int height = fontMetrics.getHeight() + 15;
		if (x + width > Main.Width) {
			x = Main.Width - width;
		}
		boolean up = Main.Height - y + 15 < 2 * height;
		paintTo.setColor(Color.DARK_GRAY);
		paintTo.fillRect(x - 10, up ? y - height - 5
				: Main.Mouse_Y + 15, width, height);
		paintTo.setColor(Color.WHITE);
		paintTo.drawString(text, x - 5, y
				- (up ? height + 20 : 0) + height / 2 + 20);
	}
}
