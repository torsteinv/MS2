package no.torsteinv.MS2.Game.Engine.Lists;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map.Entry;

import no.torsteinv.MS2.Entities.Placeable;
import no.torsteinv.MS2.Game.Engine.GameLoading.GameLoadingException;
import no.torsteinv.MS2.Game.Engine.Others.PlaceState;

public abstract class PlaceStateList extends List {

	public static HashMap<String, PlaceState> MasterPlaceStateTypeList = new HashMap<String, PlaceState>();

	public static String fromKey(PlaceState value) {
		for (Entry<String, PlaceState> e : MasterPlaceStateTypeList.entrySet())
			if (value.equals(e.getValue()))
				return e.getKey();
		return "Unidentified";
	}

	public static void load(PlaceState entity, String name) {
		MasterPlaceStateTypeList.put(name, entity);
	}

	public static void autoAssignPlaceState(Placeable e, String name) {

		PlaceState ps = new PlaceState(MasterPlaceStateTypeList.size(),
				e.getPlaceType(), e, e.getRestrictions());
		load(ps, name);
	}

	public static PlaceState forName(String name) {
		PlaceState ps = MasterPlaceStateTypeList.get(name);
		if (ps == null)
			new GameLoadingException("Could not find placestate \"" + name
					+ "\" or it is not defined").printStackTrace();
		return ps;
	}

	public abstract void loadEntities();

	public static void addList(PlaceStateList list) {
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
