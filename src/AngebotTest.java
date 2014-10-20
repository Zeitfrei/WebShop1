import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.Time;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class AngebotTest extends TestCase {
	
	Angebot a;

	@Before
	public void setUp() throws Exception {
		a = new Angebot(Date.valueOf("2013-02-21"), "London", Time.valueOf("09:35:00"), "Auckland", 
				Time.valueOf("09:36:00"), 2, 0,5.20,"Adresse");
	}

	@Test
	public void testAngebotAktualisieren() {
		assertEquals(true,a.angebotAktualisieren(1));
		assertEquals(false,a.angebotAktualisieren(2));
	}

}
