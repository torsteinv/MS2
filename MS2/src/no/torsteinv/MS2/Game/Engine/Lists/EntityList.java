package no.torsteinv.MS2.Game.Engine.Lists;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map.Entry;

import no.torsteinv.MS2.Entities.Entity;

public abstract class EntityList extends List{

	public static HashMap<String,Class<? extends Entity>> MasterEntityTypeList = new HashMap<String,Class<? extends Entity>>();
	
	public static String fromKey(Class<? extends Entity> value){
		for(Entry<String, Class<? extends Entity>> e : MasterEntityTypeList.entrySet())
			if(value.equals(e.getValue()))
				return e.getKey();
		return "Unidentified";
	}
	
	public void load(Class<? extends Entity> entity) {
		MasterEntityTypeList.put(entity.getName(), entity);
	}

	public abstract void loadEntities();

	public static void addList(EntityList list) {
		try {
			list.getClass().getMethod("loadEntities").invoke(list.getClass().newInstance());
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
