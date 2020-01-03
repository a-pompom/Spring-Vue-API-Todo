package app.api.todo.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import app.api.todo.entity.Task;
import app.api.todo.util.QueryBuilder;

/**
 * タスクを管理するDaoクラス
 * @author aoi
 *
 */
@Component
public class TaskDao extends BaseDao<Task> {
	
	/**
	 * 画面表示用に全てのタスクを取得
	 * @return DB上に存在する全てのタスクEntity
	 */
	public List<Task> findAll() {
		
		QueryBuilder q = new QueryBuilder();
		q.append("select * from task_list ");
		
		return findResultList(q.createQuery(Task.class, getEm()));
	}
	
	/**
	 * タスクを削除
	 * @param taskId 削除対象のID
	 */
	public void deleteTask(long taskId) {
		
		QueryBuilder q = new QueryBuilder();
		q.append("delete from task_list ");
		q.append(" where task_id = :taskId ").setParam("taskId", taskId);
		
		q.createQuery(Task.class, getEm()).executeUpdate();
	}
	
}
