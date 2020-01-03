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
		q.append("select * from taskList ");
		
		return findResultList(q.createQuery(Task.class, getEm()));
	}
	
}
