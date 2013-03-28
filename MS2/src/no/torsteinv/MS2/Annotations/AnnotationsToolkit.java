package no.torsteinv.MS2.Annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class AnnotationsToolkit {
	public static Method findStaticMethodWithAnnotaion(Class<? extends Object> c,Class<? extends Annotation> a){
		for(Method m : c.getMethods())
			if(m.isAnnotationPresent(a) && Modifier.isStatic(m.getModifiers()))
				return m;
		return null;
	}
	public static Method findMethodWithAnnotaion(Class<? extends Object> c,Class<? extends Annotation> a){
		for(Method m : c.getMethods())
			if(m.isAnnotationPresent(a))
				return m;
		return null;
	}
	public static Field findStaticFieldWithAnnotaion(Class<? extends Object> c,Class<? extends Annotation> a){
		for(Field f : c.getFields())
			if(f.isAnnotationPresent(a) && Modifier.isStatic(f.getModifiers()))
				return f;
		return null;
	}
	public static Field findFieldWithAnnotaion(Class<? extends Object> c,Class<? extends Annotation> a){
		for(Field f : c.getFields())
			if(f.isAnnotationPresent(a))
				return f;
		return null;
	}
}
