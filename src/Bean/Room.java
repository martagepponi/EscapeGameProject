package Bean;

import java.util.Date;

public class Room {
	private int idRoom;
	private Date date;
	private String subject;
	private String profName;  
	private String profSurname;
	private int year;
	private String password;
	private int minigame1;
	private int minigame2;
	private int minigame3;
	private int finalgame;
	private String thumbnail;



	public Room(int idRoom, Date date,String subject, String profName, String password, int minigame1, int minigame2,
			int minigame3, int finalgame, String thumbnail) {
		this.idRoom = idRoom;
		this.date = date;
		this.subject = subject;
		this.profName = profName;
		this.password = password;
		this.minigame1 = minigame1;
		this.minigame2 = minigame2;
		this.minigame3 = minigame3;
		this.finalgame = finalgame;
		this.thumbnail =thumbnail;
	}

	
	public Room(int idRoom, Date date,String subject, String profName, String profSurname, int year, String thumbnail) {
		this.idRoom = idRoom;
		this.date = date;
		this.subject = subject;
		this.profName = profName;
		this.profSurname = profSurname;
		this.year = year;
		this.thumbnail =thumbnail;
	}


	public Room() {
		super();
	}

	
	

	public String getProfSurname() {
		return profSurname;
	}


	public void setProfSurname(String profSurname) {
		this.profSurname = profSurname;
	}


	public int getYear() {
		return year;
	}


	public void setYear(int year) {
		this.year = year;
	}


	public int getIdRoom() {
		return idRoom;
	}
	public void setIdRoom(int idRoom) {
		this.idRoom = idRoom;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getProfName() {
		return profName;
	}
	public void setProfName(String profName) {
		this.profName = profName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getMinigame1() {
		return minigame1;
	}
	public void setMinigame1(int minigame1) {
		this.minigame1 = minigame1;
	}
	public int getMinigame2() {
		return minigame2;
	}
	public void setMinigame2(int minigame2) {
		this.minigame2 = minigame2;
	}
	public int getMinigame3() {
		return minigame3;
	}
	public void setMinigame3(int minigame3) {
		this.minigame3 = minigame3;
	}
	public int getFinalgame() {
		return finalgame;
	}
	public void setFinalgame(int finalgame) {
		this.finalgame = finalgame;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}




}

