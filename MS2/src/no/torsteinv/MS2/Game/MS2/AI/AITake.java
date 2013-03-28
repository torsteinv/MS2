package no.torsteinv.MS2.Game.MS2.AI;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import no.torsteinv.MS2.Entities.Container;
import no.torsteinv.MS2.Entities.Items.ItemStack;
import no.torsteinv.MS2.Entities.Movables.CommandType;
import no.torsteinv.MS2.Entities.Movables.Movable;
import no.torsteinv.MS2.Entities.Movables.AI.AI;
import no.torsteinv.MS2.Entities.Movables.AI.AIException;
import no.torsteinv.MS2.Main.Main;
import no.torsteinv.MS2.Managers.Timing.Timer;

public class AITake extends AI {
	static HashMap<String, Float> vx = new HashMap<String, Float>();
	static HashMap<String, Float> vy = new HashMap<String, Float>();

	@SuppressWarnings("unchecked")
	@Override
	public boolean invoke(Movable entity, Object... params) throws AIException {
		if (params.length < 2)
			throw new AIException(
					"you must define where the movable should go and what it should take! (java.awt.Point,java.util.HashMap<no.torsteinv.MS2.Entities.Items.BaseItem,java.lang.Integer>)");
		if (!(params[0] instanceof Point))
			throw new AIException(
					"The first parameter must be an instance of java.awt.Point!");
		if (!(params[1] instanceof ArrayList && params[1].getClass()
				.getTypeParameters()[0].getGenericDeclaration()
				.isAssignableFrom(ItemStack.class)))
			throw new AIException(
					"The second parameter must be an instance of java.util.ArrayList<no.torsteinv.MS2.Entities.Items.ItemStack>!");
		return Goto((Point) params[0], entity, (ArrayList<ItemStack>) params[1]);
	}

	private static boolean Goto(Point pos, Movable v, ArrayList<ItemStack> al) {
		vx.put(v.reference, (float) ((pos.x - v.getX()) / Math.hypot(
				(pos.x - v.getX()), (pos.y - v.getY()))));
		vy.put(v.reference, (float) ((pos.y - v.getY()) / Math.hypot(
				(pos.x - v.getX()), (pos.y - v.getY()))));
		if (PointClose(new Point((int) v.getX(), (int) v.getY()), pos)) {
			v.setDone(true);
			loadContents((Container) v,
					(Container) Main.getEntityAt(pos.x, pos.y), al);
			return true;
		}
		v.setDone(false);
		try {
			Main.setEntityProperty(v, "X", v.getX() + vx.get(v.reference)
					* Timer.getDelta() / 50);
			Main.setEntityProperty(v, "Y", v.getY() + vy.get(v.reference)
					* Timer.getDelta() / 50);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private static void loadContents(Container v, Container c,
			ArrayList<ItemStack> rm) {
		v.addContent(rm);
		c.removeContent(rm);
	}

	private static boolean PointClose(Point p1, Point p2, int difference) {
		int x1 = p1.x;
		int y1 = p1.y;
		int x2 = p2.x;
		int y2 = p2.y;
		boolean pr1 = (x2 > x1 ? x2 - x1 : x1 - x2) < difference;
		boolean pr2 = (y2 > y1 ? y2 - y1 : y1 - y2) < difference;
		return pr1 && pr2;
	}

	private static boolean PointClose(Point p1, Point p2) {
		return PointClose(p1, p2, 20);
	}

	@Override
	public CommandType getAssosiatedCommandType() {
		return CommandType.DropOff;
	}
}
