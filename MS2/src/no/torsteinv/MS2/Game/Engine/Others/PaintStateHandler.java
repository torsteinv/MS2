package no.torsteinv.MS2.Game.Engine.Others;

import java.awt.Graphics;

import no.torsteinv.MS2.Painting.PainterAction;

public interface PaintStateHandler {
	public static PainterAction pa = new PainterAction();
	public void paint(Graphics g);
}
