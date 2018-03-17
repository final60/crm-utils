package crm.utils;

import static crm.utils.StringUtils.hasText;
import static crm.utils.ValidationUtils.validateParam;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ProtocolException;
import java.net.URL;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;

/**
 * Utils methods related to HTTP communication.
 * 
 * @author Chris Mepham
 *
 */
public abstract class HttpUtils {
	
	public static final String METHOD_POST = "POST";
	public static final String METHOD_GET = "GET";

	/**
	 * Allows both POST and GET HTTP requests.
	 * 
	 * @param url The URI end-point the request is being made to.
	 * @param body The body of the message if this is a POST call.
	 * @param headers The headers to be added to the request.
	 * @param method The HTTP method (POST or GET).
	 * 
	 * @return A single entry array consisting of the response code and response body.
	 * 
	 * @throws IOException
	 */
	public static final Entry<Integer,String> post(final String url,  
												   final String body, 
												   final Map<String, String> headers,
												   String method) throws IOException {
		
		validateParam(url, "url");
		
		URL obj = new URL(url);
		
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		
		con.setDoOutput(true);
		
		addHeaders(con, headers, body, method);

		if (METHOD_POST.equals(method)) addBody(con, body);
		
		final String responseBody = readResponseBody(con);
		
		final int responseCode = con.getResponseCode();
		
		con.disconnect();
		
		return new SimpleEntry<Integer, String>(responseCode, responseBody);
	}
	
	private static final void addHeaders(HttpsURLConnection con, Map<String, String> headers, String body, String method) throws ProtocolException {
		con.setRequestMethod(method);
		
		if (hasText(body)) {
			con.setFixedLengthStreamingMode(body.getBytes().length);
		}
	}
	
	private static final void addBody(HttpsURLConnection con, String body) throws IOException {
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(body);
		wr.flush();
		wr.close();
	}
	
	private static final String readResponseBody(HttpsURLConnection con) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response.toString();
	}
}
























