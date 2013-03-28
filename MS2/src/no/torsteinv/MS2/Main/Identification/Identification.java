package no.torsteinv.MS2.Main.Identification;

import java.util.HashMap;

public class Identification {
	private static HashMap<String, Integer> ID = new HashMap<String, Integer>();
	private static HashMap<String, IdentificationTypes> TypeMap = new HashMap<String, IdentificationTypes>();
	
	public static int add(String name, IdentificationTypes type) throws IdentificationException{
		if(ID.keySet().contains(name))
			throw new IdentificationException("The name " + name + " is already used! Mod compatabiltiy issues?");
		int id = autoAssignIdentifection();
		ID.put(name, id);
		TypeMap.put(name, type);
		return id;
	}
	public static int get(String name){
		return ID.get(name.substring(TypeMap.get(name).name.length()));
	}
	public static String get(int ID) throws IdentificationException{
		if(!Identification.ID.values().contains(ID))
			throw new IdentificationException("Game does not include ID " + Integer.toHexString(ID));
		for(String str : Identification.ID.keySet())
			if(get(str) == ID)
				return str.substring(TypeMap.get(str).name.length());
		return null;
	}
	
	public static void clear(){
		ID.clear();
		TypeMap.clear();
		prev = Integer.MIN_VALUE;
	}
	
	private static int prev = Integer.MIN_VALUE;
	public static int autoAssignIdentifection(){
		prev++;
		return prev - 1;
	}
}
