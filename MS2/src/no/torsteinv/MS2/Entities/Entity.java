package no.torsteinv.MS2.Entities;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map.Entry;

import no.torsteinv.MS2.Entities.Items.BaseItem;
import no.torsteinv.MS2.Main.Main;
import no.torsteinv.MS2.Main.Loading.MainResourceLoading;
import no.torsteinv.MS2.Main.PostExcecution.ObjectTrafficException;

public abstract class Entity {

	public Entity(float x, float y, int player) {
		this.Properties.put("X", x);
		this.Properties.put("Y", y);
		this.Properties.put("Player", player);
		this.Properties.put("UpdateRequired", false);
	}

	public Entity(HashMap<String, Object> Properties,
			HashMap<String, Entity> Entities) {
		this.Properties = Properties;
		this.Entities = Entities;
	}

	public abstract Color getMapColor();

	public abstract Class<? extends Entity> getIdentification();

	public abstract Image getTexture();

	/**
	 * Hashmap of the varibles in this type
	 */
	public HashMap<String, Object> Properties = new HashMap<String, Object>();
	/**
	 * Hashmap of the entity variables in this type
	 */
	public HashMap<String, Entity> Entities = new HashMap<String, Entity>();
	/**
	 * String reference of
	 */
	public String reference = generateNewReference(this);

	/**
	 * Format:
	 * <Properties:(X=24=INTEGER),(Y=654=INTEGER),(Player=1=INTEGER),(Type
	 * =GOTO=COMMANDTYPE
	 * );Entities:(Entity=<(X=654),(Y=654)>)(Entity=<(89),(/)>)>
	 */

	public static Entity Parse(String str, boolean Reference) {
		if (Reference)
			try {
				return forReference(str);
			} catch (ObjectTrafficException e1) {
				e1.printStackTrace();
				return null;
			}
		String[] data = str.split(";");

		String PropertiesData = data[1].replace("Properties:", "<");
		String EntitiesData = data[2].replace("Entities:", "<");

		HashMap<String, Object> Properties = fromString(PropertiesData, ",");
		HashMap<String, Entity> Entities = fromStringEntities(EntitiesData);

		try {
			Entity result = (Entity) Class.forName(data[0].split("@")[0])
					.getConstructor(HashMap.class, HashMap.class)
					.newInstance(Properties, Entities);
			result.reference = data[0];
			return result;
		} catch (InstantiationException e) {
			e.printStackTrace();
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
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static final Entity create(Class<? extends Entity> type) {
		try {
			return type.getConstructor(HashMap.class, HashMap.class)
					.newInstance(new HashMap<String, Object>(),
							new HashMap<String, Entity>());
		} catch (InstantiationException e) {
			e.printStackTrace();
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
		}
		return null;
	}

	private static Entity forReference(String str)
			throws ObjectTrafficException {
		for (Entity e : Main.Entities)
			if (e.reference.equals(str))
				return e;
		throw new ObjectTrafficException("Reference " + str
				+ " could not be fond in Main entity list!");
	}

	static int lastReference = Integer.MIN_VALUE;

	private static String generateNewReference(Entity entity) {
		lastReference++;
		return entity.getIdentification().getName() + "@" + (lastReference - 1);
	}

	@Override
	public String toString() {

		String product = this.reference
				+ ";Properties:"
				+ fromHashMap(convertProp(this.Properties), ",", false)
				+ ">;Entities:"
				+ fromHashMap(convertEnt(this.Entities), "|", true)
						.replaceAll(";", "#") + ">;";

		return product;
	}

	public static HashMap<Object, Object> convertProp(HashMap<String, Object> hm) {
		HashMap<Object, Object> product = new HashMap<Object, Object>();

		for (String s : hm.keySet())
			product.put(s, hm.get(s));

		return product;
	}
	
	public static HashMap<Object, Object> convertEnt(HashMap<String, Entity> hm) {
		HashMap<Object, Object> product = new HashMap<Object, Object>();

		for (String s : hm.keySet())
			product.put(s, hm.get(s));

		return product;
	}

	public static String fromHashMap(HashMap<Object, Object> hm,
			String SplitSequence, boolean MakeReference) {
		String str = "";

		for (Entry<Object, Object> e : hm.entrySet())
			if (e.getValue() != null)
				str += fromEntry(e, MakeReference) + (SplitSequence);

		str = str.substring(0, str.length());

		return str;
	}

	public static String fromEntry(Entry<Object, Object> e, boolean Reference) {
		String str = "(";

		str += e.getKey();
		str += "=";
		try {
			str += fromObject(e.getValue(), Reference);
		} catch (ObjectTrafficException e1) {
			e1.printStackTrace();
		}
		str += "=";
		str += e.getValue().getClass().getName();

		return str + ")";
	}

	@SuppressWarnings("unchecked")
	public static String fromObject(Object value, boolean MakeReference)
			throws ObjectTrafficException {
		if (MakeReference)
			return ((Entity) value).reference;
		if (value instanceof Image)
			for (Entry<String, Image> e : MainResourceLoading.images.entrySet())
				if (e.getValue().equals(value))
					return e.getKey();
		if (value instanceof Rectangle)
			return ((Rectangle) value).x + " " + ((Rectangle) value).y + " "
					+ ((Rectangle) value).width + " "
					+ ((Rectangle) value).height;
		if (value instanceof BaseItem)
			return String.valueOf(((BaseItem) value).ID);
		if (value instanceof Point)
			return ((Point) value).x + " " + ((Point) value).y;
		if (value instanceof Enum)
			return value.toString();
		if (value instanceof Entity)
			return value.toString();
		if (value instanceof HashMap)
			return fromHashMap((HashMap<Object,Object>)value,"£",false);

		try {
			value.getClass().getDeclaredMethod("toString");
		} catch (NoSuchMethodException e1) {
			throw new ObjectTrafficException(
					value.getClass().getName()
							+ " does not explicitly implement toString(). Unknown results.");
		} catch (SecurityException e1) {
			e1.printStackTrace();
		}

		return value.toString();
	}

	public static HashMap<String, Object> fromString(String str,
			String splitSequence) {
		HashMap<String, Object> hm = new HashMap<String, Object>();

		str = str.replace("<", "");
		str = str.replace(">", "");

		String[] data = str.split(splitSequence);

		for (int i = 0; i < data.length; i++)
			getEntry(data[i]).addToHashMap(hm);

		return hm;
	}

	private static HashMap<String, Entity> fromStringEntities(String str) {
		HashMap<String, Entity> hm = new HashMap<String, Entity>();
		if (str.equals("<>"))
			return hm;

		String[] data = str.split("\\|");

		for (int i = 0; i < data.length; i++)
			getEntityEntry(data[i]).addToHashMap(hm);

		return hm;
	}

	public static class getEntryEntityResult {
		public String str = "";
		public Entity entity = null;

		public void addToHashMap(HashMap<String, Entity> hm) {
			hm.put(this.str, this.entity);
		}
	}

	public static class getEntryResult {
		public String str = "";
		public Object obj = null;

		public void addToHashMap(HashMap<String, Object> hm) {
			hm.put(this.str, this.obj);
		}
	}

	public static getEntryResult getEntry(String str) {
		getEntryResult result = new getEntryResult();
		if (str.equals("") || str.split("=").length < 3)
			return result;

		str = str.replaceAll("\\(", "");
		str = str.replaceAll("\\)", "");

		String[] data = str.split("=");

		result.str = data[0];
		result.obj = toObject(data[1], data[data.length - 1]);

		return result;
	}

	public static getEntryEntityResult getEntityEntry(String str) {
		getEntryEntityResult result = new getEntryEntityResult();
		if (str == null || str.equals("<>") || str.equals(" ")
				|| str.equals("<") || str.equals(">"))
			return result;

		str = str.replaceAll("\\(", "");
		str = str.replaceAll("\\)", "");

		String[] data = str.split("=");

		String restOfData = data[1];

		for (int i = 2; i < data.length - 1; i++)
			restOfData += "=" + data[i];

		result.str = data[0].replace("<", "");
		result.entity = Entity.Parse(restOfData, true);

		return result;
	}

	public static Object toObject(String value, String type) {
		Object obj = null;
		try {
			obj = Class.forName(type).getMethod("valueOf", String.class)
					.invoke(null, value);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {

			if (type.equals("java.awt.Rectangle"))
				obj = new Rectangle(Integer.parseInt(value.split(" ")[0]),
						Integer.parseInt(value.split(" ")[1]),
						Integer.parseInt(value.split(" ")[2]),
						Integer.parseInt(value.split(" ")[3]));

			if (type.contains("Image"))
				obj = MainResourceLoading.images.get(value);

			if (type.contains("Item"))
				obj = BaseItem.ItemByID(Integer.parseInt(value));
			
			if (type.contains("HashMap"))
				obj = fromString(value,"£");

			if (type.equals("java.awt.Point"))
				obj = new Point(Integer.parseInt(value.split(" ")[0]),
						Integer.parseInt(value.split(" ")[1]));

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return obj;
	}

	public float getFloat(String name) {
		return (float) this.Properties.get(name);
	}

	public int getInteger(String name) {
		return (int) this.Properties.get(name);
	}

	public String getString(String name) {
		return (String) this.Properties.get(name);
	}

	public void setX(float f) {
		this.Properties.put("X", f);
	}

	public void setY(float y) {
		this.Properties.put("Y", y);
	}

	public void setPlayer(int p) {
		this.Properties.put("Player", p);
	}

	public float getX() {
		return (float) this.Properties.get("X");
	}

	public float getY() {
		return (float) this.Properties.get("Y");
	}

	public int getPlayer() {
		return (int) this.Properties.get("Player");
	}

	@Override
	public int hashCode() {
		return (int) (getX() * 10000 + getY() + getPlayer());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		return obj instanceof Entity
				&& ((Entity) obj).reference.equals(this.reference);
	}

	public final void setUpdateRequired(boolean required) {
		this.Properties.put("UpdateRequired", required);
	}

	public final boolean isUpdateRequired() {
		return this.Properties.containsKey("UpdateRequired")
				&& (boolean) this.Properties.get("UpdateRequired");
	}

	// TODO ADD FUNCTIONALIY!!
	/**
	 * Called once element is added to the list
	 */
	public void onCreate() {
	}

	/**
	 * Called when element is selected
	 */
	public void onSelected() {
	}

	/**
	 * Called just before element removed from the list
	 */
	public void onDestory() {
	}

	/**
	 * Called when the entity provided in the parameteers collides with this
	 * entity. <br>
	 * Gets called in both entities. <br>
	 * Only checks for movable entities <br>
	 */
	public void onCollideWith(Entity e) {
	}

	/**
	 * Called on every frame of the game if the entity has the property
	 * "UpdateRequired" and defined as true.
	 */
	public void onUpdate() {
	}

	/**
	 * Called when entity Clicked
	 */
	public void OnClick() {
	}
}
