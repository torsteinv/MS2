package no.torsteinv.MS2.Main.Loading;

import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;

import no.torsteinv.MS2.Main.Identification.Identification;
import no.torsteinv.MS2.Main.Identification.IdentificationException;
import no.torsteinv.MS2.Main.Identification.IdentificationTypes;
import no.torsteinv.MS2.Managers.Visualization.TransperentManager;
import sun.audio.AudioStream;

public class MainResourceLoading {
	public static HashMap<String, Image> images = new HashMap<String, Image>();
	public static HashMap<String, File> files = new HashMap<String, File>();
	public static HashMap<String, InputStream> sounds = new HashMap<String, InputStream>();

	public static void clear(){
		images.clear();
		files.clear();
		sounds.clear();
	}
	
	public static Image addImage(String name, boolean transperent, String Identification) {
		Image img = null;
		try{
			img = ImageIO.read(new File("images/" + name));
		}catch(IOException e){
			e.printStackTrace();
		}
		if(transperent)
			img = TransperentManager.makeColorTransparent(img, new Color(255,0,255));
		addImage(img, Identification);
		return img;
	}

	public static Image addImage(String location, String name, boolean transperent, String Identification) {
		return addImage(location + "/" + name, transperent, Identification);
	}

	public static Image addImage(String name, boolean transperent, Rectangle cutout, String Identification) {
		Image img = null;
		try{
			img = ImageIO.read(new File("images/" + name));
		}catch(IOException e){
			e.printStackTrace();
		}
		img = ((BufferedImage)img).getSubimage(cutout.x, cutout.y, cutout.width, cutout.height);
		if(transperent)
			img = TransperentManager.makeColorTransparent(img, new Color(255,0,255));
		addImage(img, Identification);
		return img;
	}

	public static Image addImage(String location, String name, boolean transperent,
			Rectangle cutout, String Identification) {
		return addImage(location + "/" + name, transperent, cutout, Identification);
	}
	
	public static File addFile(String name, String Identification){
		File file = new File("files/" + name);
		addFile(file, Identification);
		return file;
	}
	
	public static File addFile(String location, String name, String Identification){
		return addFile(location + "/" + name, Identification);
	}
	
	public static AudioStream addSound(String name, String Identification){
		FileInputStream is = null;
		AudioStream as = null;
		try {
			is = new FileInputStream("sounds/" + name);
		} catch (IOException e) {
			e.printStackTrace();
		}
		addSound(is, Identification);
		return as;
	}
	
	public static AudioStream addSound(String location, String name, String Identification){
		return addSound(location + "/" + name, Identification);
	}

	public static void addImage(Image img, String name) {
		try {
			Identification.add("resource.image." + name,IdentificationTypes.Image);
			images.put(name, img);
		} catch (IdentificationException e) {
			e.printStackTrace();
		}
	}

	public static void addFile(File file, String name) {
		try {
			Identification.add("resource.file." + name,IdentificationTypes.File);
			files.put(name, file);
		} catch (IdentificationException e) {
			e.printStackTrace();
		}
	}

	public static void addSound(InputStream sound, String name) {
		try {
			Identification.add("resource.sounds." + name,IdentificationTypes.Sound);
			sounds.put(name, sound);
		} catch (IdentificationException e) {
			e.printStackTrace();
		}
	}
}
