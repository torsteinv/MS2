package no.torsteinv.MS2.Entities.Movables.AI;

import no.torsteinv.MS2.Entities.Movables.CommandType;
import no.torsteinv.MS2.Entities.Movables.Movable;


public class AINone extends AI {

	@Override
	public CommandType getAssosiatedCommandType() {
		return CommandType.None;
	}

	@Override
	public boolean invoke(Movable entity, Object...params) throws AIException {
		throw new AIException("You may not invoke a no.torsteinv.MS2.Entities.Movables.AI.AINone");
	}

}
