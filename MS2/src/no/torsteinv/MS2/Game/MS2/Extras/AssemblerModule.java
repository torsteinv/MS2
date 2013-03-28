package no.torsteinv.MS2.Game.MS2.Extras;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;

import no.torsteinv.MS2.Annotations.EntityProperties;
import no.torsteinv.MS2.Entities.Entity;

@EntityProperties(concrete = false)
public class AssemblerModule extends Entity{ 
	
	public AssemblerModule(AssemblerData data) throws IOException {
		super(0, 0, 0);
		this.Properties.put("AssemblerModuleSelected",null);
		this.Properties.put("",null);
		this.Properties.put("",null);
		this.Properties.put("",null);
		this.Properties.put("",null);
	}

	public AssemblerModule(HashMap<String, Object> Properties,
			HashMap<String, Entity> Entities) {
		super(Properties, Entities);
	}

	@Override
	@Deprecated
	public Color getMapColor() {
		return null;
	}

	@Override
	@Deprecated
	public Class<? extends Entity> getIdentification() {
		return getClass();
	}

	@Override
	@Deprecated
	public Image getTexture() {
		return null;
	}
}
