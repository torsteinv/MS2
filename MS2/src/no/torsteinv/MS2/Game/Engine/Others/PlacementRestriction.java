package no.torsteinv.MS2.Game.Engine.Others;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import no.torsteinv.MS2.Entities.Selectable;

public enum PlacementRestriction {
	
	NON_OVERLAP_FACTORY(PlacementRestrictionMethods.class,"FUNC1",Integer.class,Integer.class,Selectable.class),
	NON_OVERLAP_GAME(PlacementRestrictionMethods.class,"FUNC2",Integer.class,Integer.class,Selectable.class),
	GAME_ON_CONCRETE(PlacementRestrictionMethods.class,"FUNC7",Integer.class,Integer.class,Selectable.class),
	FACTORY_LEFTSIDE(PlacementRestrictionMethods.class,"FUNC4",Integer.class,Integer.class,Selectable.class),
	FACTROY_RIGHTSIDE(PlacementRestrictionMethods.class,"FUNC3",Integer.class,Integer.class,Selectable.class),
	FACTORY_BOTTOM(PlacementRestrictionMethods.class,"FUNC5",Integer.class,Integer.class,Selectable.class),
	FACTROY_TOP(PlacementRestrictionMethods.class,"FUNC6",Integer.class,Integer.class,Selectable.class);
	
	
	Method test = null;
	
	@SafeVarargs
	PlacementRestriction(Class<? extends PlacementRestrictionMethods> cl,String testMethod, Class<? extends Object>... args){
		try {
			this.test = cl.getMethod(testMethod, args);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	
	public boolean check(Object...args){
		try {
			return (boolean) test.invoke(null, args);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return false;
	}
}
