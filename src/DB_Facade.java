import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;


public class DB_Facade {
	
	private static DB_Facade instance;
	private ResultSet rs;
	private Statement stmt;
	private Connection con;
	private String connectionURL = "jdbc:mysql://localhost:3306/mydb";
	private String user = "root";
	private String password = "pw";
	private Angebot angebot;
	
	private DB_Facade(){
		angebot = new Angebot();
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(ClassNotFoundException e){
			System.err.println("Treiberklasse nicht gefunden");
		}
		con = null;
		//setConnection();
	}
	
	public static DB_Facade getInstance(){
		if(DB_Facade.instance == null){
			DB_Facade.instance = new DB_Facade();
		}
		return DB_Facade.instance;
	}
	public static void setInstance(DB_Facade fake){
		instance = fake;
	}
	public void setConnection(Connection con){
		try{
			if(con == null || con.isClosed()){
				this.con = DriverManager.getConnection(connectionURL,user,password);
			}else{
				this.con = con;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	protected Angebot bookOffer(Date travelDate, String startingPlace, Time departureTime, 
			String destinationPlace, Time destinationTime,
			int seats, int luggage){
		//Connection con = null;

		try {
			//con = DriverManager.getConnection(connectionURL,user,password);
			setConnection(con);
			Statement stmt = con.createStatement();
			//queryA nach Design
			String queryA = "SELECT * FROM Angebot WHERE travelDate='" + travelDate 
					+ "' AND startingPlace='"+startingPlace+"' AND departureTime='"+departureTime+
					"' AND destinationPlace='"+destinationPlace+"' AND destinationTime='"+destinationTime+
					"' AND (seats-"+seats+")>=0 AND (luggage-"+luggage+")>=0";		
			ResultSet rs = stmt.executeQuery(queryA);
			//(nur 1-mal)
			while(rs.next()){
				Angebot ang = angebot.angebotErstellen(rs.getDate(1),rs.getString(2),rs.getTime(3),rs.getString(4),
						rs.getTime(5), rs.getInt(6),rs.getInt(7),rs.getDouble(8),rs.getString(9));
				Angebot an = angebot.angebotErstellen(rs.getDate(1),rs.getString(2),rs.getTime(3),rs.getString(4),
						rs.getTime(5), rs.getInt(6),rs.getInt(7));
				
				//alt nach Design
				//angebotAktualisieren(seats)
				if(an.angebotAktualisieren(seats)){
					//an.seats-seats>0
					String insertA = "UPDATE Angebot SET seats=("+an.getSeats()+"-"+seats+"), luggage=("+an.getluggage()
						+"-"+luggage+") " +
						"WHERE travelDate='" + travelDate 
						+ "' AND startingPlace='"+startingPlace+"' AND departureTime='"+departureTime+
						"' AND destinationPlace='"+destinationPlace+"' AND destinationTime='"+destinationTime+"'";
					stmt.executeUpdate(insertA);
					close();
					System.out.println("Angebot gebucht.");  
					return ang;
				}else{
					//an.seats-seats=0
					String deleteA = "DELETE FROM Angebot WHERE travelDate='" + travelDate 
						+ "' AND startingPlace='"+startingPlace+"' AND departureTime='"+departureTime+
						"' AND destinationPlace='"+destinationPlace+"' AND destinationTime='"+destinationTime+"'";
					stmt.executeUpdate(deleteA);
					close();
					System.out.println("Angebot gebucht.");  
					return ang;
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			close();
		}
		System.out.println("Angebot konnte nicht gebucht werden.");
		return angebot.angebotErstellen(null, null, null, null, null, 0, 0, 0, null);
	}
	
	protected ArrayList<Angebot> angeboteBrowsen(){
		//Connection con = null;
		ArrayList<Angebot> an = new ArrayList<Angebot>();
		try {
			//con = DriverManager.getConnection(connectionURL,user,password);
			setConnection(con);
			Statement stmt = con.createStatement();
			//queryA nach Design
			String queryA = "SELECT * FROM Angebot WHERE seats > 0";
			ResultSet rs = stmt.executeQuery(queryA);
			//Loop nach Design
			while(rs.next()){
				an.add(angebot.angebotErstellen(rs.getDate(1),rs.getString(2),rs.getTime(3),rs.getString(4),
						rs.getTime(5),rs.getInt(6),rs.getInt(7),rs.getDouble(8),rs.getString(9)));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			close();
		}
		return an;
	}
	
	protected void angebotErstellen(Date travelDate, String startingPlace, Time departureTime, 
			String destinationPlace, Time destinationTime,
			int seats, int luggage, double price, String contactInformation){
		//Connection con = null;
		try {
			//con = DriverManager.getConnection(connectionURL,user,password);
			setConnection(con);
			Statement stmt = con.createStatement();
			//queryC nach Design
			String queryC = "SELECT COUNT(*) FROM Angebot WHERE travelDate='" + travelDate 
					+ "' AND startingPlace='"+startingPlace+"' AND departureTime='"+departureTime+
					"' AND destinationPlace='"+destinationPlace+"' AND destinationTime='"+destinationTime+"'";
			ResultSet rs = stmt.executeQuery(queryC);
			rs.next();
			//alt nach Design
			if(rs.getInt(1)==0){
				String insertA = "INSERT INTO Angebot VALUES ('"+travelDate+"', '"+startingPlace+"', '"+departureTime+"', '"
						+destinationPlace+"', '"+destinationTime+"', "+seats+", "+luggage+", "+price
						+", '"+contactInformation+"')";
				stmt.executeUpdate(insertA);
				System.out.println("Angebot erstellt");
			}else{
				System.out.println("Angebot existiert bereits.");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			close();
		}
	}
	public void clear(){
		try{
			setConnection(con);
			Statement stmt = con.createStatement();
			String clear = "TRUNCATE TABLE Angebot";
			stmt.executeUpdate(clear);
			System.out.println("test");
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close();
		}
	}

	private void close(){
		try{
			if (rs != null) {
		        rs.close();
		      }
		    if (stmt != null) {
		        stmt.close();
		    }
		    if (con != null) {
		    	con.close();
		    }
	    }catch (SQLException e) {
	    	e.printStackTrace();
	    }
	}
}
