package cz.magix.maarifa.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Deprecated
public class BeanUtils {

	public static List<PropertyDescriptor> getBeanPropertyDescriptor(final Class<?> beanClass) throws IntrospectionException {
		// Oracle bug 4275879: Introspector does not consider superinterfaces of
		// an interface
		if (beanClass.isInterface()) {
			List<PropertyDescriptor> propertyDescriptors = new ArrayList<PropertyDescriptor>();

			for (Class<?> cls : beanClass.getInterfaces()) {
				propertyDescriptors.addAll(getBeanPropertyDescriptor(cls));
			}

			BeanInfo info = Introspector.getBeanInfo(beanClass);
			propertyDescriptors.addAll(Arrays.asList(info.getPropertyDescriptors()));

			return propertyDescriptors;
		} else {
			BeanInfo info = Introspector.getBeanInfo(beanClass);
			return Arrays.asList(info.getPropertyDescriptors());
		}
	}

}
