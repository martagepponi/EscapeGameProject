package Bean;

public class Subject {
	
	int idsubject;
	String name;
	int year;
	String object1;
	String object2;
	String object3;
	String object4;
	
	
	
	public Subject(int idsubject, String name, int year, String object1, String object2, String object3,
			String object4) {
		super();
		this.idsubject = idsubject;
		this.name = name;
		this.year = year;
		this.object1 = object1;
		this.object2 = object2;
		this.object3 = object3;
		this.object4 = object4;
	
	}
	
	
	
	public int getIdsubject() {
		return idsubject;
	}
	public void setIdsubject(int idsubject) {
		this.idsubject = idsubject;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getObject1() {
		return object1;
	}
	public void setObject1(String object1) {
		this.object1 = object1;
	}
	public String getObject2() {
		return object2;
	}
	public void setObject2(String object2) {
		this.object2 = object2;
	}
	public String getObject3() {
		return object3;
	}
	public void setObject3(String object3) {
		this.object3 = object3;
	}
	public String getObject4() {
		return object4;
	}
	public void setObject4(String object4) {
		this.object4 = object4;
	}

}
