package no.torsteinv.MS2.Input;

import java.awt.event.MouseWheelEvent;

import no.torsteinv.MS2.Main.Main;

public class MouseWheelHandler implements java.awt.event.MouseWheelListener {

	@SuppressWarnings("unused")
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (/*Main.game()*/false) {
			Main.zoom -= e.getWheelRotation();
			Main.HorisontalAlignment += (e.getWheelRotation() <= 0 ? (Main.Mouse_X - ((Main.Width - 200)) / 2) / 2
					: -((Main.Mouse_X - ((Main.Width - 200)) / 2) / 2))
					/ Main.zoom;
			Main.VerticalAlignment += (e.getWheelRotation() <= 0 ? (Main.Mouse_Y - ((Main.Height)) / 2) / 2
					: -((Main.Mouse_Y - ((Main.Height)) / 2) / 2))
					/ Main.zoom;
		}
	}
}
