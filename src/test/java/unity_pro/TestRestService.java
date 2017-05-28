/**
 * 
 */
package unity_pro;



import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import junit.framework.Assert;

/**
 * @author Aniket Savanand
 *
 */
public class TestRestService {

	@Test
	
	public void test() throws JSONException, org.json.JSONException {
		Client client = Client.create();

		WebResource webResource = client
		   .resource("http://localhost:8080/unity_pro/rest/service/requestproject?projectid=1&country=usa&number=29&keyword=sports");

		ClientResponse response = webResource.accept("application/json")
                   .get(ClientResponse.class);
		String actual=response.getEntity(String.class);
		String expected= "{\"projectName\":\"test project number 1\",\"projectCost\":5.5,\"projectUrl\":\"http:\\/\\/www.unity3d.com\"}";
		//JSONObject actual = new JSONObject(response.getEntity(String.class));
		//JSONObject expected = new JSONObject("{\"projectName\":\"test project number 1\",\"projectCost\":5.5,\"projectUrl\":\"http:\\/\\/www.unity3d.com\"}");
		System.out.println("Output from Server .... \n");
		System.out.println(actual);
		JSONAssert.assertEquals(expected, actual, false);
	}

}
