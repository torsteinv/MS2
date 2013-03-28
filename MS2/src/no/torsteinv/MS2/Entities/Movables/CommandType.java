package no.torsteinv.MS2.Entities.Movables;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import no.torsteinv.MS2.Entities.Movables.AI.AI;
import no.torsteinv.MS2.Entities.Movables.AI.AIException;
import no.torsteinv.MS2.Entities.Movables.AI.AINone;
import no.torsteinv.MS2.Game.MS2.AI.AIDropOff;
import no.torsteinv.MS2.Game.MS2.AI.AIStraightWalk;
import no.torsteinv.MS2.Game.MS2.AI.AITake;

public enum CommandType {
	Goto(1, new AIStraightWalk()), None(0, new AINone()),
	DropOff(2, new AIDropOff()), Take(3, new AITake());
	int ID = 0;
	AI AI = new AINone();
	List<String> RequiredArguments;

	CommandType(int ID, AI AI, String... RequiredArguments) {
		this.ID = ID;
		this.AI = AI;
		this.RequiredArguments = Arrays.asList(RequiredArguments);
	}

	// TODO: Complete target functionality
	CommandType(int ID, AI AI, Class<? extends Movable> target,
			String... RequiredArguments) {
		this.ID = ID;
		this.AI = AI;
		this.RequiredArguments = Arrays.asList(RequiredArguments);
	}

	public int getID() {
		return this.ID;
	}

	public void setID(int iD) {
		this.ID = iD;
	}

	public void check(HashMap<String, Object> properties) {
		if (!properties.keySet().containsAll(this.RequiredArguments))
			new AIException(
					"Properties did not include nececary arguments! KeySet: "
							+ properties.keySet() + " Required: "
							+ this.RequiredArguments).printStackTrace();
	}
}
