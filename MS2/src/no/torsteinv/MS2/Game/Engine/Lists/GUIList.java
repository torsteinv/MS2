package no.torsteinv.MS2.Game.Engine.Lists;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map.Entry;

import no.torsteinv.MS2.Annotations.AnnotationsToolkit;
import no.torsteinv.MS2.Annotations.Name;
import no.torsteinv.MS2.GUI.IGUI;

public abstract class GUIList extends List {

	public static HashMap<String, Class<? extends IGUI>> MasterGUITypeList = new HashMap<String, Class<? extends IGUI>>();

	public static String fromKey(Class<? extends IGUI> value) {
		for (Entry<String, Class<? extends IGUI>> e : MasterGUITypeList
				.entrySet())
			if (value.equals(e.getValue()))
				return e.getKey();
		return "Unidentified";
	}

	public void load(Class<? extends IGUI> gui) {
		try {
			MasterGUITypeList.put((String) AnnotationsToolkit
					.findStaticFieldWithAnnotaion(gui, Name.class).get(null),
					gui);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public abstract void loadGUI();

	public static void addList(GUIList list) {
		try {
			list.getClass().getMethod("loadGUI")
					.invoke(list.getClass().newInstance());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
	}

}
