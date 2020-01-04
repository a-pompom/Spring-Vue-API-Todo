package app.api.todo.controller.test;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import app.api.todo.dao.TaskDao;
import app.api.todo.entity.Task;
import app.api.todo.main.ApiTodoApplication;
import app.api.todo.request.TodoRequest;
import app.api.todo.test.util.CsvDataSetLoader;

@AutoConfigureMockMvc
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)
@TestExecutionListeners({
	  DependencyInjectionTestExecutionListener.class,
	  TransactionalTestExecutionListener.class,
	  DbUnitTestExecutionListener.class
	})

@SpringBootTest(classes = ApiTodoApplication.class)
@Transactional
public class TodoControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private TaskDao taskDao;
	
	@AfterEach
	void tearDown() {
		taskDao.getEm().flush();
	}
	
	@Test
	void init処理が走って200が返る() throws Exception {
		
		this.mockMvc.perform(get("/todo/"))
			.andExpect(status().isOk());	
	}
	
	@Test
	@DatabaseSetup(value = "/todo-api/setUp/")
	void init処理が走ってレスポンスとしてタスク一覧が返される() throws Exception {
		this.mockMvc.perform(get("/todo/"))
		
		// $[index]でリスト要素の指定した要素へアクセスが可能となる
		// JsonPathはJavaでJSONを扱えるようにするためのライブラリ
		.andExpect(jsonPath("$[0].taskId", is(1))) // $はJSONのルートを表す
		.andExpect(jsonPath("$[0].taskName", is("test_task1")))
		.andExpect(jsonPath("$[0].done", is(false)))
		
		.andExpect(jsonPath("$[1].taskId", is(2)))
		.andExpect(jsonPath("$[1].taskName", is("test_task2")))
		.andExpect(jsonPath("$[1].done", is(true)));
	}
	
	@Test
	@DatabaseSetup(value = "/todo-api/setUp/")
	@ExpectedDatabase(value = "/todo-api/create/", assertionMode = DatabaseAssertionMode.NON_STRICT)
	void create処理が走ってレスポンスとして成功メッセージが返される() throws Exception {
		
		// 新規タスク
		Task task = new Task();
		task.setTaskName("new-task");
		task.setDone(true);
		
		TodoRequest request = new TodoRequest();
		request.setTask(task);
		
		// リクエストのボディに設定できるのはバイトの配列(JSON)のみなので、ObjectMapperでPOJO→JSON変換
		ObjectMapper mapper = new ObjectMapper();
        byte[] jsonRequest = mapper.writeValueAsBytes(request);
		
		this.mockMvc.perform(post("/todo/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andDo(print())
				.andExpect(status().isOk());
				
	}
	
	@Test
	@DatabaseSetup(value = "/todo-api/setUp/")
	@ExpectedDatabase(value = "/todo-api/delete/", assertionMode = DatabaseAssertionMode.NON_STRICT)
	void delete処理でタスクが削除される() throws Exception {
		
		this.mockMvc.perform(get("/todo/delete/2"));
		
	}
	
	@Test
	@DatabaseSetup(value = "/todo-api/setUp/")
	@ExpectedDatabase(value = "/todo-api/update/", assertionMode = DatabaseAssertionMode.NON_STRICT)
	void update処理でタスクが更新されレスポンスとして成功メッセージが返される() throws Exception {
		
		Task task = new Task();
		task.setTaskId(2L);
		task.setTaskName("test_task2_mod");
		task.setDone(true);
		
		TodoRequest request = new TodoRequest();
		request.setTask(task);
		
		ObjectMapper mapper = new ObjectMapper();
        byte[] jsonRequest = mapper.writeValueAsBytes(request);
		
		this.mockMvc.perform(post("/todo/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isOk());
	}

}
