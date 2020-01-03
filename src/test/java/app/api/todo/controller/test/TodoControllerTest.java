package app.api.todo.controller.test;

import static org.hamcrest.CoreMatchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import app.api.todo.main.ApiTodoApplication;

@AutoConfigureMockMvc
@SpringBootTest(classes = ApiTodoApplication.class)
public class TodoControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void init処理が走って200が返る() throws Exception {
		
		this.mockMvc.perform(get("/todo/init"))
			.andExpect(status().isOk());	
	}
	
	@Test
	void init処理が走ってレスポンスとしてメッセージが返される() throws Exception {
		this.mockMvc.perform(get("/todo/init")).andDo(print())
		.andExpect(jsonPath("message", is("hello")));
	}

}
