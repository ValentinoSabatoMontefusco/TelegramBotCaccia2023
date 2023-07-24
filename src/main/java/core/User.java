package core;

public class User {
	private String name;
	private Long id;
	private int status;
	
	
	public User(String name, Long id, int status) {
		this.name = name;
		this.id = id;
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public void increaseStatus() {
		
		this.status++;
	}
	
}
