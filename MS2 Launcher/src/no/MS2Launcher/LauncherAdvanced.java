package no.MS2Launcher;

import java.awt.Desktop;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Formatter;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class LauncherAdvanced {
	public void downloadFull(String version) {

		System.out.println("Downloading...");
		try {
			new File("curver.txt").delete();
			new File("curver.txt").createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Formatter f = null;
		try {
			f = new Formatter(new File("curver.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		f.format(version);
		f.flush();

		new File(System.getenv("APPDATA") + "/MS2").delete();

		String l = "";
		try {
			Scanner s = new Scanner(
					new URL("http://torsteinv.host22.com/data/Registry.html")
							.openStream());
			while (s.hasNextLine() && !(l = s.nextLine()).equals("FILE-BREAK")) {
				File file = new File(System.getenv("APPDATA") + "/MS2/" + l);
				file.mkdirs();
			}
			while (s.hasNextLine()) {
				l = s.nextLine();
				File file = new File(System.getenv("APPDATA") + "/MS2/" + l);
				file.createNewFile();
				downloadFile(file, new URL("http://torsteinv.host22.com/data/"
						+ l));
			}
			s.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void checkVersion() {
		try {
			Scanner s1 = new Scanner(new URL(
					"http://torsteinv.host22.com/data/ver.html").openStream());
			Scanner s2 = new Scanner(new File("curver.txt"));
			String currentVersion = s2.nextLine();
			String onlineVersion = s1.nextLine();
			s2.close();
			s1.close();
			if (onlineVersion.equals(currentVersion))
				startGame();
			else if (new File(System.getenv("APPDATA")
					+ "/MS2/bin/no/torsteinv/MS2/Holder.txt").exists())
				askUpdate(onlineVersion);
			else
				update(onlineVersion);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void update(String vers) {
		downloadFull(vers);
		checkVersion();
	}

	private void askUpdate(String vers) {
		if (JOptionPane.showConfirmDialog(null,
				"An update is avalible. Update?") == 0)
			update(vers);
		else
			startGame();
	}

	private void startGame() {
		try {
			Desktop.getDesktop().open(
					new File(System.getenv("APPDATA") + "/MS2/bin/MS2.bat"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void downloadFile(File compPath, URL webPath) {
		try {
			InputStream is = webPath.openConnection().getInputStream();
			OutputStream os = new BufferedOutputStream(new FileOutputStream(
					compPath));
			byte[] buffer = new byte[1024 * 1024];
			int len;

			while ((len = is.read(buffer)) >= 0)
				os.write(buffer, 0, len);

			is.close();
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

}
