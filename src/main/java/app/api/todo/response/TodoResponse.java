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
	 * タスクのID
	 */
	private Long taskId;
	
	/**
	 * タスク名
	 */
	private String taskName;
	
	/**
	 * タスクが完了したか否か
	 */
	private Boolean done;
	
	/**
	 * メッセージ
	 * リクエストの成否を格納
	 */
	private String message;

}
