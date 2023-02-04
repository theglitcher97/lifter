package lib;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

public class Factory {
	public static <T> T get(Class<T> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		T fatherObject = clazz.getDeclaredConstructor().newInstance();
		List<Field> fields = Arrays.stream(clazz.getDeclaredFields()).filter(field -> field.isAnnotationPresent(Inject.class)).toList();
		for(Field field : fields){
			field.setAccessible(true);
			field.set(clazz, Factory.get(field.getType()));
			field.setAccessible(false);
		}
		return fatherObject;
	}
}
