import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.Time;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class FB_ApplicationTest {

	@Test
	public void testAngeboteAuflisten() {
		DB_Facade stub = mock(DB_Facade.class);
		DB_Facade.setInstance(stub);
		FB_Application.getInstance().angeboteAuflisten();
		verify(stub,times(1)).angeboteBrowsen();
	}

	@Test
	public void testBookOffer() {
		DB_Facade stub = mock(DB_Facade.class);
		DB_Facade.setInstance(stub);
		FB_Application.getInstance().bookOffer(Date.valueOf("2013-02-21"), "London", Time.valueOf("09:35:00"), "Auckland", 
				Time.valueOf("09:36:00"), 2, 0);
		verify(stub,times(1)).bookOffer(Date.valueOf("2013-02-21"), "London", Time.valueOf("09:35:00"), "Auckland", 
				Time.valueOf("09:36:00"), 2, 0);	
	}

	@Test
	public void testErstelleAngebot() {
		DB_Facade stub = mock(DB_Facade.class);
		DB_Facade.setInstance(stub);
		FB_Application.getInstance().erstelleAngebot(Date.valueOf("2013-02-21"), "London", Time.valueOf("09:35:00"), "Auckland", 
							Time.valueOf("09:36:00"), 2, 0,5.20,"Adresse");
		verify(stub,times(1)).angebotErstellen(Date.valueOf("2013-02-21"), "London", Time.valueOf("09:35:00"), "Auckland", 
							Time.valueOf("09:36:00"), 2, 0,5.20,"Adresse");
	}
}
