import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class FB_Application {
	
	
	private static FB_Application instance;
	
	private FB_Application(){
	}
	
	public static FB_Application getInstance(){
		if(FB_Application.instance == null){
			FB_Application.instance = new FB_Application();
		}
		return FB_Application.instance;
	}
	
	//bekommt von DB_Facade ArrayList mit allen Angeboten aus DB
	protected ArrayList<Angebot> angeboteAuflisten(){
		System.out.println("Angebotsliste ausgegeben.");
		return DB_Facade.getInstance().angeboteBrowsen();
	}
	
	//ruft bookOffer von DB_Facade auf
	protected void bookOffer(Date travelDate, String startingPlace, Time departureTime, 
			String destinationPlace, Time destinationTime,
			int seats, int luggage){
		DB_Facade.getInstance().bookOffer(travelDate, startingPlace, departureTime, 
				destinationPlace, destinationTime, seats, luggage);	
		//Hier noch Bestätigungsmail
	}
	
	//ruft angebotErstellen von DB_Facade auf
	protected void erstelleAngebot(Date travelDate, String startingPlace, Time departureTime, 
			String destinationPlace, Time destinationTime,
			int seats, int luggage, double price, String contactInformation){
		DB_Facade.getInstance().angebotErstellen(travelDate, startingPlace, departureTime, destinationPlace, 
				destinationTime, seats, luggage, price, contactInformation);
	}
}

