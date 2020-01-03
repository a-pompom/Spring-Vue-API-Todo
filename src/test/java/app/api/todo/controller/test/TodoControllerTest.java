package app.api.todo.controller.test;

import static org.hamcrest.CoreMatchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

import app.api.todo.main.ApiTodoApplication;
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
	
	@Test
	void init処理が走って200が返る() throws Exception {
		
		this.mockMvc.perform(get("/todo/"))
			.andExpect(status().isOk());	
	}
	
	@Test
	@DatabaseSetup(value = "/todo-api/setUp/")
	void init処理が走ってレスポンスとしてメッセージが返される() throws Exception {
		this.mockMvc.perform(get("/todo/")).andDo(print())
		.andExpect(jsonPath("$[0].taskId", is(1))) // $はJSONのルートを表す
		.andExpect(jsonPath("$[0].taskName", is("test_task1")))
		.andExpect(jsonPath("$[0].done", is(false)))
		.andExpect(jsonPath("$[1].taskId", is(2))) // $はJSONのルートを表す
		.andExpect(jsonPath("$[1].taskName", is("test_task2")))
		.andExpect(jsonPath("$[1].done", is(true)));
	}

}
