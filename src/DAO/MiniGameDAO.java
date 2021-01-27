package DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import Bean.AbstractMinigame;
import Bean.Affinitygame;
import Bean.Hangmangame;
import Bean.Quizgame;
import Bean.User;



public class MiniGameDAO {
	private static final Logger log = Logger.getLogger(MiniGameDAO.class.getName());
	private Connection connection;
	private static final String HANGMAN = "hangmangame";
	private static final String AFFINITY = "affinitygame";
	private static final String QUIZ = "quizgame";
	public MiniGameDAO (Connection conn) {
		this.connection= conn;
		}
	//CERCO IL MINIGIOCO IN BASE ALL'ID E LO COSTRUISCO IN BASE AL TIPO
	
	
	
	public AbstractMinigame findById(int Id_minigame) {

		String query = "SELECT * FROM `minigame` WHERE `idminigame`=? ;";
		AbstractMinigame minigame = null;
		ResultSet result = null;
		PreparedStatement pstatement = null;
		

		try {
			pstatement = connection.prepareStatement(query);
			pstatement.setInt(1, Id_minigame);
			
			result = pstatement.executeQuery();
			if(result.next()) {

			    //RIPRENDO RISULTATI DELLA QUERY
				String type=result.getString("type");
				int subject= result.getInt("idsubject");
				//COSTRUISCO GIOCO IN BASE AL TIPO
				if(HANGMAN.equalsIgnoreCase(type)) {
					 Hangmangame hangmangame= new Hangmangame();
					
					String query1 ="SELECT * FROM `hangmangame` WHERE `idhangman`=? ;";
					 
					ResultSet result1 = null;
					PreparedStatement pstate1 = null;
					
					pstate1 = connection.prepareStatement(query1);
					pstate1.setInt(1, Id_minigame);
					
					result1 = pstate1.executeQuery();
					if(result1.next()) {
					hangmangame.setIdHangman(result1.getInt("idhangman"));
					hangmangame.setType(type);
					hangmangame.setIdsubject(subject);
				    hangmangame.setWord(result1.getString("word"));
					hangmangame.setQuestion1(result1.getString("question1"));
					hangmangame.setQuestion2(result1.getString("question2"));
					hangmangame.setPrize(result1.getString("prize"));
			
				
					minigame=hangmangame;
					}
					
				}else if(QUIZ.equalsIgnoreCase(type)) {
					
					 Quizgame quizgame= new Quizgame();
						
						String query1 ="SELECT * FROM `quizgame` WHERE `idquiz`=? ;";
						 
						ResultSet result2 = null;
						PreparedStatement pstate2 = null;
						
						pstate2 = connection.prepareStatement(query1);
						pstate2.setInt(1, Id_minigame);
						
						result2 = pstate2.executeQuery();
						if(result2.next()) {
						quizgame.setIdQuiz(result2.getInt("idquiz"));
						quizgame.setType(type);
						quizgame.setIdsubject(subject);
					    quizgame.setQuestion(result2.getString("question"));
						quizgame.setRightAnswer(result2.getString("rightanswer"));
						quizgame.setWrong1(result2.getString("wrong1"));
						quizgame.setWrong2(result2.getString("wrong2"));
						quizgame.setPrize(result2.getString("prize"));
						
					
						
						
						minigame = quizgame;
					
						}
					
					
					
				}else if (AFFINITY.equalsIgnoreCase(type)) {
					 Affinitygame affinitygame= new Affinitygame();
						
						String query1 ="SELECT * FROM `affinitygame` WHERE `idaffinityGame`=? ;";
						 
						ResultSet result3 = null;
						PreparedStatement pstate3 = null;
						
						pstate3 = connection.prepareStatement(query1);
						pstate3.setInt(1, Id_minigame);
						
						result3 = pstate3.executeQuery();
						if(result3.next()) {
						affinitygame.setIdAffgame(result3.getInt("idaffinityGame"));
						affinitygame.setType(type);
						affinitygame.setIdsubject(subject);
						affinitygame.setWord1(result3.getString("word1"));
						affinitygame.setWord2(result3.getString("word2"));
						affinitygame.setWord3(result3.getString("word3"));
						affinitygame.setWord4(result3.getString("word4"));
						affinitygame.setRightAnswer(result3.getString("rightanswer"));
						affinitygame.setHint(result3.getString("hint"));
						affinitygame.setPrize(result3.getString("prize"));
			
						
						minigame = affinitygame;
					
					
					
						}
					
				} else {
					//ERRORE
				}



			}	
			
		} catch (SQLException e) {
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
		return minigame;
		
	}
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	//CERCO TUTTE LE TUPLE CON UNO SPECIFICO TIPO DI GAME DA MINIGAME 
	/*
	public static List<MiniGame> findMinigameByType(int type, Connection connection) throws SQLException {
		List<MiniGame> minigames = new ArrayList<MiniGame>();
		String query = "SELECT * FROM `minigame` WHERE `type` = ?";
		ResultSet result = null;
		PreparedStatement pstatement = null;
		try {
			pstatement = connection.prepareStatement(query);
			pstatement.setInt(1, type);
			result = pstatement.executeQuery();
			while (result.next()) {
				String strType = result.getString("type");
				Integer idGame = result.getInt("idminigame");
				MiniGame item = getMiniGame(strType, idGame, connection);
				if (item != null) {
					minigames.add(item);
				}
			}
		} catch (SQLException e) {
			throw new SQLException(e);

		} finally {
			try {
				result.close();
			} catch (Exception e1) {
				throw new SQLException(e1);
			}
			try {
				pstatement.close();
			} catch (Exception e2) {
				throw new SQLException(e2);
			}
		}

		return minigames;
	}
	

	
	//CERCO TUTTE LE TUPLE CON UNO SPECIFICO IDSUBJECT DA MINIGAME 
	
		public static List<MiniGame> findMinigameByIdsubject(int idsubject, Connection connection) throws SQLException {
			List<MiniGame> minigames = new ArrayList<MiniGame>();
			String query = "SELECT * FROM `minigame` WHERE `idsubject` = ?";
			ResultSet result = null;
			PreparedStatement pstatement = null;
			try {
				pstatement = connection.prepareStatement(query);
				pstatement.setInt(1, idsubject);
				result = pstatement.executeQuery();
				while (result.next()) {
					String strType = result.getString("type");
					Integer idGame = result.getInt("idminigame");
					MiniGame item = getMiniGame(strType, idGame, connection);
					if (item != null) {
						minigames.add(item);
					}
				}
			} catch (SQLException e) {
				throw new SQLException(e);

			} finally {
				try {
					result.close();
				} catch (Exception e1) {
					throw new SQLException(e1);
				}
				try {
					pstatement.close();
				} catch (Exception e2) {
					throw new SQLException(e2);
				}
			}

			return minigames;
		}
	
		
		
		//CERCO TUTTE LE TUPLE CON UNO SPECIFICO IDSUBJECT e UNO SPECIFICO TYPE DA MINIGAME 
		
			public static List<MiniGame> findMinigameByIdsubjectandtype(int idsubject, String type, Connection connection) throws SQLException {
				List<MiniGame> minigames = new ArrayList<MiniGame>();
				String query = "SELECT * FROM `minigame` WHERE `idsubject` = ? AND `type` = ?";
				ResultSet result = null;
				PreparedStatement pstatement = null;
				try {
					pstatement = connection.prepareStatement(query);
					pstatement.setInt(1, idsubject);
					pstatement.setString(2, type);
					result = pstatement.executeQuery();
					while (result.next()) {
						String strType = result.getString("type");
						Integer idGame = result.getInt("idminigame");
						MiniGame item = getMiniGame(strType, idGame, connection);
						if (item != null) {
							minigames.add(item);
						}
					}
				} catch (SQLException e) {
					throw new SQLException(e);

				} finally {
					try {
						result.close();
					} catch (Exception e1) {
						throw new SQLException(e1);
					}
					try {
						pstatement.close();
					} catch (Exception e2) {
						throw new SQLException(e2);
					}
				}

				return minigames;
			}
		
	
	
			private	static MiniGame getMiniGame(String strType, Integer idGame, Connection connection) throws SQLException {
				MiniGame retval = null;
				if ("Q".equals(strType)) {
					retval = QuizGameDAO.selectById(idGame, connection);
				} else if ("H".equals(strType)) {
					retval = HangmanGameDAO.selectById(idGame,connection);
				} else if ("A".equals(strType)) {
					retval = AffinityGameDAO.SelectById(idGame, connection);
				} else {
					log.log(Level.SEVERE, "dfdfdfd");
				}
				return retval;
			}
	
	
	
	
}































*/








/*package DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import Bean.MiniGame;



public class MiniGameDAO {
	private static final Logger log = Logger.getLogger(MiniGameDAO.class.getName());
	
	//CERCO TUTTE LE TUPLE CON UNO SPECIFICO TIPO DI GAME DA MINIGAME 
	
	public static List<MiniGame> findMinigameByType(int type, Connection connection) throws SQLException {
		List<MiniGame> minigames = new ArrayList<MiniGame>();
		String query = "SELECT * FROM `minigame` WHERE `type` = ?";
		ResultSet result = null;
		PreparedStatement pstatement = null;
		try {
			pstatement = connection.prepareStatement(query);
			pstatement.setInt(1, type);
			result = pstatement.executeQuery();
			while (result.next()) {
				String strType = result.getString("type");
				Integer idGame = result.getInt("idminigame");
				MiniGame item = getMiniGame(strType, idGame, connection);
				if (item != null) {
					minigames.add(item);
				}
			}
		} catch (SQLException e) {
			throw new SQLException(e);

		} finally {
			try {
				result.close();
			} catch (Exception e1) {
				throw new SQLException(e1);
			}
			try {
				pstatement.close();
			} catch (Exception e2) {
				throw new SQLException(e2);
			}
		}

		return minigames;
	}
	
	
	
	//CERCO TUTTE LE TUPLE CON UNO SPECIFICO IDSUBJECT DA MINIGAME 
	
		public static List<MiniGame> findMinigameByIdsubject(int idsubject, Connection connection) throws SQLException {
			List<MiniGame> minigames = new ArrayList<MiniGame>();
			String query = "SELECT * FROM `minigame` WHERE `idsubject` = ?";
			ResultSet result = null;
			PreparedStatement pstatement = null;
			try {
				pstatement = connection.prepareStatement(query);
				pstatement.setInt(1, idsubject);
				result = pstatement.executeQuery();
				while (result.next()) {
					String strType = result.getString("type");
					Integer idGame = result.getInt("idminigame");
					MiniGame item = getMiniGame(strType, idGame, connection);
					if (item != null) {
						minigames.add(item);
					}
				}
			} catch (SQLException e) {
				throw new SQLException(e);

			} finally {
				try {
					result.close();
				} catch (Exception e1) {
					throw new SQLException(e1);
				}
				try {
					pstatement.close();
				} catch (Exception e2) {
					throw new SQLException(e2);
				}
			}

			return minigames;
		}
	
		
		
		//CERCO TUTTE LE TUPLE CON UNO SPECIFICO IDSUBJECT e UNO SPECIFICO TYPE DA MINIGAME 
		
			public static List<MiniGame> findMinigameByIdsubjectandtype(int idsubject, String type, Connection connection) throws SQLException {
				List<MiniGame> minigames = new ArrayList<MiniGame>();
				String query = "SELECT * FROM `minigame` WHERE `idsubject` = ? AND `type` = ?";
				ResultSet result = null;
				PreparedStatement pstatement = null;
				try {
					pstatement = connection.prepareStatement(query);
					pstatement.setInt(1, idsubject);
					pstatement.setString(2, type);
					result = pstatement.executeQuery();
					while (result.next()) {
						String strType = result.getString("type");
						Integer idGame = result.getInt("idminigame");
						MiniGame item = getMiniGame(strType, idGame, connection);
						if (item != null) {
							minigames.add(item);
						}
					}
				} catch (SQLException e) {
					throw new SQLException(e);

				} finally {
					try {
						result.close();
					} catch (Exception e1) {
						throw new SQLException(e1);
					}
					try {
						pstatement.close();
					} catch (Exception e2) {
						throw new SQLException(e2);
					}
				}

				return minigames;
			}
		
	
	
			private	static MiniGame getMiniGame(String strType, Integer idGame, Connection connection) throws SQLException {
				MiniGame retval = null;
				if ("Q".equals(strType)) {
					retval = QuizGameDAO.selectById(idGame, connection);
				} else if ("H".equals(strType)) {
					retval = HangmanGameDAO.selectById(idGame,connection);
				} else if ("A".equals(strType)) {
					retval = AffinityGameDAO.SelectById(idGame, connection);
				} else {
					log.log(Level.SEVERE, "dfdfdfd");
				}
				return retval;
			}
	
	
	
	
}
*/