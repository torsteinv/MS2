package no.torsteinv.MS2.Entities.Factory;

import no.torsteinv.MS2.Entities.Entity;
import no.torsteinv.MS2.Game.MS2.Buildings.FactoryBuilding;
import no.torsteinv.MS2.Main.Main;

public class TemplateUser {
	public static void use(Template t, FactoryBuilding Parent) {
		FactoryElement[] l = TemplateDecoder.decode(t, Parent);
		try {
			TemplateDecoder.buy(t);
			for (Entity e : Main.Entities)
				if (e instanceof FactoryElement
						&& ((FactoryElement) e).getHome().equals(Parent))
					Main.removeEntity(e);
			for (FactoryElement e : l)
				Main.addEntity(e);
		} catch (Exception e1) {
			try {
				Main.sendMessage("Can not aford!", Main.player);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
