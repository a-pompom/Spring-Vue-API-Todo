package app.api.todo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * Todoリストの各タスクを格納するためのEntity
 * @author aoi
 *
 */
@Entity
@Table(name = "taskList")
@Data
public class Task extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "task_id")
	private Long taskId;
	
	@Column(name = "task_name")
	private String taskName;
	
	@Column(name = "done")
	private Boolean done;

}
