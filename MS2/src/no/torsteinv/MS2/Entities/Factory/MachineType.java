package no.torsteinv.MS2.Entities.Factory;

import java.awt.Color;

public enum MachineType {
	None(null),
	Burner(new no.torsteinv.MS2.Game.MS2.Factory.Buildings.Burner(0,0,null,0)),
	Compresser(new no.torsteinv.MS2.Game.MS2.Factory.Buildings.Compressor(0,0,null,0)),
	Mixer(new no.torsteinv.MS2.Game.MS2.Factory.Buildings.Mixer(0,0,null,0)),
	Refiner(new no.torsteinv.MS2.Game.MS2.Factory.Buildings.Refiner(0,0,null,0)),
	Output(new no.torsteinv.MS2.Game.MS2.Factory.Buildings.OutputMachine(0,0,null,0)),
	Input(new no.torsteinv.MS2.Game.MS2.Factory.Buildings.InputMachine(0,0,null,null,0)),
	Crafter(new no.torsteinv.MS2.Game.MS2.Factory.Buildings.Crafter(0,0,null,0)),
	Macerator(new no.torsteinv.MS2.Game.MS2.Factory.Buildings.Macerator(0,0,null,0)),
	Pipe(new no.torsteinv.MS2.Game.MS2.Pipe.BasicPipe(0,0,0,null));
	public static final MachineType[] TypeList = {Burner,Compresser,Mixer,Refiner,Output,Input,Crafter,Pipe};
	FactoryElement m = null;
	MachineType(FactoryElement Instance){
		m = Instance;
	}
	public Color decodeColor(){
		return m.decodeColor();
	}
}
