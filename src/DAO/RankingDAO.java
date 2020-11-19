package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Bean.Ranking;
import Bean.Room;

public class RankingDAO {
	private Connection connection;

	public RankingDAO(Connection conn) {
		this.connection= conn;
	
	}
	
	//PUNTEGGI UTENTE LOGGATO E REPERIMENTO IMMAGINE STANZA GIOCATA 
	
	public List<Ranking> findAllRanking (int iduser){
		
		String query = "SELECT room.thumbnail, ranking.idranking, ranking.user, ranking.date, ranking.rank1, ranking.rank2, ranking.rank3, ranking.rank4, ranking.totalrank, ranking.idroom FROM escapegame.room JOIN escapegame.ranking WHERE room.idroom=ranking.idroom AND user=?;";
		List<Ranking> Rankings = new ArrayList<>();
		ResultSet result = null;
		PreparedStatement pstatement = null;
		try {
			pstatement = connection.prepareStatement(query);
			pstatement.setInt(1, iduser);
			result = pstatement.executeQuery();
			while (result.next()) {
				String thumbnail = result.getString("thumbnail");
				int idRanking =result.getInt("idranking");
				int user= result.getInt("user");
				Date date= result.getDate("date");
				int rank1= result.getInt("rank1");
				int rank2= result.getInt("rank2");
				int rank3= result.getInt("rank3");
				int rank4= result.getInt("rank4");
				int totalrank= result.getInt("totalrank");
				int idroom = result.getInt("idroom");
				
				Ranking ranking = new Ranking(thumbnail, idRanking, user, date, rank1, rank2, rank3, rank4, totalrank, idroom);
				
				//AGGIUNGO ALLA LISTA TUTTI I PUNTEGGI RISULTANTI DALLA QUERY
				Rankings.add(ranking);
				
				
			}
				
			}catch (SQLException e) {
				e.printStackTrace();
			}finally {
				  try {
					  result.close();
				  } 
				  catch (Exception e2) {
					  e2.printStackTrace();
				  }
				  try {
					  pstatement.close();
				  } 
				  catch (Exception e3) {
					  e3.printStackTrace();
				  }
			}
return Rankings;
}
	
	
	
	//PUNTEGGI UTENTE LOGGATO E REPERIMENTO IMMAGINE STANZA GIOCATA 
	
		public List<Ranking> findRankingByProf (int iduser){
			
			String query = "SELECT room.thumbnail, ranking.idranking, ranking.user, ranking.date, ranking.rank1, ranking.rank2, ranking.rank3, ranking.rank4, ranking.totalrank, ranking.idroom  FROM escapegame.room JOIN escapegame.ranking WHERE room.idroom=ranking.idroom AND room.idprof=?;";
			List<Ranking> Rankings = new ArrayList<>();
			ResultSet result = null;
			PreparedStatement pstatement = null;
			try {
				pstatement = connection.prepareStatement(query);
				pstatement.setInt(1, iduser);
				result = pstatement.executeQuery();
				while (result.next()) {
					String thumbnail = result.getString("thumbnail");
					int idRanking =result.getInt("idranking");
					int user= result.getInt("user");
					Date date= result.getDate("date");
					System.out.println(date);
					int rank1= result.getInt("rank1");
					System.out.println(rank1);
					int rank2= result.getInt("rank2");
					System.out.println(rank2);
					int rank3= result.getInt("rank3");
					int rank4= result.getInt("rank4");
					int totalrank= result.getInt("totalrank");
					int idroom = result.getInt("idroom");
					
					Ranking ranking = new Ranking(thumbnail, idRanking, user, date, rank1, rank2, rank3, rank4, totalrank, idroom);
					
					//AGGIUNGO ALLA LISTA TUTTI I PUNTEGGI RISULTANTI DALLA QUERY
					Rankings.add(ranking);
					System.out.println(ranking);
					
				}
					
				}catch (SQLException e) {
					e.printStackTrace();
				}finally {
					  try {
						  result.close();
					  } 
					  catch (Exception e2) {
						  e2.printStackTrace();
					  }
					  try {
						  pstatement.close();
					  } 
					  catch (Exception e3) {
						  e3.printStackTrace();
					  }
				}
	return Rankings;
		}
}
