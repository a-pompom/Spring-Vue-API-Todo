package app.api.todo.request;

import app.api.todo.entity.Task;
import lombok.Data;

@Data
public class TodoRequest {
	
	private Task task;
	
	private String message;
	

}
