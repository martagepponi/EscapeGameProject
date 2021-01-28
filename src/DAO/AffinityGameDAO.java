package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import Bean.Affinitygame;


public class AffinityGameDAO {
	
	
private Connection connection;
	
	public AffinityGameDAO (Connection conn) {
	this.connection= conn;
	}
	
	
	
	//CERCO LA TUPLA DI AFFINITYGAME CON UNO SPECIFICO IDAFFINITYGAME
	
		public static Affinitygame SelectById(int idGame, Connection connection) throws SQLException {
			Affinitygame affinity = new Affinitygame();
			String query = "SELECT * FROM `affinitygame` where `idaffinityGame` = ?";
			ResultSet result = null;
			PreparedStatement pstatement = null;
			try {
				pstatement = connection.prepareStatement(query);
				pstatement.setInt(1, idGame);
				result = pstatement.executeQuery();
				while (result.next()) {
					
					affinity.setIdAffgame(result.getInt("idaffinityGame"));
					affinity.setIdsubject(result.getInt("idsubject"));
					affinity.setWord1(result.getString("word1"));
					affinity.setWord2(result.getString("word2"));
					affinity.setWord3(result.getString("word3"));
					affinity.setWord4(result.getString("word4"));
					affinity.setRightAnswer(result.getString("rightanswer"));
					affinity.setHint(result.getString("hint"));
					affinity.setPrize(result.getString("prize"));
					
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
			return affinity;
		}

}
