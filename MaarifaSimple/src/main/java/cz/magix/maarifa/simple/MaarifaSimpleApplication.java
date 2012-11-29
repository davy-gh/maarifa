package cz.magix.maarifa.simple;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MaarifaSimpleApplication {

	public MaarifaSimpleApplication() {
		// Creating context
		ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		// Destroying context
		context.close();
	}
}
