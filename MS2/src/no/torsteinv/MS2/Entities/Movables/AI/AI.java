package no.torsteinv.MS2.Entities.Movables.AI;

import no.torsteinv.MS2.Entities.Movables.CommandType;
import no.torsteinv.MS2.Entities.Movables.Movable;

public abstract class AI {
	public abstract CommandType getAssosiatedCommandType();

	public abstract boolean invoke(Movable entity, Object...params) throws AIException;
}
