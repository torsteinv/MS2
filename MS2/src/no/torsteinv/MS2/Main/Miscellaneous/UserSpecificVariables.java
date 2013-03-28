package no.torsteinv.MS2.Main.Miscellaneous;

import java.awt.Point;
import java.util.HashMap;

public class UserSpecificVariables {
	public HashMap<String,Object> Properties = new HashMap<String,Object>();
	
	public UserSpecificVariables(){
		Properties.put("Money", 1000);
		Properties.put("Spawn", new Point(0,0));
	}

	public Point getSpawn() {
		return (Point) Properties.get("Spawn");
	}
	
	public int getMoney() {
		return (int) Properties.get("Money");
	}
}
