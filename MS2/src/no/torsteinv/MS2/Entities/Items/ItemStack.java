package no.torsteinv.MS2.Entities.Items;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.util.HashMap;

import no.torsteinv.MS2.Entities.Container;
import no.torsteinv.MS2.Entities.Entity;
import no.torsteinv.MS2.Game.MS2.Buildings.ContainerBuilding;
import no.torsteinv.MS2.Main.Main;

public class ItemStack extends Entity {

	public void updateInPipe() {
		((BaseItem) this.Properties.get("Type")).onUpdateInPipe();
	}

	public ItemStack(BaseItem type, Container parent, int quantity, int x, int y,boolean autoadd) {
		super(x, y, parent != null ? ((Entity) parent).getInteger("Player")
				: 200);
		this.Properties.put("Type", type);
		this.Properties.put("Quantity", quantity);
		this.Properties.put("InPipe", false);
		if (parent != null && autoadd)
			parent.addContent(this);

	}

	public ItemStack(HashMap<String, Object> Properties,
			HashMap<String, Entity> Entities) {
		super(Properties, Entities);
	}

	public static void add(ItemStack item) {
		boolean CanPlace = true;
		for (Entity e : Main.Entities) {
			if (e instanceof Container && ((Container) e).pureContianer()
					&& e.getPlayer() == Main.player)
				for (ItemStack s : ((Container) e).getContent())
					if (item.Properties.get("Type") == s.Properties.get("Type")) {
						s.Properties.put("Quantity",
								(int) item.Properties.get("Quantity")
										+ (int) s.Properties.get("Quantity"));
						return;
					} else if (s.getFloat("X") == item.getFloat("X")
							&& s.getFloat("Y") == item.getFloat("Y")) {
						CanPlace = false;
					}
		}
		if (CanPlace)
			Main.Entities.add(item);
	}

	public static void remove(ItemStack item) throws Exception {
		if (has(item)) {
			for (Entity e : Main.Entities)
				if (e instanceof Container && e.getPlayer() == Main.player
						&& ((Container) e).pureContianer())
					for (ItemStack s : ((Container) e).getContent())
						if (item.Properties.get("Type").equals(
								s.Properties.get("Type"))
								&& (int) s.Properties.get("Quantity") >= (int) item.Properties
										.get("Quantity")) {
							s.Properties.put(
									"Quantity",
									(int) s.Properties.get("Quantity")
											- (int) item.Properties
													.get("Quantity"));
							if ((int) s.Properties.get("Quantity") <= 0)
								Main.Entities.remove(s);
						}
		} else
			throw new ItemMethodException(
					"Container does not contain requierd item! Item: " + item);
	}

	public static boolean has(ItemStack item) {
		for (Entity e : Main.Entities)
			if (e instanceof Container
					&& ((Container) e).pureContianer()
					&& ((Container) e).contains(item.getType(),
							item.getQuantity()))
				return true;
		return false;
	}

	public static Point getPrefferdPosition(Container ish) {
		int i = getNextIndex(ish);
		return new Point(i % 14, (i / 14));
	}

	public static ItemStack findStackWithStuffInContainer(BaseItem in,
			int quantity) {
		for (Entity s : Main.Entities)
			if (s instanceof Container && ((Container) s).pureContianer()
					&& ((Container) s).contains(in, quantity))
				for (ItemStack is : ((Container) s).getContent())
					if (is.getQuantity() >= quantity && is.getType().equals(in))
						return is;
		return null;
	}

	private static int getNextIndex(Container ish) {
		for (int i = 0; i < 100; i++) {
			boolean used = false;
			for (ItemStack is : ish.getContent())
				if (is.getX() + is.getY() * 10 == i)
					used = true;
			if (!used)
				return i;
		}

		return -1;
	}

	public static Container findContainerWithTypeAndQuantityInContainer(
			BaseItem i, int q) {
		for (Entity b : Main.Entities)
			if (b.getInteger("Player") == Main.player && b instanceof Container
					&& ((Container) b).pureContianer()
					&& ((Container) b).contains(i, q))
				return (Container) b;
		return null;
	}

	public ItemStack ExtractQuantity(int quantity) {
		ItemStack New = null;
		try {
			New = new ItemStack(((BaseItem) this.Properties.get("Type")),
					new ContainerBuilding(0, 0, 0), quantity, 0, 0,false);
			Main.setEntityProperty(this, "Quantity", getQuantity() - quantity);
			if (getQuantity() <= 0) {
				Main.removeEntity(this);
				return null;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return New;
	}

	public static ItemStack findStackOfTypeInContainer(BaseItem type) {
		for (Entity s : Main.Entities)
			if (s instanceof Container && ((Container) s).pureContianer()
					&& ((Container) s).contains(type,1))
				for (ItemStack is : ((Container) s).getContent())
					if (is.getType().equals(type))
						return is;
		return null;
	}

	@Override
	public Color getMapColor() {
		return null;
	}

	@Override
	public Image getTexture() {
		try {
			return BaseItem.Texture(
					((BaseItem) this.Properties.get("Type")).ID,
					BaseItem.Scale_Default);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public BaseItem getType() {
		return (BaseItem) this.Properties.get("Type");
	}

	public int getQuantity() {
		return (int) this.Properties.get("Quantity");
	}

	public boolean isInPipe() {
		return (boolean) this.Properties.get("InPipe");
	}

	public void setInPipe(boolean b) {
		this.Properties.put("InPipe", b);
	}

	public void setQuantity(int quantity) {
		this.Properties.put("Quantity", quantity);
	}

	public void setType(BaseItem type) {
		this.Properties.put("Type", type);
	}

	@Override
	public Class<? extends Entity> getIdentification() {
		return getClass();
	}

}
