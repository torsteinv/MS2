package no.torsteinv.MS2.Main.PostExcecution;

import java.util.Arrays;
import java.util.HashMap;

import no.torsteinv.MS2.Entities.Container;
import no.torsteinv.MS2.Entities.Entity;
import no.torsteinv.MS2.Entities.Items.ItemStack;
import no.torsteinv.MS2.Main.Main;
import no.torsteinv.MS2.Main.Miscellaneous.Message;
import no.torsteinv.MS2.Main.PostExcecution.BitSystem.BitDecypherer;
import no.torsteinv.MS2.ParticleSystem.Emitter;
import no.torsteinv.MS2.Sound.Player;
import no.torsteinv.MS2.Sound.Sound;

public class Excecuter {
	public static void excecuteCommand(String cmd, boolean oldPlayerSystem)
			throws Exception {
		cmd = cmd.replace(" %OLDSYSTEM%", "");
		String[] splited = (cmd.split("-")[1] + arrayAddAllAfter(1,
				cmd.split("-"), '-')).split("/");
		if (cmd.startsWith("!MSG"))
			message(splited[1], splited[2], oldPlayerSystem);
		else if (cmd.startsWith("!SET"))
			setVar(splited[1], splited[2], splited[3], oldPlayerSystem);
		else if (cmd.startsWith("!LADD"))
			if (splited.length >= 5)
				addVar(splited[1], splited[2], splited[3], splited[4],
						oldPlayerSystem);
			else
				addVar(splited[1], splited[2], "NULL", splited[3],
						oldPlayerSystem);
		else if (cmd.startsWith("!LSET"))
			ListSet(splited[1], splited[2], splited[3], splited[4],
					oldPlayerSystem, false);
		else if (cmd.startsWith("!LASET"))
			ListSet(splited[1], splited[2], splited[3], splited[4],
					oldPlayerSystem, true);
		else if (cmd.startsWith("!LREMOVE"))
			remVar(splited[1], splited[2], splited[3], oldPlayerSystem);
		else
			throw new Exception(
					"Could not parse message due to modifier not making sence");

	}

	private static String arrayAddAllAfter(int i, String[] array, char inbetween) {
		String[] ar = Arrays.copyOfRange(array, i + 1, array.length);
		String rslt = "";
		for (String s : ar)
			rslt += inbetween + s;
		return rslt;
	}

	private static void ListSet(String Varname, String UsedEntity,
			String Varval, String Player, boolean oldPlayerSystem,
			boolean EntityList) throws Exception {
		if (oldPlayerSystem) {
			if (!((!(Integer.parseInt(Player) - 100 == Main.player))
					|| (Integer.parseInt(Player) == Main.player) || (Integer
						.parseInt(Player) == 200)))
				return;
		} else {
			if (!Player.equals(Integer.toString(BitDecypherer.ALL)))
				if (!BitDecypherer.Decypher(Integer.parseInt(Player))
						.isIncluded(Main.player, true))
					return;
		}

		if (!EntityList) {
			Entity.Parse(UsedEntity, true).Properties.put(
					Varname.split("@")[0],
					Entity.toObject(Varval, Varname.split("@")[1]));
		} else {
			Entity.Parse(UsedEntity, true).Entities.put(Varname.split("@")[0],
					Entity.Parse(Varval, false));
			if (Entity.Parse(UsedEntity, true) instanceof ItemStack
					&& Varname.equals("Parent"))
				((Container) Entity.Parse(Varval, false))
						.addContent((ItemStack) Entity.Parse(UsedEntity, true));
		}
	}

	private static void remVar(String list, String addedEntity, String player,
			boolean oldPlayerSystem) throws Exception {
		if (oldPlayerSystem) {
			if (!((!(Integer.parseInt(player) - 100 == Main.player))
					|| (Integer.parseInt(player) == Main.player) || (Integer
						.parseInt(player) == 200)))
				return;
		} else {
			if (!player.equals(Integer.toString(BitDecypherer.ALL)))
				if (!BitDecypherer.Decypher(Integer.parseInt(player))
						.isIncluded(Main.player, true))
					return;
		}

		if (list.equals("Entity")) {
			if (Entity.Parse(addedEntity, false) instanceof ItemStack)
				ItemStack.add((ItemStack) Entity.Parse(addedEntity, false));
			Main.Entities.remove(Entity.Parse(addedEntity, false));
		} else if (list.equals("Emitter")) {
			Main.Emitters.remove(KeyByValue(Emitter.Parse(addedEntity),
					Main.Emitters));
		} else
			throw new Exception("Keyword: " + list + " does not make sence!");
	}

	public static Entity KeyByValue(Emitter value, HashMap<Entity, Emitter> m) {
		for (Entity e : m.keySet())
			if (value.equals(m.get(e)))
				return e;
		return null;
	}

	private static void addVar(String List, String AddedEntity,
			String EmitterEntity, String Player, boolean oldPlayerSystem)
			throws Exception {
		if (oldPlayerSystem) {
			if (!((!(Integer.parseInt(Player) - 100 == Main.player))
					|| (Integer.parseInt(Player) == Main.player) || (Integer
						.parseInt(Player) == 200)))
				return;
		} else {
			if (!Player.equals(Integer.toString(BitDecypherer.ALL)))
				if (!BitDecypherer.Decypher(Integer.parseInt(Player))
						.isIncluded(Main.player, true))
					return;
		}
		if (List.equals("Entity")) {
			if (Entity.Parse(AddedEntity, false) instanceof ItemStack) {
				ItemStack.add((ItemStack) Entity.Parse(AddedEntity, false));
				return;
			}
			Main.Entities.add(Entity.Parse(AddedEntity, false));
		} else if (List.equals("Emitter")) {
			Main.Emitters.put(Entity.Parse(EmitterEntity, true),
					Emitter.Parse(AddedEntity));
		} else
			throw new Exception("Keyword: " + List + " does not make sence!");
	}

	private static void setVar(String PropertyName, String Value,
			String Player, boolean oldPlayerSystem) throws Exception {
		Main.userSpecificVariables[Main.player].Properties.put(
				PropertyName.split("@")[0],
				Entity.toObject(Value, PropertyName.split("@")[1]));

	}

	private static void message(String msg, String player,
			boolean oldPlayerSystem) {
		if (oldPlayerSystem) {
			if (!((!(Integer.parseInt(player) - 100 == Main.player))
					|| (Integer.parseInt(player) == Main.player) || (Integer
						.parseInt(player) == 200)))
				return;
		} else {
			if (!player.equals(Integer.toString(BitDecypherer.ALL)))
				if (!BitDecypherer.Decypher(Integer.parseInt(player))
						.isIncluded(Main.player, true))
					return;
		}
		try {
			Player.play(Sound.buttonClick);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Main.Messages.add(new Message(msg));
	}
}
