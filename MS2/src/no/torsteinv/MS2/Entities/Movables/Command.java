package no.torsteinv.MS2.Entities.Movables;

import java.awt.Point;
import java.util.HashMap;

import no.torsteinv.MS2.Entities.Entity;
import no.torsteinv.MS2.Entities.Movables.AI.AIException;

public class Command {
	public CommandType Type = CommandType.None;
	// Name : value
	public HashMap<String, Object> Properties = new HashMap<String, Object>();

	public Command Perform(CommandType Type, String... Properties) {
		this.Type = Type;
		if (((double) (Properties.length / 2) - (Properties.length / 2)) == .5)
			new IllegalArgumentException("Variable without value!")
					.printStackTrace();
		for (int i = 0; i < Properties.length; i += 2)
			this.Properties.put(Properties[i], Properties[i + 1]);
		Type.check(this.Properties);
		return this;
	}

	@Override
	public String toString() {
		return this.Type.toString() + "0x1337" + Entity.fromHashMap(Entity.convertProp(this.Properties),"æ",false);
	}

	public static Command valueOf(String str) {
		Command product = new Command();

		String[] data = str.split("0x1337");
		CommandType type = CommandType.valueOf(data[0]);

		HashMap<String, Object> hm = Entity.fromString(data.length < 2 ? "" : data[1].replace("(", ""),"æ");

		product.Type = type;
		product.Properties = hm;

		return product;
	}

	@Override
	public Command clone() {
		return valueOf(toString());
	}

	public void Stop() {
		this.Type = CommandType.None;
	}

	public String getVariable(String variable) {
		return (String) this.Properties.get(variable);
	}

	public int getVaribleAsInteger(String variable) {
		return Integer.parseInt(getVariable(variable));
	}

	public boolean getVaribleAsBoolean(String variable) {
		return Boolean.parseBoolean(getVariable(variable));
	}

	public Command() {
	}

	public void update(Movable v) {
		try {
			if (this.Type != CommandType.None ? this.Type.AI.invoke(v,
					new Point(Integer.parseInt((String) this.Properties.get("X")),
							Integer.parseInt((String) this.Properties.get("Y"))))
					: false)
				this.Type = CommandType.None;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (AIException e) {
			e.printStackTrace();
		}
	}
}
