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
		
		
		
		
		
		//INSERISCO IN TABELLA IL PUNTEGGIO DEL MINIGIOCO 
		
		
				public void InsertRank(int numeroMinigioco, int punteggio,int iduser,int idroom) {
					
					
					
					String querySelect = "SELECT * FROM escapegame.ranking WHERE user =? AND idroom =?";			
					String queryInsert = "INSERT INTO escapegame.ranking (user, rank"+numeroMinigioco+", idroom) VALUES(?,?,?)";
					String queryUpdate = "UPDATE escapegame.ranking SET rank"+numeroMinigioco+"=? WHERE idranking= ?";
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
						      pstatement2.setInt(1, punteggio);
						      pstatement2.setInt(2, idranking);
						      
						      pstatement2.executeUpdate();
							
						}else {
						
						
							pstatement2 = connection.prepareStatement(queryInsert);
							pstatement2.setInt(1, iduser);
							pstatement2.setInt(2, punteggio);
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
				
				//INSERISCO IN TABELLA IL PUNTEGGIO DEL MINIGIOCO 2

				public void InsertRank2(int punteggio,int iduser,int idroom) {
					
					String query = "INSERT INTO ranking(user, rank2, idroom) VALUES(?,?,?)";
					PreparedStatement pstatement = null;
					
					try {
						
						pstatement = connection.prepareStatement(query);
						pstatement.setInt(1, iduser);
						pstatement.setInt(2, punteggio);
						pstatement.setInt(3, idroom);
						
						pstatement.executeUpdate();
						
					} catch (SQLException e) {
						
						e.printStackTrace();
					} finally {
						try {
							pstatement.close();
						} catch (Exception e) {
							
							e.printStackTrace();
						}
					}
					
				}		
				
				
				//INSERISCO IN TABELLA IL PUNTEGGIO DEL MINIGIOCO 3

						public void InsertRank3(int punteggio,int iduser,int idroom) {
							
							String query = "INSERT INTO ranking(user, rank3, idroom) VALUES(?,?,?)";
							PreparedStatement pstatement = null;
							
							try {
								
								pstatement = connection.prepareStatement(query);
								pstatement.setInt(1, iduser);
								pstatement.setInt(2, punteggio);
								pstatement.setInt(3, idroom);
								
								pstatement.executeUpdate();
								
							} catch (SQLException e) {
								
								e.printStackTrace();
							} finally {
								try {
									pstatement.close();
								} catch (Exception e) {
									
									e.printStackTrace();
								}
							}
							
						}		
		
		
		
		
						//PUNTEGGI UTENTE CHE HA APPENA GIOCATO A UNA SPECIFICA STANZA 
						
						public Ranking findRankingByRoomAndUser (int iduser, int idroom){
							
							String query = "SELECT room.thumbnail, ranking.idranking, ranking.user, ranking.date, ranking.rank1, ranking.rank2, ranking.rank3, ranking.rank4, ranking.totalrank FROM escapegame.room JOIN escapegame.ranking WHERE room.idroom=? AND user=?;";
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
									
								 ranking = new Ranking(thumbnail, idRanking, user, date, rank1, rank2, rank3, rank4, totalrank, idroom);
									
									
									
									
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
