package DAO;


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
