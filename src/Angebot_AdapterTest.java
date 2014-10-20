import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


public class Angebot_AdapterTest {
	
	public Date travelDate = Date.valueOf("2013-02-21");
	public String startingPlace = "London";
	public Time departureTime = Time.valueOf("09:35:00");
	public String destinationPlace = "Auckland";
	public Time destinationTime = Time.valueOf("09:36:00");
	public int seats=1;
	public int luggage=0;
	
	@Test
	public void testAngeboteBrowsen(){
		//Angebot realisiert durch Connection, Statement und ResultSet
		Connection stubCon = mock(Connection.class);
		Statement stubStmt = mock(Statement.class);
		ResultSet stubRes = mock(ResultSet.class);
		
		//Klasse die die Komponente implementiert instantiieren und mit stubs verbinden
		DB_Facade.getInstance().setConnection(stubCon);
		
		try{
			//Set return values for database connection
			when(stubCon.createStatement()).thenReturn(stubStmt);
			//set return values for queryA
			String queryA = "SELECT * FROM Angebot WHERE seats > 0";
			when(stubStmt.executeQuery(queryA)).thenReturn(stubRes);
			when(stubRes.next()).thenReturn(true).thenReturn(false);          
			when(stubRes.getDate(1)).thenReturn(Date.valueOf("2013-02-21"));
			when(stubRes.getString(2)).thenReturn("London");
			when(stubRes.getTime(3)).thenReturn(Time.valueOf("09:35:00"));
			when(stubRes.getString(4)).thenReturn("Auckland");
			when(stubRes.getTime(5)).thenReturn(Time.valueOf("09:36:00"));
			when(stubRes.getInt(6)).thenReturn(2);
			when(stubRes.getInt(7)).thenReturn(0);
			when(stubRes.getDouble(8)).thenReturn(5.20);
			when(stubRes.getString(9)).thenReturn("Adresse");
			
			//Methode mit entsprechenden parametern aufrufen
			ArrayList<Angebot> an = DB_Facade.getInstance().angeboteBrowsen();
			
			//verify calls  stub erwartet nachricht 1x
			verify(stubCon, times(1)).createStatement();
			verify(stubStmt, times(1)).executeQuery(queryA);
			
			//verify return values
			assertTrue(an.size() == 1);
			Angebot a = an.get(0);
			assertTrue(Integer.valueOf(a.getSeats()) > 0);				
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	@Test
	//No such Angebot exists
	public void testAngebotErstellen(){
		Connection stubCon = mock(Connection.class);
		Statement stubStmt = mock(Statement.class);
		ResultSet stubRes = mock(ResultSet.class);
		
		DB_Facade.getInstance().setConnection(stubCon);
		
		try{
			when(stubCon.createStatement()).thenReturn(stubStmt);
			String queryC = "SELECT COUNT(*) FROM Angebot WHERE travelDate='" + Date.valueOf("2013-02-21") 
					+ "' AND startingPlace='"+"London"+"' AND departureTime='"+Time.valueOf("09:35:00")+
					"' AND destinationPlace='"+"Auckland"+"' AND destinationTime='"+Time.valueOf("09:36:00")+"'";
			
			when(stubStmt.executeQuery(queryC)).thenReturn(stubRes);
			when(stubRes.next()).thenReturn(true).thenReturn(false);         
			when(stubRes.getInt(1)).thenReturn(0); //count=0
			
			String insertA = "INSERT INTO Angebot VALUES ('"+Date.valueOf("2013-02-21")+"', '"+"London"+"', '"+Time.valueOf("09:35:00")+"', '"
						+"Auckland"+"', '"+Time.valueOf("09:36:00")+"', "+2+", "+0+", "+5.20
						+", '"+"Adresse"+"')";
			
			FB_Application.getInstance().erstelleAngebot(
					Date.valueOf("2013-02-21"), "London", Time.valueOf("09:35:00"), "Auckland", 
					Time.valueOf("09:36:00"), 2, 0,5.20,"Adresse");	
		
			//executeUpdate gets calles 1 time, to create Angebot
			verify(stubCon, times(1)).createStatement();
			verify(stubStmt, times(1)).executeQuery(queryC);
			verify(stubStmt, times(1)).executeUpdate(insertA);   
			
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	@Test
	//Angebot exists
	public void testAngebotErstellenExists(){		
		Connection stubCon = mock(Connection.class);
		Statement stubStmt = mock(Statement.class);
		ResultSet stubRes = mock(ResultSet.class);
		
		DB_Facade.getInstance().setConnection(stubCon);
		
		try{
			when(stubCon.createStatement()).thenReturn(stubStmt);
			
			String queryC = "SELECT COUNT(*) FROM Angebot WHERE travelDate='" + Date.valueOf("2013-02-21") 
					+ "' AND startingPlace='"+"London"+"' AND departureTime='"+Time.valueOf("09:35:00")+
					"' AND destinationPlace='"+"Auckland"+"' AND destinationTime='"+Time.valueOf("09:36:00")+"'";
			
			
			when(stubStmt.executeQuery(queryC)).thenReturn(stubRes);
			when(stubRes.next()).thenReturn(false).thenReturn(false);         
			when(stubRes.getInt(1)).thenReturn(1); //count=1, Angebot exists
			
			String insertA = "INSERT INTO Angebot VALUES ('"+Date.valueOf("2013-02-21")+"', '"+"London"+"', '"+Time.valueOf("09:35:00")+"', '"
						+"Auckland"+"', '"+Time.valueOf("09:36:00")+"', "+2+", "+0+", "+5.20
						+", '"+"Adresse"+"')";
			
			FB_Application.getInstance().erstelleAngebot(
					Date.valueOf("2013-02-21"), "London", Time.valueOf("09:35:00"), "Auckland", 
					Time.valueOf("09:36:00"), 2, 0,5.20,"Adresse");	
		
			//executeUpdate 0-times because Angebot exists
			verify(stubCon, times(1)).createStatement();
			verify(stubStmt, times(1)).executeQuery(queryC);
			verify(stubStmt, times(0)).executeUpdate(insertA);   
			
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	@Test
	//Angebot has to be updated after booking (an.seats-seats>0
	public void testAngebotBuchenInsert(){
		Connection stubCon = mock(Connection.class);
		Statement stubStmt = mock(Statement.class);
		ResultSet stubRes = mock(ResultSet.class);
		
		DB_Facade.getInstance().setConnection(stubCon);
		
		try{
			when(stubCon.createStatement()).thenReturn(stubStmt);
			
			String queryA = "SELECT * FROM Angebot WHERE travelDate='" + travelDate 
					+ "' AND startingPlace='"+startingPlace+"' AND departureTime='"+departureTime+
					"' AND destinationPlace='"+destinationPlace+"' AND destinationTime='"+destinationTime+
					"' AND (seats-"+seats+")>=0 AND (luggage-"+luggage+")>=0";	
			

			when(stubStmt.executeQuery(queryA)).thenReturn(stubRes);
			when(stubRes.next()).thenReturn(true).thenReturn(false);		
			when(stubRes.getDate(1)).thenReturn(Date.valueOf("2013-02-21"));
			when(stubRes.getString(2)).thenReturn("London");
			when(stubRes.getTime(3)).thenReturn(Time.valueOf("09:35:00"));
			when(stubRes.getString(4)).thenReturn("Auckland");
			when(stubRes.getTime(5)).thenReturn(Time.valueOf("09:36:00"));
			when(stubRes.getInt(6)).thenReturn(2);
			when(stubRes.getInt(7)).thenReturn(0);
			when(stubRes.getDouble(8)).thenReturn(5.20);
			when(stubRes.getString(9)).thenReturn("Adresse");
			
			String insertA = "UPDATE Angebot SET seats=("+"2"+"-"+seats+"), luggage=("+"0"
					+"-"+luggage+") " + "WHERE travelDate='" + travelDate 
					+ "' AND startingPlace='"+startingPlace+"' AND departureTime='"+departureTime+
					"' AND destinationPlace='"+destinationPlace+"' AND destinationTime='"+destinationTime+"'";
			
			Angebot ang = DB_Facade.getInstance().bookOffer(	
					Date.valueOf("2013-02-21"), "London", Time.valueOf("09:35:00"), "Auckland", 
					Time.valueOf("09:36:00"), 1, 0);
			
			//createStatement and queryA must always be executed, insertA if an.seats-seats>0
			verify(stubCon, times(1)).createStatement();
			verify(stubStmt, times(1)).executeQuery(queryA);
			verify(stubStmt, times(1)).executeUpdate(insertA);
			
			//verify return values
			assertTrue(ang.getTravelDate().equals("2013-02-21")&&ang.getStartingPlace().equals("London")&&
					ang.getDepartureTime().equals("09:35:00")&&ang.getDestinationPlace().equals("Auckland")&&
					ang.getdestinationTime().equals("09:36:00")&&
					ang.getSeats().equals("2")&&ang.getluggage().equals("0"));		
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	@Test
	//Angebot has to be deleted after booking (an.seats-seats=0
	public void testAngebotBuchenDelete(){
		Connection stubCon = mock(Connection.class);
		Statement stubStmt = mock(Statement.class);
		ResultSet stubRes = mock(ResultSet.class);
		
		DB_Facade.getInstance().setConnection(stubCon);
		
		try{
			when(stubCon.createStatement()).thenReturn(stubStmt);
			
			String queryA = "SELECT * FROM Angebot WHERE travelDate='" + travelDate 
					+ "' AND startingPlace='"+startingPlace+"' AND departureTime='"+departureTime+
					"' AND destinationPlace='"+destinationPlace+"' AND destinationTime='"+destinationTime+
					"' AND (seats-"+seats+")>=0 AND (luggage-"+luggage+")>=0";	
			
			when(stubStmt.executeQuery(queryA)).thenReturn(stubRes);
			when(stubRes.next()).thenReturn(true).thenReturn(false);		
			when(stubRes.getDate(1)).thenReturn(Date.valueOf("2013-02-21"));
			when(stubRes.getString(2)).thenReturn("London");
			when(stubRes.getTime(3)).thenReturn(Time.valueOf("09:35:00"));
			when(stubRes.getString(4)).thenReturn("Auckland");
			when(stubRes.getTime(5)).thenReturn(Time.valueOf("09:36:00"));
			when(stubRes.getInt(6)).thenReturn(1);	//set to 1 so there are no seats left after booking
			when(stubRes.getInt(7)).thenReturn(0);
			when(stubRes.getDouble(8)).thenReturn(5.20);
			when(stubRes.getString(9)).thenReturn("Adresse");
			
			String deleteA = "DELETE FROM Angebot WHERE travelDate='" + travelDate 
					+ "' AND startingPlace='"+startingPlace+"' AND departureTime='"+departureTime+
					"' AND destinationPlace='"+destinationPlace+"' AND destinationTime='"+destinationTime+"'";
			
			Angebot ang = DB_Facade.getInstance().bookOffer(	
					Date.valueOf("2013-02-21"), "London", Time.valueOf("09:35:00"), "Auckland", 
					Time.valueOf("09:36:00"), 1, 0);
			
			//createStatement and queryA must always be executed, deleteA if an.seats-seats=0
			verify(stubCon, times(1)).createStatement();
			verify(stubStmt, times(1)).executeQuery(queryA);
			verify(stubStmt, times(1)).executeUpdate(deleteA);
			
			//verify return values
			assertTrue(ang.getTravelDate().equals("2013-02-21")&&ang.getStartingPlace().equals("London")&&
					ang.getDepartureTime().equals("09:35:00")&&ang.getDestinationPlace().equals("Auckland")&&
					ang.getdestinationTime().equals("09:36:00")&&
					ang.getSeats().equals("1")&&ang.getluggage().equals("0"));		
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
}
