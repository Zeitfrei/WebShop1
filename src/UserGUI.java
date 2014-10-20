import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.*;

//Change both return values of checkCreate() and checkBook() to true to enter Information by hand

public class UserGUI extends HttpServlet {

	private static final long serialVersionUID = -6248880990366938622L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		//String to store the generated HTML code
		StringBuffer responseString = new StringBuffer();
		
		//Map of all parameters of request
		Map<String, String[]> parameters = request.getParameterMap();
		
		//state transition from SelectAngWebpage to Buchen or initial transition to ShowDefaultWebpage
		if(parameters.containsKey("buchenForm")){
			responseString.append(showAngebotBuchenForm());
		}else{
			responseString.append(defaultWebpage());
		}
		responseString.append("</body>");
		responseString.append("</html>");
		
		response.getWriter().println(responseString.toString());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		StringBuffer responseString = new StringBuffer();
		Map<String, String[]> parameters = request.getParameterMap();
		
		//the name of the submit is a parameter of the request
		//from ShowDefaultWebpage to SelectAngWebpage - 'angeboteBrowsen'
		if(parameters.containsKey("SelectAngWebpage")){             
			ArrayList<Angebot> res = FB_Application.getInstance().angeboteAuflisten();
			responseString.append(angebotsDarstellung(request, res));
		//bucht und geht zur defaultWebpage
		}else if(parameters.containsKey("angebotBuchen")){      
			if(checkBook()){
				FB_Application.getInstance().bookOffer(
						Date.valueOf(request.getParameter("travelDate")), request.getParameter("startingPlace"), 
						Time.valueOf(request.getParameter("departureTime")), request.getParameter("destinationPlace"), 
						Time.valueOf(request.getParameter("destinationTime")), Integer.parseInt(request.getParameter("seats")), 
						Integer.parseInt(request.getParameter("luggage")));
			}else{
				//Bucht zufälliges Angebot zum Test
				int i = (int) (Math.random()*10);
				if(i<4){
					FB_Application.getInstance().bookOffer(
							Date.valueOf("2013-02-21"), "London", Time.valueOf("09:35:00"), "Auckland", 
							Time.valueOf("09:36:00"), 2, 0);
				}else if(i>7){
					FB_Application.getInstance().bookOffer(
							Date.valueOf("2013-01-25"), "Frankfurt", Time.valueOf("08:10:00"), "Duisburg", 
							Time.valueOf("11:30:00"), 2, 0);
				}else{
					FB_Application.getInstance().bookOffer(
							Date.valueOf("2013-01-21"), "Duisburg", Time.valueOf("11:50:00"), "Krefeld", 
							Time.valueOf("12:30:00"), 1, 0);
				}
			} 
			responseString.append(defaultWebpage());
		//erstellt Angebot, geht zur defaultWebpage
		}else if(parameters.containsKey("angebotErstellen")){
			if(checkCreate()){
				FB_Application.getInstance().erstelleAngebot(
						Date.valueOf(request.getParameter("travelDate")), request.getParameter("startingPlace"), 
						Time.valueOf(request.getParameter("departureTime")), request.getParameter("destinationPlace"), 
						Time.valueOf(request.getParameter("destinationTime")), Integer.parseInt(request.getParameter("seats")), 
						Integer.parseInt(request.getParameter("luggage")),Double.parseDouble(request.getParameter("price")),
						request.getParameter("contactInformation"));
			}else{
				//Erstellt zufälliges Angebot zum Test
				int i = (int) (Math.random()*10);
				if(i<4){
					FB_Application.getInstance().erstelleAngebot(
							Date.valueOf("2013-02-21"), "London", Time.valueOf("09:35:00"), "Auckland", 
							Time.valueOf("09:36:00"), 2, 0,5.20,"Adresse");
				}else if(i>7){
					FB_Application.getInstance().erstelleAngebot(
							Date.valueOf("2013-01-25"), "Frankfurt", Time.valueOf("08:10:00"), "Duisburg", 
							Time.valueOf("11:30:00"), 3, 0,20.50,"Adresse");
				}else{
					FB_Application.getInstance().erstelleAngebot(
							Date.valueOf("2013-01-21"), "Duisburg", Time.valueOf("11:50:00"), "Krefeld", 
							Time.valueOf("12:30:00"), 1, 0,3.50,"Adresse");
				}
			}
			responseString.append(defaultWebpage());
		}else{
			responseString.append(defaultWebpage());
		}		
		responseString.append("</body>");
		responseString.append("</html>");
		
		response.getWriter().println(responseString.toString());
	}
	
	private String defaultWebpage(){
		StringBuffer responseString = new StringBuffer();
		// document type and HTML-Header information
		responseString.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://w3.org/TR/html4/strict.dtd\">");
		responseString.append("<html>");
		responseString.append("<head>");
		responseString.append("<title> Fahrtenbörse - User</title>");
		responseString.append("</head>");
		
		//content of the default webpage
		responseString.append("<body>");
		responseString.append("<h2>Fahrtenbörse - User</h2>");
		
		responseString.append("<form method=\"post\" action=\"UserGUI\">");
		responseString.append("<input type=\"submit\" name=\"SelectAngWebpage\" value=\"Browse Offers\"/><br />");
		responseString.append("<br />");
		responseString.append("<br />");
		responseString.append("<h4>Angebot erstellen:</h4>");
		responseString.append("<table>");
			responseString.append("<tr>");
				responseString.append("<td align=\"left\">Travel Date: (YYYY-MM-DD)</td>");
				responseString.append("<td align=\"right\"> <input type=\"text\" name=\"travelDate\" align=\"right\"></td>");
			responseString.append("</tr>");
			responseString.append("<tr>");
				responseString.append("<td align=\"left\">Starting Place:</td>");
				responseString.append("<td align=\"right\"><input type=\"text\" name=\"startingPlace\" align=\"right\"></td>");
			responseString.append("</tr>");
			responseString.append("<tr>");
				responseString.append("<td align=\"left\">Departure Time: (HH:MM:SS)</td>");
				responseString.append("<td align=\"right\"><input type=\"text\" name=\"departureTime\"></td>");
			responseString.append("</tr>");
			responseString.append("<tr>");
				responseString.append("<td align=\"left\">Destination Place:</td>");
				responseString.append("<td align=\"right\"><input type=\"text\" name=\"destinationPlace\" style=\"float:center\"></td>");
			responseString.append("</tr>");
			responseString.append("<tr>");
				responseString.append("<td align=\"left\">Destination Time:</td>");
				responseString.append("<td align=\"right\"><input type=\"text\" name=\"destinationTime\"></td>");
			responseString.append("</tr>");
			responseString.append("<tr>");
				responseString.append("<td align=\"left\">Seats:</td>");
				responseString.append("<td align=\"right\"><input type=\"text\" name=\"seats\"></td>");
			responseString.append("</tr>");
			responseString.append("<tr>");
				responseString.append("<td align=\"left\">Luggage:</td>");
				responseString.append("<td align=\"right\"><input type=\"text\" name=\"luggage\"></td>");
			responseString.append("</tr>");
			responseString.append("<tr>");
				responseString.append("<td align=\"left\">Price::</td>");
				responseString.append("<td align=\"right\"><input type=\"text\" name=\"price\"></td>");
			responseString.append("</tr>");
			responseString.append("<tr>");
				responseString.append("<td align=\"left\">Contact Information:</td>");
				responseString.append("<td align=\"right\"><input type=\"text\" name=\"contactInformation\"></td>");
			responseString.append("</tr>");
		responseString.append("</table>");
		responseString.append("<br />");
		responseString.append("<input type=\"submit\" name=\"angebotErstellen\" value=\"Create Offer\"/>");
		return responseString.toString();
	}
	
	public String showAngebotBuchenForm(){
		StringBuffer responseString = new StringBuffer();
		
		// document type and HTML-Header information
		responseString.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://w3.org/TR/html4/strict.dtd\">");
		responseString.append("<html>");
		responseString.append("<head>");
		responseString.append("<title> Fahrtenbörse - User</title>");
		responseString.append("</head>");
		responseString.append("<body>");
		responseString.append("<h2>Fahrtenbörse - Angebot Buchen</h2>");

		responseString.append("<table>");
			responseString.append("<tr>");
				responseString.append("<td align=\"left\">Travel Date: (YYYY-MM-DD)</td>");
				responseString.append("<td align=\"right\"> <input type=\"text\" name=\"travelDate\" align=\"right\"></td>");
			responseString.append("</tr>");
			responseString.append("<tr>");
				responseString.append("<td align=\"left\">Starting Place:</td>");
				responseString.append("<td align=\"right\"><input type=\"text\" name=\"startingPlace\" align=\"right\"></td>");
			responseString.append("</tr>");
			responseString.append("<tr>");
				responseString.append("<td align=\"left\">Departure Time: (HH:MM:SS)</td>");
				responseString.append("<td align=\"right\"><input type=\"text\" name=\"departureTime\"></td>");
			responseString.append("</tr>");
			responseString.append("<tr>");
				responseString.append("<td align=\"left\">Destination Place:</td>");
				responseString.append("<td align=\"right\"><input type=\"text\" name=\"destinationPlace\" style=\"float:center\"></td>");
			responseString.append("</tr>");
			responseString.append("<tr>");
				responseString.append("<td align=\"left\">Destination Time:</td>");
				responseString.append("<td align=\"right\"><input type=\"text\" name=\"destinationTime\"></td>");
			responseString.append("</tr>");
			responseString.append("<tr>");
				responseString.append("<td align=\"left\">Seats:</td>");
				responseString.append("<td align=\"right\"><input type=\"text\" name=\"seats\"></td>");
			responseString.append("</tr>");
			responseString.append("<tr>");
				responseString.append("<td align=\"left\">Luggage:</td>");
				responseString.append("<td align=\"right\"><input type=\"text\" name=\"luggage\"></td>");
			responseString.append("</tr>");
		responseString.append("</table>");
		
		responseString.append("<form method=\"post\" action=\"UserGUI\">");
		responseString.append("<input type=\"submit\" name=\"ShowDefaultWebpage\" value=\"back\"/>");
		responseString.append("<input type=\"submit\" name=\"angebotBuchen\" value=\"Book Offer\"/>");
		responseString.append("</form>");
		return responseString.toString();
	}
	
	private String angebotsDarstellung(HttpServletRequest request, ArrayList<Angebot> res){
		StringBuffer responseString = new StringBuffer();
		responseString.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://w3.org/TR/html4/strict.dtd\">");
		responseString.append("<html>");
		responseString.append("<head>");
		responseString.append("<title> Fahrtenbörse - User</title>");
		responseString.append("</head>");
		responseString.append("<body>");
		responseString.append("<form method=\"post\" action=\"UserGUI\">");
		responseString.append("<h2>Fahrtenbörse - Angebote Browsen</h2>");
		responseString.append("<input type=\"submit\" name=\"ShowDefaultWebpage\" value=\"Back\"/>");
	
		//Table contains all Angebote in DB
		responseString.append("<table border=\"1\">");
		
		responseString.append("<col width=\"80\">");
		responseString.append("<col width=\"80\">");
		responseString.append("<col width=\"60\">");
		responseString.append("<col width=\"80\">");
		responseString.append("<col width=\"80\">");
		responseString.append("<col width=\"50\">");
		responseString.append("<col width=\"70\">");
		responseString.append("<col width=\"50\">");
		
		responseString.append("<tr>");
		responseString.append("<th>Travel Date</th>");
		responseString.append("<th>Starting Place</th>");
		responseString.append("<th>Departure Time</th>");
		responseString.append("<th>Destination Place</th>");
		responseString.append("<th>Destination Time</th>");
		responseString.append("<th>Seats</th>");
		responseString.append("<th>Luggage</th>");
		responseString.append("<th>Price</th>");
		responseString.append("</tr>");
		
		//Fills in rows of the table with values of each Angebot
		for(Angebot an : res){
			
			responseString.append("<tr>");
				responseString.append("<td>" + an.getTravelDate() + "</td>");
				responseString.append("<td>" + an.getStartingPlace() + "</td>");
				responseString.append("<td>" + an.getDepartureTime() + "</td>");
				responseString.append("<td>" + an.getDestinationPlace() + "</td>");
				responseString.append("<td>" + an.getdestinationTime() + "</td>");
				responseString.append("<td>" + an.getSeats() + "</td>");
				responseString.append("<td>" + an.getluggage() + "</td>");
				responseString.append("<td>" + an.getPrice() + "</td>");
			responseString.append("</tr>");
		}
		responseString.append("</table>");
		responseString.append("</form>");
		responseString.append("<form method=\"get\" action=\"UserGUI\">");
		responseString.append("<input type=\"submit\" name=\"buchenForm\" value=\"Book Offer\"/>");
		responseString.append("</form>");
		return responseString.toString();
	}
	
	//Should check if parameters of request are correct
	//Change both return values to true to enter Information by hand
	private boolean checkCreate(){
		return false;
	}
	private boolean checkBook(){
		return false;
	}
}
