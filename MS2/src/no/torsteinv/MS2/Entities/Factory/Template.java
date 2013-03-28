package no.torsteinv.MS2.Entities.Factory;

import java.awt.Image;
import java.io.File;

import no.torsteinv.MS2.Main.Loading.MainResourceLoading;

public enum Template {
	Stone("TemplateStoneImage","TemplateStoneData"),
	Brick("TemplateBrickImage","TemplateBrickData"),
	Fuel("TemplateFuelImage","TemplateFuelData");
	private Image d = null;
	private File f = null;
	Template(String TextureTag,String DataTag){
		d = MainResourceLoading.images.get(TextureTag);
		f = MainResourceLoading.files.get(DataTag);
	}
	public Image getImage(){
		return d;
	}
	public File getData(){
		return f;
	}
}
