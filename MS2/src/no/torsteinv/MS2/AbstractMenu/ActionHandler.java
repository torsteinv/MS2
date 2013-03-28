package no.torsteinv.MS2.AbstractMenu;

import no.torsteinv.MS2.Main.Main;

public abstract class ActionHandler {
	public void handle(){
		Handle();
		Main.mouseHandler.cooldown = 0;
	}
	public abstract void Handle();
}
