package Bean;

import java.util.Date;

public class Ranking {
	
	private int idRanking;
	private int user;
	private Date date;
	private int rank1;
	private int rank2;
	private int rank3;
	private int rank4;
	private int totalrank;
	private int idroom;
	private String thumbnail;




public Ranking(int idRanking, int user, Date date, int rank1, int rank2,
		int rank3, int rank4,int totalrank, int idroom) {
	this.idRanking = idRanking;
	this.user = user;
	this.date = date;
	this.rank1 = rank1;
	this.rank2 = rank2;
	this.rank3 = rank3;
	this.rank4 = rank4;
	this.totalrank = totalrank;
	this.idroom = idroom;
	
}


public Ranking(String thumbnail, int idRanking, int user, Date date, int rank1, int rank2,
		int rank3, int rank4,int totalrank, int idroom) {
	this.thumbnail = thumbnail;
	this.idRanking = idRanking;
	this.user = user;
	this.date = date;
	this.rank1 = rank1;
	this.rank2 = rank2;
	this.rank3 = rank3;
	this.rank4 = rank4;
	this.totalrank = totalrank;
	this.idroom = idroom;
	
}



public String getThumbnail() {
	return thumbnail;
}


public void setThumbnail(String thumbnail) {
	this.thumbnail = thumbnail;
}


public Ranking() {
	super();
}


public int getIdRanking() {
	return idRanking;
}


public void setIdRanking(int idRanking) {
	this.idRanking = idRanking;
}


public int getUser() {
	return user;
}


public void setUser(int user) {
	this.user = user;
}


public Date getDate() {
	return date;
}


public void setDate(Date date) {
	this.date = date;
}


public int getRank1() {
	return rank1;
}


public void setRank1(int rank1) {
	this.rank1 = rank1;
}


public int getRank2() {
	return rank2;
}


public void setRank2(int rank2) {
	this.rank2 = rank2;
}


public int getRank3() {
	return rank3;
}


public void setRank3(int rank3) {
	this.rank3 = rank3;
}


public int getRank4() {
	return rank4;
}


public void setRank4(int rank4) {
	this.rank4 = rank4;
}


public int getTotalrank() {
	return totalrank;
}


public void setTotalrank(int totalrank) {
	this.totalrank = totalrank;
}


public int getIdroom() {
	return idroom;
}


public void setIdroom(int idroom) {
	this.idroom = idroom;
}




}

