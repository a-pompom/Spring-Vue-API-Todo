package app.api.todo.dao.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import app.api.todo.dao.TaskDao;
import app.api.todo.entity.Task;
import app.api.todo.main.ApiTodoApplication;
import app.api.todo.test.util.CsvDataSetLoader;

/**
 * Daoレイヤーのテストクラス
 * CRUD処理がDBに反映されるか検証
 * @author aoi
 *
 */
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)
@TestExecutionListeners({
	  DependencyInjectionTestExecutionListener.class,
	  TransactionalTestExecutionListener.class,
	  DbUnitTestExecutionListener.class
	})
@SpringBootTest(classes = {ApiTodoApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
public class TaskDaoTest {

	@Autowired
	private TaskDao taskDao;
	
//	public TaskDaoTest(TaskDao taskDao) {
//		this.taskDao = taskDao;
//	}
	
	// テストメソッド実行後の状態をデータベースに反映させるための処理
	// 通常、更新系の処理はトランザクションがコミットされるタイミングでデータベースと同期化されるが、
	// テスト処理ではコミットしないため、明示的に同期化を行う
	@AfterEach
	void tearDown() {
		taskDao.getEm().flush();
	}
//	@Test
//	@DatabaseSetup(value = "/todo-api/setUp/")
//	void DBからタスク一覧を取得できる() {
//		
//		int actual = taskDao.findAll().size();
//		
//		assertThat(actual, is(2));
//	}
	
	@Test
	@DatabaseSetup(value = "/todo-api/setUp/")
	@ExpectedDatabase(value = "/todo-api/create/", assertionMode = DatabaseAssertionMode.NON_STRICT)
	void saveOrUpdateで新規タスクを登録できる() {
		
		Task entity = new Task();
		entity.setTaskName("new-task");
		entity.setDone(true);
		
		taskDao.saveOrUpdate(entity);
	}
}
