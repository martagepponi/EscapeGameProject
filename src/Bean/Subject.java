package Bean;

public class Subject {
	
	private int idsubject;
	private String name;
	private int year;
	private String object1;
	private	String object2;
	private String object3;
	private String object4;
	private String wall1;
	private String wall2;
	private String wall3;
	private String wall4;
	
	
	
	
	public Subject(int idsubject, String name, int year, String object1, String object2, String object3,
			String object4, String wall1, String wall2,String wall3,String wall4) {
		super();
		this.idsubject = idsubject;
		this.name = name;
		this.year = year;
		this.object1 = object1;
		this.object2 = object2;
		this.object3 = object3;
		this.object4 = object4;
		this.wall1= wall1;
		this.wall2= wall2;
		this.wall3= wall3;
		this.wall4= wall4;
	}
	public Subject(int idsubject, String name, int year) {
		super();
		this.idsubject = idsubject;
		this.name = name;
		this.year = year;
		this.object1 = "asse";
		this.object2 = "fuoco";
		this.object3 = "cane";
		this.object4 = "archibugio";
		this.wall1= "muro1";
		this.wall2= "muro2";
		this.wall3= "muro3";
		this.wall4= "muro4";
	}
	
	public Subject () {
		super();
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



	public String getWall1() {
		return wall1;
	}



	public void setWall1(String wall1) {
		this.wall1 = wall1;
	}



	public String getWall2() {
		return wall2;
	}



	public void setWall2(String wall2) {
		this.wall2 = wall2;
	}



	public String getWall3() {
		return wall3;
	}



	public void setWall3(String wall3) {
		this.wall3 = wall3;
	}



	public String getWall4() {
		return wall4;
	}



	public void setWall4(String wall4) {
		this.wall4 = wall4;
	}

}
