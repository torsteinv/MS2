package no.torsteinv.MS2.Sound;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import no.torsteinv.MS2.Main.Loading.MainResourceLoading;

public enum Sound{
	buttonClick("ButtonClick","ButtonClick");
	String path;
	String indentification;
	ByteArrayOutputStream sound;
	Sound(String path, String indentification){
		this.path = path;
		this.indentification = indentification;
		InputStream input = MainResourceLoading.sounds.get(indentification);
		sound = new ByteArrayOutputStream(); 
	    byte[] buffer = new byte[1024]; 
	    int len; 
	    try {
			while ((len = input.read(buffer)) > -1 ) { 
			    sound.write(buffer, 0, len); 
			}
			sound.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
