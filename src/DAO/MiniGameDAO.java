package DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
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
	public AbstractMinigame findById(int Id_minigame, int id_room) {
		
		String query = "select minigame.*, subject.prize1 as prize "
				+ "from escapegame.minigame "
				+ "inner join escapegame.subject on subject.idsubject = minigame.idsubject "
				+ "inner join escapegame.room on room.idroom = ? and room.minigame1 = ? "
				+ "where minigame.idminigame = ? "
				+ "union all "
				+ "select minigame.*, subject.prize2 as prize "
				+ "from escapegame.minigame "
				+ "inner join escapegame.subject on subject.idsubject = minigame.idsubject "
				+ "inner join escapegame.room on room.idroom = ? and room.minigame2 = ? "
				+ "where minigame.idminigame = ? "
				+ "union all "				
				+ "Select minigame.*, subject.prize3 as prize "
				+ "from escapegame.minigame "
				+ "inner join escapegame.subject on subject.idsubject = minigame.idsubject "
				+ "inner join escapegame.room on room.idroom = ? and room.minigame3 = ? "
				+ "where minigame.idminigame = ?";
		AbstractMinigame minigame = null;
		ResultSet result = null;
		PreparedStatement pstatement = null;
		

		try {
			pstatement = connection.prepareStatement(query);
			pstatement.setInt(1, id_room);
			pstatement.setInt(2, Id_minigame);
			pstatement.setInt(3, Id_minigame);
			pstatement.setInt(4, id_room);
			pstatement.setInt(5, Id_minigame);
			pstatement.setInt(6, Id_minigame);
			pstatement.setInt(7, id_room);
			pstatement.setInt(8, Id_minigame);
			pstatement.setInt(9, Id_minigame);
			
			result = pstatement.executeQuery();
			if(result.next()) {

			    //RIPRENDO RISULTATI DELLA QUERY
				String type=result.getString("type");
				int subject= result.getInt("idsubject");
				String prize= result.getString("prize");
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
					hangmangame.setPrize(prize);
			
				
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
						quizgame.setPrize(prize);
						
					
						
						
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
						affinitygame.setPrize(prize);
			
						
						minigame = affinitygame;
					
					
					
						}
					
				} else {
					
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
	
	
	
	
	//CERCO IL MINIGIOCO IN BASE ALL'ID E LO COSTRUISCO IN BASE AL TIPO
/*	public AbstractMinigame findById(int Id_minigame) {

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
	*/
	
	

	//DOCENTE

 /* public List<AbstractMinigame> findMinigamesByType(String Type) {

	String query = "SELECT * FROM `minigame` WHERE `type`=? ;";
	List<AbstractMinigame> minigamesByType = new ArrayList<AbstractMinigame>();
	ResultSet result = null;
	PreparedStatement pstatement = null;
	

	try {
		pstatement = connection.prepareStatement(query);
		pstatement.setString(1, Type);
		
		result = pstatement.executeQuery();
		
		while(result.next()) {

		    //RIPRENDO RISULTATI DELLA QUERY
			String type=result.getString("type");
			int subject= result.getInt("idsubject");
			int Id_minigame = result.getInt("idminigame");
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
		
			
				minigamesByType.add(hangmangame);
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
					
				
					
					
					minigamesByType.add(quizgame);
				
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
		
					
					minigamesByType.add(affinitygame);
				
				
				
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
	return minigamesByType;
	
}*/
	
  public List<AbstractMinigame> findMinigamesByTypesubject(String Type, int idSubject) {

	String query = "SELECT * FROM `minigame` WHERE `type`=? AND `idsubject`=? ;";
	List<AbstractMinigame> minigamesByType = new ArrayList<AbstractMinigame>();
	ResultSet result = null;
	PreparedStatement pstatement = null;
	

	try {
		pstatement = connection.prepareStatement(query);
		pstatement.setString(1, Type);
		pstatement.setInt(2, idSubject);
		result = pstatement.executeQuery();
		
		while(result.next()) {

		    //RIPRENDO RISULTATI DELLA QUERY
			String type=result.getString("type");
			int subject= result.getInt("idsubject");
			int Id_minigame = result.getInt("idminigame");
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
				//hangmangame.setPrize(result1.getString("prize"));
		
			
				minigamesByType.add(hangmangame);
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
					//quizgame.setPrize(result2.getString("prize"));
					
				
					
					
					minigamesByType.add(quizgame);
				
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
					//affinitygame.setPrize(result3.getString("prize"));
		
					
					minigamesByType.add(affinitygame);
				
				
				
					}
				
			} else {
				
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
	return minigamesByType;
	
}
 
	public void addMinigameH(String hangmanWord,String questionH, String hintH, int idsubject) {
		String query1 = "INSERT INTO `escapegame`.`minigame` (`type`, `idsubject`) VALUES (?, ?);";
		String query2 ="SELECT `idminigame` FROM `escapegame`.`minigame`;";
		String query3 ="INSERT INTO `escapegame`.`hangmangame` (`idhangman`, `word`, `question1`, `question2` ) VALUES (?, ?, ?, ?);";
		PreparedStatement pstatement1 = null;
		PreparedStatement pstatement2 = null;
		PreparedStatement pstatement3 = null;
		ResultSet rs = null;
		int id = 0;
		ArrayList<Integer> appL = new ArrayList<Integer>();
		try {
			pstatement1 = connection.prepareStatement(query1);
			pstatement1.setString(1, "hangmangame");
			pstatement1.setInt(2, idsubject); 			
			pstatement1.executeUpdate(); 
			
			pstatement2 = connection.prepareStatement(query2);
			rs = pstatement2.executeQuery();
			while (rs.next()) {
				
				id = rs.getInt("idminigame");
				
				appL.add(id);
			}
			Integer i= Collections.max(appL);
			
			pstatement3 = connection.prepareStatement(query3);
			pstatement3.setInt(1, i);
			pstatement3.setString(2, hangmanWord);
			pstatement3.setString(3, questionH);
			pstatement3.setString(4, hintH);
			pstatement3.executeUpdate(); 
			
			}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try { 
				pstatement1.close();
				pstatement2.close();
				pstatement3.close();
			} 
			catch (Exception e3){ 
				e3.printStackTrace();
			} 
			try {
				rs.close();
			} 
			catch (Exception e2) {
				e2.printStackTrace();
			}
	}
}
	
	
	public void addMinigameA(String affinityWord, String word1A, String word2A, String word3A, String word4A, String hintA, int idsubject) {
		String query1 = "INSERT INTO `escapegame`.`minigame` (`type`, `idsubject`) VALUES (?, ?);";
		String query2 ="SELECT `idminigame` FROM `escapegame`.`minigame`;";
		String query3 ="INSERT INTO `escapegame`.`affinitygame` (`idaffinityGame`, `word1`, `word2`, `word3`, `word4`, `rightanswer`, `hint` ) VALUES (?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement pstatement1 = null;
		PreparedStatement pstatement2 = null;
		PreparedStatement pstatement3 = null;
		ResultSet rs = null;
		int id = 0;
		ArrayList<Integer> appL = new ArrayList<Integer>();
		try {
			pstatement1 = connection.prepareStatement(query1);
			pstatement1.setString(1, "affinitygame");
			pstatement1.setInt(2, idsubject); 			
			pstatement1.executeUpdate(); 
			
			pstatement2 = connection.prepareStatement(query2);
			rs = pstatement2.executeQuery();
			while (rs.next()) {
				
				id = rs.getInt("idminigame");
				
				appL.add(id);
			}
			Integer i= Collections.max(appL);
			
			pstatement3 = connection.prepareStatement(query3);
			pstatement3.setInt(1, i);
			pstatement3.setString(2, word1A);
			pstatement3.setString(3, word2A);
			pstatement3.setString(4, word3A);
			pstatement3.setString(5, word4A);
			pstatement3.setString(6, affinityWord);
			pstatement3.setString(7, hintA);
			
			pstatement3.executeUpdate(); 
			
			}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try { 
				pstatement1.close();
				pstatement2.close();
				pstatement3.close();
			} 
			catch (Exception e3){ 
				e3.printStackTrace();
			} 
			try {
				rs.close();
			} 
			catch (Exception e2) {
				e2.printStackTrace();
			}
	}
}
	
	public void addMinigameQ(String questionQ, String quizWord, String wrong1Q, String wrong2Q, int idsubject) {
		String query1 = "INSERT INTO `escapegame`.`minigame` (`type`, `idsubject`) VALUES (?, ?);";
		String query2 ="SELECT `idminigame` FROM `escapegame`.`minigame`;";
		String query3 ="INSERT INTO `escapegame`.`quizgame` (`idquiz`, `question`, `rightanswer`, `wrong1`, `wrong2`) VALUES (?, ?, ?, ?, ?);";
		PreparedStatement pstatement1 = null;
		PreparedStatement pstatement2 = null;
		PreparedStatement pstatement3 = null;
		ResultSet rs = null;
		int id = 0;
		ArrayList<Integer> appL = new ArrayList<Integer>();
		try {
			pstatement1 = connection.prepareStatement(query1);
			pstatement1.setString(1, "quizgame");
			pstatement1.setInt(2, idsubject); 			
			pstatement1.executeUpdate(); 
			
			pstatement2 = connection.prepareStatement(query2);
			rs = pstatement2.executeQuery();
			while (rs.next()) {
				
				id = rs.getInt("idminigame");
				
				appL.add(id);
			}
			Integer i= Collections.max(appL);
			
			pstatement3 = connection.prepareStatement(query3);
			pstatement3.setInt(1, i);
			pstatement3.setString(2, questionQ);
			pstatement3.setString(3, quizWord);
			pstatement3.setString(4, wrong1Q);
			pstatement3.setString(5, wrong2Q);
			
			
			pstatement3.executeUpdate(); 
			
			}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try { 
				pstatement1.close();
				pstatement2.close();
				pstatement3.close();
			} 
			catch (Exception e3){ 
				e3.printStackTrace();
			} 
			try {
				rs.close();
			} 
			catch (Exception e2) {
				e2.printStackTrace();
			}
	}
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