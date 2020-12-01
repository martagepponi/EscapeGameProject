package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import Bean.Quizgame;




public class QuizGameDAO {
	
	//CERCO LA TUPLA DI QUIZGAME CON UNO SPECIFICO IDQUIZGAME
	
	public static Quizgame selectById(int idQuiz, Connection connection) throws SQLException {
		Quizgame t = new Quizgame();
		String query = "SELECT * FROM `quizgame` where `idquiz` = ?";
		ResultSet result = null;
		PreparedStatement pstatement = null;
		try {
			pstatement = connection.prepareStatement(query);
			pstatement.setInt(1, idQuiz);
			result = pstatement.executeQuery();
			while (result.next()) {
				
				t.setIdQuiz(result.getInt("idquiz"));
				t.setIdsubject(result.getInt("idsubject"));
				t.setQuestion(result.getString("question"));
				t.setRightAnswer(result.getString("rightanswer"));
				t.setWrong1(result.getString("wrong1"));
				t.setWrong2(result.getString("wrong2"));
				t.setPrize(result.getString("prize"));
				t.setPass(result.getString("pass"));
			}
		} catch (SQLException e) {
			throw new SQLException(e);

		} finally {
			try {
				result.close();
			} catch (Exception e1) {
				throw new SQLException("Cannot close result");
			}
			try {
				pstatement.close();
			} catch (Exception e1) {
				throw new SQLException("Cannot close statement");
			}
		}
		return t;
	}
	
	
	
	
	

}
	
	
	

