package app.api.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import app.api.todo.response.TodoResponse;

/**
 * Todoリストの機能を管理するコントローラ
 * @author aoi
 *
 */
@Controller
@RequestMapping(value = "/todo")
@CrossOrigin(origins = "http://localhost:8080")
public class TodoController {
	
	@GetMapping(value = "/init")
	@ResponseBody
	private TodoResponse init() {
		
		TodoResponse response = new TodoResponse();
		response.setMessage("hello");
		
		return response;
	}

}
