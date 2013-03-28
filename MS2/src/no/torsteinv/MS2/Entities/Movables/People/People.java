package no.torsteinv.MS2.Entities.Movables.People;

import java.util.HashMap;

import no.torsteinv.MS2.Entities.Entity;
import no.torsteinv.MS2.Entities.Movables.Movable;
import no.torsteinv.MS2.Main.Loading.MainResourceLoading;

public abstract class People extends Movable {

	private People(float x, float y, int player, String TextureTag,
			int discoverPower) {
		super(x, y, player, discoverPower);
		Properties.put("Texture",MainResourceLoading.images.get(TextureTag));
		getSelectionBox().setBounds((int) x, (int) y, getAbsoluteTexture().getWidth(null),
				getAbsoluteTexture().getHeight(null));
	}
	
	public People(HashMap<String, Object> Properties,
			HashMap<String, Entity> Entities) {
		super(Properties,Entities);
	}
}
