package app.api.todo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import app.api.todo.request.TodoRequest;
import app.api.todo.response.TodoResponse;
import app.api.todo.service.TaskService;

/**
 * Todoリストの機能を管理するコントローラ
 * @author aoi
 *
 */
@Controller
@RequestMapping(value = "/todo")
@CrossOrigin(origins = "http://localhost:8080")
public class TodoController {
	
	private TaskService service;
	
	public TodoController(TaskService service) {
		this.service = service;
	}
	
	@GetMapping(value = "/")
	@ResponseBody
	private List<TodoResponse> init() {
		
		return service.getTodoResponse();
	}
	
	@PostMapping
	@ResponseBody
	private TodoResponse createTask(@RequestBody TodoRequest request) {
		
		service.createTask(request.getTask());
		
		TodoResponse response = new TodoResponse();
		response.setMessage("task created!");
		
		return response;
	}
	
	@PostMapping
	@ResponseBody
	private TodoResponse updateTask(@RequestBody TodoRequest request) {
		
		service.createTask(request.getTask());
		
		TodoResponse response = new TodoResponse();
		response.setMessage("task updated!");
		
		return response;
	}
	
	@GetMapping
	@ResponseBody
	private TodoResponse dleteTask(@PathVariable Long targetId) {
		
		service.deleteTask(targetId);
		
		TodoResponse response = new TodoResponse();
		response.setMessage("task deleted!");
		
		return response;
	}

}
