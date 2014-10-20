import net.sourceforge.jwebunit.junit.WebTestCase;
import org.junit.*;

public class UserGUIWebTestCase extends WebTestCase{
	@Before
	public void setUp(){
		setBaseUrl("http://localhost:8080/Fahrtenboerse");
		DB_Facade.getInstance().clear();
	}
	@Test
	public void testAngeboteBrowsen(){
		beginAt("/UserGUI");
		//verify content of default webpage
		assertTitleEquals("Fahrtenbörse - User");
		assertTextPresent("Fahrtenbörse - User");
		assertTextPresent("Angebot erstellen:");
		assertFormPresent();
		assertTextPresent("Travel Date: (YYYY-MM-DD)");
		assertFormElementPresent("travelDate");
		assertTextPresent("Starting Place:");
		assertFormElementPresent("startingPlace");
		assertTextPresent("Departure Time: (HH:MM:SS)");
		assertFormElementPresent("departureTime");
		assertTextPresent("Destination Place:");
		assertFormElementPresent("destinationPlace");
		assertTextPresent("Destination Time:");
		assertFormElementPresent("destinationTime");
		assertTextPresent("Seats:");
		assertFormElementPresent("seats");
		assertTextPresent("Luggage:");
		assertFormElementPresent("luggage");
		assertTextPresent("Price:");
		assertFormElementPresent("price");
		assertTextPresent("Contact Information:");
		assertFormElementPresent("contactInformation");
		assertSubmitButtonPresent("SelectAngWebpage");
		assertSubmitButtonPresent("angebotErstellen");
		
		submit("SelectAngWebpage");
		
		//verify content of target webpage selectangwebpage
		assertTitleEquals("Fahrtenbörse - User");
		assertTextPresent("Fahrtenbörse - Angebote Browsen");
		assertSubmitButtonPresent("ShowDefaultWebpage");
		assertTablePresent("");
		String[][] tableHeadings = {{"Travel Date","Starting Place","Departure Time","Destination Place",
			"Destination Time","Seats","Luggage","Price"}};
		assertTableEquals("",tableHeadings);
	}
	@Test
	public void testAngebotErstellen(){
		beginAt("/UserGUI");
		//verify content of default webpage
		assertTitleEquals("Fahrtenbörse - User");
		assertTextPresent("Fahrtenbörse - User");
		assertTextPresent("Angebot erstellen:");
		assertFormPresent();
		assertTextPresent("Travel Date: (YYYY-MM-DD)");
		assertFormElementPresent("travelDate");
		assertTextPresent("Starting Place:");
		assertFormElementPresent("startingPlace");
		assertTextPresent("Departure Time: (HH:MM:SS)");
		assertFormElementPresent("departureTime");
		assertTextPresent("Destination Place:");
		assertFormElementPresent("destinationPlace");
		assertTextPresent("Destination Time:");
		assertFormElementPresent("destinationTime");
		assertTextPresent("Seats:");
		assertFormElementPresent("seats");
		assertTextPresent("Luggage:");
		assertFormElementPresent("luggage");
		assertTextPresent("Price:");
		assertFormElementPresent("price");
		assertTextPresent("Contact Information:");
		assertFormElementPresent("contactInformation");
		assertSubmitButtonPresent("SelectAngWebpage");
		assertSubmitButtonPresent("angebotErstellen");
		
		//fill out the form - refer to comments in UserGUI
		setTextField("travelDate","2013-02-21");
		setTextField("startingPlace","London");
		setTextField("departureTime","09:35:00");
		setTextField("destinationPlace","Auckland");
		setTextField("destinationTime","09:36:00");
		setTextField("seats","2");
		setTextField("luggage","0");
		setTextField("price","5.20");
		setTextField("contactInformation","Adresse");
		
		submit("angebotErstellen");
	}
	@Test
	public void testAngebotBuchen(){
		beginAt("/UserGUI?buchenForm=Book+Offer");
		//verify content of default webpage
		assertTitleEquals("Fahrtenbörse - User");
		assertTextPresent("Fahrtenbörse - Angebot Buchen");
		assertFormPresent();
		assertTextPresent("Travel Date: (YYYY-MM-DD)");
		assertFormElementPresent("travelDate");
		assertTextPresent("Starting Place:");
		assertFormElementPresent("startingPlace");
		assertTextPresent("Departure Time: (HH:MM:SS)");
		assertFormElementPresent("departureTime");
		assertTextPresent("Destination Place:");
		assertFormElementPresent("destinationPlace");
		assertTextPresent("Destination Time:");
		assertFormElementPresent("destinationTime");
		assertTextPresent("Seats:");
		assertFormElementPresent("seats");
		assertTextPresent("Luggage:");
		assertFormElementPresent("luggage");
		assertSubmitButtonPresent("ShowDefaultWebpage");
		assertSubmitButtonPresent("angebotBuchen");
		
		//fill out the form - refer to comments in UserGUI
		setTextField("travelDate","2013-02-21");
		setTextField("startingPlace","London");
		setTextField("departureTime","09:35:00");
		setTextField("destinationPlace","Auckland");
		setTextField("destinationTime","09:36:00");
		setTextField("seats","2");
		setTextField("luggage","0");
		
		submit("angebotBuchen");
		
		//verify content of target webpage defaultWebpage
		assertTitleEquals("Fahrtenbörse - User");
		assertTextPresent("Fahrtenbörse - User");
		assertTextPresent("Angebot erstellen:");
		assertFormPresent();
		assertTextPresent("Travel Date: (YYYY-MM-DD)");
		assertFormElementPresent("travelDate");
		assertTextPresent("Starting Place:");
		assertFormElementPresent("startingPlace");
		assertTextPresent("Departure Time: (HH:MM:SS)");
		assertFormElementPresent("departureTime");
		assertTextPresent("Destination Place:");
		assertFormElementPresent("destinationPlace");
		assertTextPresent("Destination Time:");
		assertFormElementPresent("destinationTime");
		assertTextPresent("Seats:");
		assertFormElementPresent("seats");
		assertTextPresent("Luggage:");
		assertFormElementPresent("luggage");
		assertTextPresent("Price:");
		assertFormElementPresent("price");
		assertTextPresent("Contact Information:");
		assertFormElementPresent("contactInformation");
		assertSubmitButtonPresent("SelectAngWebpage");
		assertSubmitButtonPresent("angebotErstellen");
	}
}

