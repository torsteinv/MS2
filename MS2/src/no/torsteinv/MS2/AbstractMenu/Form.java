package no.torsteinv.MS2.AbstractMenu;

import java.util.concurrent.CopyOnWriteArrayList;

import no.torsteinv.MS2.Game.Engine.Others.PaintState;
import no.torsteinv.MS2.Game.MS2.PaintStates.MainPaintStateList;

public class Form {
	public CopyOnWriteArrayList<AbstractElement> Elements = new CopyOnWriteArrayList<AbstractElement>();
	public PaintState BelongTo = MainPaintStateList.Game;

	public Form(PaintState BelongTo) {
		this.BelongTo = BelongTo;
	}

	public void add(AbstractElement ae) {
		this.Elements.add(ae);
	}
}
