package no.torsteinv.MS2.Entities.EntityAttachments;

import java.awt.Color;
import java.util.HashMap;

import no.torsteinv.MS2.Entities.Entity;
import no.torsteinv.MS2.Main.Loading.MainResourceLoading;

public abstract class EntityAttachment extends Entity{
	
	public EntityAttachment(HashMap<String, Object> Properties,
			HashMap<String, Entity> Entities) {
		super(Properties,Entities);
	}
	
	public EntityAttachment(int x, int y, int player, Entity parent, String TextureTag) {
		super(x,y,player);
		Entities.put("Parent",parent);
		Properties.put("Texture",MainResourceLoading.images.get(TextureTag));
	}

	public Color getMapColor(){
		return null;
	}
}
