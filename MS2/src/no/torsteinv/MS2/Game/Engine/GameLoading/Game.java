package no.torsteinv.MS2.Game.Engine.GameLoading;

import java.awt.Image;
import java.awt.Rectangle;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import no.torsteinv.MS2.Main.Loading.MainResourceLoading;
import sun.audio.AudioStream;

public abstract class Game {

	public abstract void loadResources();

	public static ArrayList<Game> Games = new ArrayList<>();

	@SuppressWarnings("unchecked")
	private static void loadGameMainClass(GameLoaderEntry gle) {
		try {
			Class<Game> c = null;
			if (new File(System.getenv("APPDATA")
					.concat("/MS2/MS2/bin/no/torsteinv/MS2/Game/").concat(gle.main))
					.exists()) {
				URL url = new File(System.getenv("APPDATA").concat(
						"/MS2/MS2/bin/")).toURI().toURL();
				URLClassLoader cl = URLClassLoader
						.newInstance(new URL[] { url });
				String name = "no.torsteinv.MS2.Game.".concat(gle.main.replace(".class", ""));
				c = (Class<Game>) cl.loadClass(name);
			} else {
				ZipEntry next = null;
				for (Enumeration<ZipEntry> e = (Enumeration<ZipEntry>) gle
						.getZipFile().entries(); e.hasMoreElements();) {
					File f = new File(System.getenv("APPDATA")
							.concat("/MS2/MS2/bin/no/torsteinv/MS2/Game/")
							.concat("/" + (next = e.nextElement()).getName()));
					if (next.getName().toUpperCase().endsWith("CLASS")) {
						f.createNewFile();

						InputStream is = gle.getZipFile().getInputStream(next);
						OutputStream os = new BufferedOutputStream(
								new FileOutputStream(f));

						byte[] buffer = new byte[1024 * 1000];
						int len;

						while ((len = is.read(buffer)) >= 0)
							os.write(buffer, 0, len);

						is.close();
						os.close();
					} else
						f.mkdir();
				}
				loadGameMainClass(gle);
				return;
			}
			c.newInstance();
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | IOException e) {
			e.printStackTrace();
		}
	}

	public static void findAll() {
		String path = System.getenv("APPDATA").concat("/MS2/MS2/mods");

		ArrayList<File> al = new ArrayList<File>();
		String files;
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				files = listOfFiles[i].getName();
				if (files.toUpperCase().endsWith(".MSMOD")) {
					al.add(listOfFiles[i]);
				}
			}
		}
		// File formating
		// Zip-file
		// Main-file
		Scanner s;
		for (File f : al) {
			try {
				s = new Scanner(f);
				String[] data = new String[2];
				for (int i = 0; i < 2; i++)
					data[i] = s.next();
				loadGameMainClass(new GameLoaderEntry(data[0], new ZipFile(
						folder.getPath().concat("\\").concat(data[0])), data[1]));
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public String name = "Unamed";
	public int ID = 0;

	public void addGame(String name, int ID) {
		Games.add(this);
		this.name = name;
		this.ID = ID;
		loadResources();
	}

	public Game(String name, int ID) {
		addGame(name, ID);
	}

	public Game(String name) {
		this(name, autoAssignIdentifection());
	}

	private static int prev = 0;

	private static int autoAssignIdentifection() {
		prev++;
		return prev - 1;
	}

	public void clear() {
		MainResourceLoading.clear();
	}

	public Image addImage(String name, boolean transperent,
			String Identification) {
		return MainResourceLoading.addImage(name, transperent, Identification);
	}

	public Image addImage(String location, String name, boolean transperent,
			String Identification) {
		return addImage(location + "/" + name, transperent, Identification);
	}

	public Image addImage(String name, boolean transperent, Rectangle cutout,
			String Identification) {
		return MainResourceLoading.addImage(name, transperent, cutout,
				Identification);
	}

	public Image addImage(String location, String name, boolean transperent,
			Rectangle cutout, String Identification) {
		return addImage(location + "/" + name, transperent, cutout,
				Identification);
	}

	public File addFile(String name, String Identification) {
		return MainResourceLoading.addFile(name, Identification);
	}

	public File addFile(String location, String name, String Identification) {
		return addFile(location + "/" + name, Identification);
	}

	public AudioStream addSound(String name, String Identification) {
		return MainResourceLoading.addSound(name, Identification);
	}

	public AudioStream addSound(String location, String name,
			String Identification) {
		return addSound(location + "/" + name, Identification);
	}

	static boolean loaded = false;
	public static void loadGames() {
		if(!loaded)
			findAll();
		loaded = true;
	}
}
