package app.api.todo.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import app.api.todo.dao.TaskDao;
import app.api.todo.entity.Task;
import app.api.todo.response.TodoResponse;

@Service
public class TaskService {
	
	private TaskDao taskDao;
	
	public TaskService(TaskDao taskDao) {
		this.taskDao = taskDao;
	}
	
	/**
	 * DBからレスポンスに格納するタスクのリストを取得
	 * @return JSONとして返すためのレスポンスのリスト
	 */
	public List<TodoResponse> getTodoResponse() {
		
		List<Task> taskList = taskDao.findAll();
		
		return convertToResponse(taskList);
	}
	
	/**
	 * タスクを新規作成
	 * @param newTask 新規作成対象のタスクEntity
	 */
	@Transactional
	public void createTask(Task newTask) {
		
		taskDao.saveOrUpdate(newTask);
	}
	
	/**
	 * タスクを更新
	 * @param updateTask 更新対象のタスクEntity
	 */
	@Transactional
	public void updateTask(Task updateTask) {
		
		taskDao.saveOrUpdate(updateTask);
	}
	
	/**
	 * タスクを削除
	 * @param taskId 削除対象のタスクID
	 */
	@Transactional
	public void deleteTask(Long taskId) {
		
		taskDao.deleteTask(taskId);
	}
	
	/**
	 * DBから取得したEntityのリストをレスポンスへ変換
	 * @param taskList DBから取得したタスクのリスト
	 * @return JSONとして返すためのレスポンスのリスト
	 */
	private List<TodoResponse> convertToResponse(List<Task> taskList) {
		
		List<TodoResponse> todoResponseList = new ArrayList<TodoResponse>();
		
		for (Task task : taskList) {
			TodoResponse theResponse = new TodoResponse();
			
			theResponse.setTaskId(task.getTaskId());
			theResponse.setTaskName(task.getTaskName());
			theResponse.setDone(task.getDone());
			
			todoResponseList.add(theResponse);
		}
		
		return todoResponseList;
	}

}
