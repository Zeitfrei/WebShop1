import java.sql.Date;
import java.sql.Time;

public class Angebot {
	
	private final Date travelDate;
	private final String startingPlace, destinationPlace, contactInformation;
	private final Time departureTime, destinationTime;
	private int seats, luggage;
	private final double price;
	
	public Angebot(){
		this.travelDate = null;
		this.startingPlace = null;
		this.departureTime = null;
		this.destinationPlace = null;
		this.destinationTime = null;
		this.seats = 0;
		this.luggage = 0;
		this.price = 0;
		this.contactInformation = null;
	}
	public Angebot(Date travelDate, String startingPlace, Time departureTime, 
			String destinationPlace, Time destinationTime,
			int seats, int luggage, double price, String contactInformation){
		this.travelDate = travelDate;
		this.startingPlace = startingPlace;
		this.departureTime = departureTime;
		this.destinationPlace = destinationPlace;
		this.destinationTime = destinationTime;
		this.seats = seats;
		this.luggage = luggage;
		this.price = price;
		this.contactInformation = contactInformation;
	}
	
	public Angebot(Date travelDate, String startingPlace, Time departureTime, 
			String destinationPlace, Time destinationTime,
			int seats, int luggage){
		this.travelDate = travelDate;
		this.startingPlace = startingPlace;
		this.departureTime = departureTime;
		this.destinationPlace = destinationPlace;
		this.destinationTime = destinationTime;
		this.seats = seats;
		this.luggage = luggage;
		this.price = 0;
		this.contactInformation = null;
	}

	protected boolean angebotAktualisieren(int seats){
		if(this.seats-seats>0){
			return true;
		}
		return false;
	}
	
	protected Angebot angebotErstellen(Date travelDate, String startingPlace, Time departureTime, 
			String destinationPlace, Time destinationTime,
			int seats, int luggage, double price, String contactInformation){
		return new Angebot(travelDate, startingPlace, departureTime, destinationPlace, 
				destinationTime, seats, luggage, price, contactInformation);
	}
	protected Angebot angebotErstellen(Date travelDate, String startingPlace, Time departureTime, 
			String destinationPlace, Time destinationTime,
			int seats, int luggage){
		return new Angebot(travelDate, startingPlace, departureTime, destinationPlace, 
				destinationTime, seats, luggage);
	}
	
	public String getTravelDate(){
		return this.travelDate.toString();
	}
	public String getStartingPlace(){
		return this.startingPlace;
	}
	public String getDepartureTime(){
		return this.departureTime.toString();
	}
	public String getDestinationPlace(){
		return this.destinationPlace;
	}
	public String getdestinationTime(){
		return this.destinationTime.toString();
	}
	public String getSeats(){
		return String.valueOf(this.seats);
	}
	public String getluggage(){
		return String.valueOf(this.luggage);
	}
	public String getPrice(){
		return String.valueOf(this.price);
	}
	public String getContactInformation(){
		return this.contactInformation;
	}
}
