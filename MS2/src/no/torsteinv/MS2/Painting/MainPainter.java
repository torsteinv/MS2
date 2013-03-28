package no.torsteinv.MS2.Painting;

import java.awt.Graphics;
import java.awt.Image;

import no.torsteinv.MS2.Main.Main;
import no.torsteinv.MS2.Main.Loading.MainResourceLoading;

public class MainPainter {

	public static Image MarsSand = null;
	public static Image IceLakes = null;

	public MainPainter() {
		MarsSand = MainResourceLoading.images.get("MarsSand");
		IceLakes = MainResourceLoading.images.get("IceLake");
	}

	public void paint(Graphics g) {
		Main.getPaintState().psh.paint(g);
		ButtonPaintingManager.paintButtons(g);
	}
}
