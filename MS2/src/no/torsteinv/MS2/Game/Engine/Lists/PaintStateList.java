package no.torsteinv.MS2.Game.Engine.Lists;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map.Entry;

import no.torsteinv.MS2.Game.Engine.GameLoading.GameLoadingException;
import no.torsteinv.MS2.Game.Engine.Others.PaintState;
import no.torsteinv.MS2.Game.Engine.Others.PaintStateType;

public abstract class PaintStateList extends List {
	public static HashMap<String, PaintState> MasterPlaceStateTypeList = new HashMap<String, PaintState>();

	public static String fromKey(PaintState value) {
		for (Entry<String, PaintState> e : MasterPlaceStateTypeList.entrySet())
			if (value.equals(e.getValue()))
				return e.getKey();
		return "Unidentified";
	}

	public static void load(PaintState paintState, String name) {
		MasterPlaceStateTypeList.put(name, paintState);
	}

	public static void autoAssignPaintState(String name, PaintStateType... pst) {
		PaintState ps = new PaintState(pst);
		load(ps, name);
	}

	public static PaintState forName(String name) {
		PaintState ps = MasterPlaceStateTypeList.get(name);
		if (ps == null)
			new GameLoadingException("Could not find paintstate \"" + name
					+ "\" or it is not defined").printStackTrace();
		return ps;
	}

	public abstract void loadEntities();

	public static void addList(PaintStateList list) {
		try {
			list.getClass().getMethod("loadEntities")
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
