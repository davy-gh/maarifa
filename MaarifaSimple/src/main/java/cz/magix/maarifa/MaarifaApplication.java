package cz.magix.maarifa;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MaarifaApplication {

	public MaarifaApplication() {
		// Creating context
		ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		// Destroying context
		context.close();
	}
}
