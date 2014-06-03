package test.server;

import test.client.GreetingService;
import test.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
GreetingService {

public String greetServer(String input) throws IllegalArgumentException {
// Verify that the input is valid. 
if (!FieldVerifier.isValidName(input)) {
	// If the input is not valid, throw an IllegalArgumentException back to
	// the client.
	throw new IllegalArgumentException(
			"Name must be at least 4 characters long");
}

String serverInfo = getServletContext().getServerInfo();
String userAgent = getThreadLocalRequest().getHeader("User-Agent");

// Escape data from the client to avoid cross-site script vulnerabilities.
input = escapeHtml(input);
userAgent = escapeHtml(userAgent);

return "Hello, " + input + "!<br><br>I am running " + serverInfo
		+ ".<br><br>It looks like you are using:<br>" + userAgent;
}
@Override
public String greetServer(int a, int b, int c)  throws IllegalArgumentException {
// TODO Auto-generated method stub
	double delta=Math.pow(b, 2) - 4 * a * c;
	if (delta<0)
	{return "no Solution";}
	else if(delta==0)
	{double x = (-b ) / (2 * a);
		return "One Solution Resut of this eqation is X="+x;}
	else {
double x1 = (-b +delta) / (2 * a);
double x2 = (-b - delta) / (2 * a);
return "Two Solution Resut of this eqation is X1= " + x1 +" X2= " + x2;
}}

/**
* Escape an html string. Escaping data received from the client helps to
* prevent cross-site script vulnerabilities.
* 
* @param html the html string to escape
* @return the escaped string
*/
private String escapeHtml(String html) {
if (html == null) {
	return null;
}
return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
		.replaceAll(">", "&gt;");
}


}
