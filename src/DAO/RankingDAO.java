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

		String query = "SELECT room.thumbnail, ranking.idranking, ranking.user, ranking.date, ranking.rank1, ranking.rank2, ranking.rank3, ranking.rank4, ranking.totalrank, ranking.idroom, room.title, user.name,user.surname FROM escapegame.room JOIN escapegame.ranking JOIN escapegame.user WHERE room.idroom=ranking.idroom AND ranking.user=user.iduser AND user=?;";
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
				String title = result.getString("title");
				String studentName = result.getString("user.name");
				String studentSurname = result.getString("user.surname");

				Ranking ranking = new Ranking(thumbnail, idRanking, user, date, rank1, rank2, rank3, rank4, totalrank, idroom, title, studentName, studentSurname);
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



	//DOCENTE
	//PUNTEGGI UTENTE LOGGATO E REPERIMENTO IMMAGINE STANZA GIOCATA 

	public List<Ranking> findRankingByProf (int iduser){

		String query = "SELECT room.thumbnail, room.title, ranking.idranking, ranking.user, ranking.date, ranking.rank1, ranking.rank2, ranking.rank3, ranking.rank4, ranking.totalrank, ranking.idroom, user.name, user.surname  FROM escapegame.room JOIN escapegame.ranking JOIN escapegame.user WHERE room.idroom=ranking.idroom AND ranking.user=user.iduser AND room.idprof=?;";
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
				String title = result.getString ("title");
				String studentName = result.getString("user.name");
				String studentSurname = result.getString("user.surname");

				Ranking ranking = new Ranking(thumbnail, idRanking, user, date, rank1, rank2, rank3, rank4, totalrank, idroom, title, studentName, studentSurname);

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



	// METODO PER OTTENERE ID STANZE GIOCATE DA UN UTENTE SPECIFICO, CON TOTAL RANK DIVERSO DA 0
	public List<Integer> idRoomList (int iduser){

		String query = "SELECT escapegame.ranking.idroom  FROM escapegame.ranking AS ranking WHERE ranking.totalrank != 0 AND ranking.user =?;";
		List<Integer> idRoomList = new ArrayList<>();
		ResultSet result = null;
		PreparedStatement pstatement = null;
		try {
			pstatement = connection.prepareStatement(query);
			pstatement.setInt(1, iduser);
			result = pstatement.executeQuery();
			while (result.next()) {
				int idroom = result.getInt("idroom");
				idRoomList.add(idroom);
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
		return idRoomList;

	}


	//INSERISCO IN TABELLA IL PUNTEGGIO DEL MINIGIOCO 

	public void InsertRank(int minigameNumber, int score,int iduser,int idroom) {

		String querySelect = "SELECT * FROM escapegame.ranking WHERE user =? AND idroom =?";			
		String queryInsert = "INSERT INTO escapegame.ranking (user, rank"+minigameNumber+", idroom) VALUES(?,?,?)";
		String queryUpdate = "UPDATE escapegame.ranking SET rank"+minigameNumber+"=? WHERE idranking= ?";
		PreparedStatement pstatement = null;
		PreparedStatement pstatement2 = null;
		ResultSet rs = null;

		try {

			pstatement = connection.prepareStatement(querySelect);
			pstatement.setInt(1, iduser);
			pstatement.setInt(2, idroom);


			rs = pstatement.executeQuery();
			if(rs.next()) {
				int idranking= rs.getInt("idranking");
				pstatement2 = connection.prepareStatement(queryUpdate);
				pstatement2.setInt(1, score);
				pstatement2.setInt(2, idranking);

				pstatement2.executeUpdate();

			}else {

				pstatement2 = connection.prepareStatement(queryInsert);
				pstatement2.setInt(1, iduser);
				pstatement2.setInt(2, score);
				pstatement2.setInt(3, idroom);

				pstatement2.executeUpdate();

			}

			rs.close();
			rs=null;
			pstatement.close();
			pstatement=null;
			pstatement2.close();
			pstatement2=null;

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) {
					rs.close();
				}
				if(pstatement!= null) {
					pstatement.close();
				}
				if(pstatement2!= null) {
					pstatement2.close();
				}
			} catch (Exception e) {

				e.printStackTrace();
			}
		}

	}

	//INSERISCO IN TABELLA PUNTEGGIO FINALE

	public void insertTotalRank(int totalRank,int idUser, int idRoom) {

		String queryUpdate = "UPDATE escapegame.ranking SET totalrank=? WHERE user= ? AND idroom = ?";	
		PreparedStatement pstatement = null;


		try {

			pstatement = connection.prepareStatement(queryUpdate);
			pstatement.setInt(1, totalRank);
			pstatement.setInt(2, idUser);
			pstatement.setInt(3, idRoom);

			pstatement.executeUpdate();

		}catch (SQLException e) {

			e.printStackTrace();
		} finally {
			try {

				if(pstatement!= null) {
					pstatement.close();
				}

			} catch (Exception e) {

				e.printStackTrace();
			}
		}


	}


	//PUNTEGGI UTENTE CHE HA APPENA GIOCATO A UNA SPECIFICA STANZA 

	public Ranking findRankingByRoomAndUser (int iduser, int idroom){

		String query = "SELECT room.thumbnail, room.title, ranking.idranking, ranking.user, ranking.date, ranking.rank1, ranking.rank2, ranking.rank3, ranking.rank4, ranking.totalrank, user.name, user.surname FROM escapegame.room JOIN escapegame.ranking JOIN escapegame.user WHERE room.idroom=? AND ranking.user=user.iduser AND user=?;";
		ResultSet result = null;
		PreparedStatement pstatement = null;
		Ranking ranking = null;
		try {
			pstatement = connection.prepareStatement(query);
			pstatement.setInt(1, iduser);
			pstatement.setInt(2, idroom);
			result = pstatement.executeQuery();
			if (result.next()) {
				String thumbnail = result.getString("thumbnail");
				int idRanking =result.getInt("idranking");
				int user= result.getInt("user");
				Date date= result.getDate("date");
				int rank1= result.getInt("rank1");
				int rank2= result.getInt("rank2");
				int rank3= result.getInt("rank3");
				int rank4= result.getInt("rank4");
				int totalrank= result.getInt("totalrank");
				String title = result.getString("title");
				String studentName = result.getString("user.name");
				String studentSurname = result.getString("user.surname");

				ranking = new Ranking(thumbnail, idRanking, user, date, rank1, rank2, rank3, rank4, totalrank, idroom, title, studentName, studentSurname);

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
		return ranking;
	}

}
