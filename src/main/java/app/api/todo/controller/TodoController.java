package app.api.todo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
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
public class TodoController {
	
	private TaskService service;
	
	public TodoController(TaskService service) {
		this.service = service;
	}
	
	/**
	 * 初期処理 画面に表示するタスク一覧を取得
	 * @return
	 */
	@GetMapping(value = "/")
	@ResponseBody
	private List<TodoResponse> init() {
		
		return service.getTodoResponse();
	}
	
	/**
	 * タスクを新規作成
	 * @param request 新規タスクを含んだリクエスト
	 * @return メッセージ
	 */
	@PostMapping(value = "/create")
	@ResponseBody
	private TodoResponse createTask(@RequestBody TodoRequest request) {
		
		Long taskId = service.createTask(request.getTask());
		
		TodoResponse response = new TodoResponse();
		response.setMessage("task created!");
		response.setTaskId(taskId);
		
		return response;
	}
	
	/**
	 * タスクを更新
	 * @param request 更新対象のタスクを含んだリクエスト
	 * @return メッセージ
	 */
	@PostMapping(value = "/update")
	@ResponseBody
	private TodoResponse updateTask(@RequestBody TodoRequest request) {
		
		service.updateTask(request.getTask());
		
		TodoResponse response = new TodoResponse();
		response.setMessage("task updated!");
		
		return response;
	}
	
	/**
	 * 対象のタスクを削除
	 * @param taskId 削除対象のタスク
	 * 
	 * @return メッセージ
	 */
	@GetMapping(value = "delete/{taskId}")
	@ResponseBody
	private TodoResponse dleteTask(@PathVariable Long taskId) {
		
		service.deleteTask(taskId);
		
		TodoResponse response = new TodoResponse();
		response.setMessage("task deleted!");
		
		return response;
	}

}
