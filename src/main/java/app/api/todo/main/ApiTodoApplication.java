package app.api.todo.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("app.api.todo")
public class ApiTodoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiTodoApplication.class, args);
	}

}
