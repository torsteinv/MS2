package no.torsteinv.MS2.Game.Engine.GameLoading;

import java.util.zip.ZipFile;

public class GameLoaderEntry {
	String file = "";
	String main = "";
	ZipFile zipFile = null;
	public GameLoaderEntry(String file,
			ZipFile zipFile, String main) {
		this.file = file;
		this.zipFile = zipFile;
		this.main = main;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public ZipFile getZipFile() {
		return zipFile;
	}
	public void setZipFile(ZipFile zipFile) {
		this.zipFile = zipFile;
	}
}
