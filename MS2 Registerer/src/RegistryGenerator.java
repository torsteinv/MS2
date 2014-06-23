import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;

public class RegistryGenerator {
	public static void main(String[] args) {
		if (args.length > 0)
			args[0] = args[0].replace("\\", "/").replace("\\\\", "/");
		File fl = new File(args.length == 1 ? args[0]
				: "C:/Users/tv/Desktop/MS2 Obfuscator");
		ArrayList<File> files = getAllFiles(fl);

		File reg = new File(args.length == 1 ? args[0] + "/Registry.html"
				: "C:/Users/tv/Desktop/MS2 Obfuscator/Registry.html");
		Formatter fr = null;
		try {
			reg.createNewFile();
			fr = new Formatter(reg);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		files.remove(new File(args.length == 1 ? args[0]
				+ "/RegistryGenerator.class"
				: "C:/Users/tv/Desktop/MS2 Obfuscator/RegistryGenerator.class"));
		files.remove(new File(args.length == 1 ? args[0] + "/Obfuscate.bat"
				: "C:/Users/tv/Desktop/MS2 Obfuscator/Obfuscate.bat"));
		files.remove(new File(args.length == 1 ? args[0] + "/Registry.html"
				: "C:/Users/tv/Desktop/MS2 Obfuscator/Registry.html"));

		try {
			for (File f : files) {
				if (f.isDirectory())
					fr.format(f.getCanonicalPath().replace("\\", "/")
							.replace(args[0] + "/", "")
							+ "\n");
			}
			fr.format("FILE-BREAK\n");
			for (File f : files) {
				if (f.isFile())
					fr.format(f.getCanonicalPath().replace("\\", "/")
							.replace(args[0] + "/", "")
							+ "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		fr.flush();
	}

	public static ArrayList<File> getAllFiles(File folder) {
		return ffRECUR(folder);
	}

	public static ArrayList<File> ffRECUR(File folder) {
		ArrayList<File> complete = new ArrayList<File>();
		complete.addAll(filterFiles(folder.listFiles()));
		for (File nfolder : filterFolders(folder.listFiles())) {
			complete.add(nfolder);
			complete.addAll(ffRECUR(nfolder));
		}
		return complete;
	}

	public static ArrayList<File> filterFolders(File[] files) {
		ArrayList<File> res = new ArrayList<File>();
		for (File f : files)
			if (f.isDirectory())
				res.add(f);
		return res;
	}

	public static ArrayList<File> filterFiles(File[] files) {
		ArrayList<File> res = new ArrayList<File>();
		for (File f : files)
			if (f.isFile())
				res.add(f);
		return res;
	}
}
