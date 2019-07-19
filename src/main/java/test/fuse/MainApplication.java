package test.fuse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("test.fuse")
public class MainApplication extends SpringBootServletInitializer{

	public static void main(String... args) {
		SpringApplication.run(MainApplication.class, args);
	}
}