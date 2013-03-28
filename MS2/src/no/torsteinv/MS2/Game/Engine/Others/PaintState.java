package no.torsteinv.MS2.Game.Engine.Others;

import java.awt.Graphics;

public class PaintState {
	PaintStateType pst[] = new PaintStateType[] { PaintStateType.MENU };

	public PaintState(PaintStateType... pst) {

	}

	public PaintStateHandler psh = new PaintStateHandler() {

		@Override
		public void paint(Graphics g) {
			pa.PaintMenu(g);
		}

	};

	public void setHandler(PaintStateHandler paintStateHandler) {
		this.psh = paintStateHandler;
	}
}
