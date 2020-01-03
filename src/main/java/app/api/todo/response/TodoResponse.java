package app.api.todo.response;

import lombok.Data;

/**
 * Todoリストで扱うJSONを表すレスポンス
 * @author aoi
 *
 */
@Data
public class TodoResponse {
	
	/**
	 * メッセージ
	 */
	private String message;

}
