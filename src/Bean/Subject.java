package Bean;

public class Subject {
	
	private int idsubject;
	private String name;
	private int year;
	private String object1;
	private	String object2;
	private String object3;
	private String object4;
	private String muro1;
	private String muro2;
	private String muro3;
	private String muro4;
	
	
	
	
	public Subject(int idsubject, String name, int year, String object1, String object2, String object3,
			String object4, String muro1, String muro2,String muro3,String muro4) {
		super();
		this.idsubject = idsubject;
		this.name = name;
		this.year = year;
		this.object1 = object1;
		this.object2 = object2;
		this.object3 = object3;
		this.object4 = object4;
		this.muro1= muro1;
		this.muro2= muro2;
		this.muro3= muro3;
		this.muro4= muro4;
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



	public String getMuro1() {
		return muro1;
	}



	public void setMuro1(String muro1) {
		this.muro1 = muro1;
	}



	public String getMuro2() {
		return muro2;
	}



	public void setMuro2(String muro2) {
		this.muro2 = muro2;
	}



	public String getMuro3() {
		return muro3;
	}



	public void setMuro3(String muro3) {
		this.muro3 = muro3;
	}



	public String getMuro4() {
		return muro4;
	}



	public void setMuro4(String muro4) {
		this.muro4 = muro4;
	}

}
