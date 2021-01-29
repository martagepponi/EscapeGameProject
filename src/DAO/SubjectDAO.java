package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Bean.Room;
import Bean.Subject;


public class SubjectDAO {
	private Connection connection;
	
	
	public SubjectDAO (Connection conn) {
		this.connection= conn;
		}
	
	
	
	
	//METODO PER REPERIRE objects E MURI DI SUBJECT DATO UN ID STANZA 
	
	public Subject findSubjectByIdRoom (int idroom){
		
		String query = "SELECT S.idsubject, S.wall1, S.wall2, S.wall3, S.wall4, S.name, S.object1, S.object2, S.object3, S.object4, S.year FROM escapegame.subject AS S JOIN escapegame.room AS R ON S.idsubject= R.idsubject WHERE R.idroom= ? LIMIT 1;";
		ResultSet result = null;
		PreparedStatement pstatement = null;
		Subject subject= null;
		try {
			pstatement = connection.prepareStatement(query);
			pstatement.setInt(1, idroom);
			result = pstatement.executeQuery();
			while (result.next()) {
				int idsubject = result.getInt("idsubject");
				String name= result.getString("name");
				int year= result.getInt("year");
				String object1= result.getString("object1");
				String object2= result.getString("object2");
				String object3= result.getString("object3");
				String object4= result.getString("object4");
				String wall1= result.getString("wall1");
				String wall2= result.getString("wall2");
				String wall3= result.getString("wall3");
				String wall4= result.getString("wall4");
				
	        	subject = new Subject(idsubject, name, year, object1, object2, object3, object4, wall1,wall2,wall3,wall4);
				
				
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
		return subject;
	}
	
	//select all subject
	 public List<Subject> findAllSubject() {
			
			
			
			String query = "SELECT * FROM `escapegame`.`subject` ;";
				List<Subject> allSubjects = new ArrayList<Subject>();
				ResultSet result = null;
				PreparedStatement pstatement = null;
				try {
					pstatement = connection.prepareStatement(query);
					result = pstatement.executeQuery();
					while (result.next()) {
					
						
						int idSubject = result.getInt("idsubject"); 
						//System.out.println("idRoom  "+ idRoom);
						String name = result.getString("name");
						int year = result.getInt("year");
						
						Subject subject = new Subject(idSubject,name, year);
						allSubjects.add(subject);
						System.out.println("subject DAO:  "+ allSubjects);
						
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
				return allSubjects;
			}
	 
	 
	//insert new subject
}

		
		
		


