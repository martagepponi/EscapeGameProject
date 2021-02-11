package Bean;

public class User {
	public static final String DOCENTE="docente";
	public static final String STUDENTE="studente";
	private int iduser;
	private String name;
	private String surname;
	private String username;
	private String password;
	private String type;
	
	
	
	public User(int iduser, String name, String surname, String username, String password, String type) {
		super();
		this.iduser = iduser;
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.password = password;
		this.type = type;
	}
	
	public User() {
		super();
	}
	
	public int getIduser() {
		return iduser;
	}

	public void setIduser(int iduser) {
		this.iduser = iduser;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
