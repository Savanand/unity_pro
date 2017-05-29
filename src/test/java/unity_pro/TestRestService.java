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

	
	
	/*
	 * TEST METHODS - GET
	 */
	@Test
	
	public void test_all_params_present() throws JSONException, org.json.JSONException {
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
	@Test
	public void test_keyword_param_skipped() throws JSONException, org.json.JSONException {
		Client client = Client.create();

		WebResource webResource = client
		   .resource("http://localhost:8080/unity_pro/rest/service/requestproject?projectid=1&country=usa&number=29");

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
	
	@Test
	public void test_number_param_skipped() throws JSONException, org.json.JSONException {
		Client client = Client.create();

		WebResource webResource = client
		   .resource("http://localhost:8080/unity_pro/rest/service/requestproject?projectid=1&country=usa&keyword=sports");

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
	
	@Test
	public void test_country_param_skipped() throws JSONException, org.json.JSONException {
		Client client = Client.create();

		WebResource webResource = client
		   .resource("http://localhost:8080/unity_pro/rest/service/requestproject?projectid=1&number=29&keyword=sports");

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
	
	@Test
	public void test_projectId_param_skipped() throws JSONException, org.json.JSONException {
		// should filter based on other params and return project with highest cost
		Client client = Client.create();

		WebResource webResource = client
		   .resource("http://localhost:8080/unity_pro/rest/service/requestproject?number=29&keyword=sports");

		ClientResponse response = webResource.accept("application/json")
                   .get(ClientResponse.class);
		String actual=response.getEntity(String.class);
		String expected= "{\"projectName\":\"test project number 5\",\"projectCost\":8.8,\"projectUrl\":\"www.testpro5.com\"}";
		//JSONObject actual = new JSONObject(response.getEntity(String.class));
		//JSONObject expected = new JSONObject("{\"projectName\":\"test project number 1\",\"projectCost\":5.5,\"projectUrl\":\"http:\\/\\/www.unity3d.com\"}");
		System.out.println("Output from Server .... \n");
		System.out.println(actual);
		JSONAssert.assertEquals(expected, actual, false);
	}
	
	@Test
	public void test_only_projecid_present() throws JSONException, org.json.JSONException {
		//should return project if present
		Client client = Client.create();

		WebResource webResource = client
		   .resource("http://localhost:8080/unity_pro/rest/service/requestproject?projectid=4");

		ClientResponse response = webResource.accept("application/json")
                   .get(ClientResponse.class);
		String actual=response.getEntity(String.class);
		String expected= "{\"projectName\":\"test project number 4\",\"projectCost\":6.4,\"projectUrl\":\"www.hight.com\"}";
		//JSONObject actual = new JSONObject(response.getEntity(String.class));
		//JSONObject expected = new JSONObject("{\"projectName\":\"test project number 1\",\"projectCost\":5.5,\"projectUrl\":\"http:\\/\\/www.unity3d.com\"}");
		System.out.println("Output from Server .... \n");
		System.out.println(actual);
		JSONAssert.assertEquals(expected, actual, false);
	}
	
	@Test
	public void test_all_param_skipped() throws JSONException, org.json.JSONException {
		//should return project with highest cost
		Client client = Client.create();

		WebResource webResource = client
		   .resource("http://localhost:8080/unity_pro/rest/service/requestproject");

		ClientResponse response = webResource.accept("application/json")
                   .get(ClientResponse.class);
		String actual=response.getEntity(String.class);
		String expected= "{\"projectName\":\"test project number 5\",\"projectCost\":8.8,\"projectUrl\":\"www.testpro5.com\"}";
		//JSONObject actual = new JSONObject(response.getEntity(String.class));
		//JSONObject expected = new JSONObject("{\"projectName\":\"test project number 1\",\"projectCost\":5.5,\"projectUrl\":\"http:\\/\\/www.unity3d.com\"}");
		System.out.println("Output from Server .... \n");
		System.out.println(actual);
		JSONAssert.assertEquals(expected, actual, false);
	}
	
	@Test
	public void test_no_match_project_found_skip_project_id() throws JSONException, org.json.JSONException {
		//should return json message with no matching project found
		Client client = Client.create();

		WebResource webResource = client
		   .resource("http://localhost:8080/unity_pro/rest/service/requestproject?country=uae&number=29&keyword=sports");

		ClientResponse response = webResource.accept("application/json")
                   .get(ClientResponse.class);
		String actual=response.getEntity(String.class);
		String expected= "{\"message\":\"no project found\"}";
		//JSONObject actual = new JSONObject(response.getEntity(String.class));
		//JSONObject expected = new JSONObject("{\"projectName\":\"test project number 1\",\"projectCost\":5.5,\"projectUrl\":\"http:\\/\\/www.unity3d.com\"}");
		System.out.println("Output from Server .... \n");
		System.out.println(actual);
		JSONAssert.assertEquals(expected, actual, false);
	}
	
	@Test
	public void test_no_match_project_found_with_project_id() throws JSONException, org.json.JSONException {
		//should return json message with no matching project found
		Client client = Client.create();

		WebResource webResource = client
		   .resource("http://localhost:8080/unity_pro/rest/service/requestproject?projectid=1&country=uae&number=29&keyword=sports");

		ClientResponse response = webResource.accept("application/json")
                   .get(ClientResponse.class);
		String actual=response.getEntity(String.class);
		String expected= "{\"message\":\"no project found\"}";
		//JSONObject actual = new JSONObject(response.getEntity(String.class));
		//JSONObject expected = new JSONObject("{\"projectName\":\"test project number 1\",\"projectCost\":5.5,\"projectUrl\":\"http:\\/\\/www.unity3d.com\"}");
		System.out.println("Output from Server .... \n");
		System.out.println(actual);
		JSONAssert.assertEquals(expected, actual, false);
	}

	@Test
	public void test_should_skip_expired_project() throws JSONException, org.json.JSONException {
		//if project expired date is passed, should return json message with no matching project found
		Client client = Client.create();

		WebResource webResource = client
		   .resource("http://localhost:8080/unity_pro/rest/service/requestproject?projectid=6&country=usa&number=29&keyword=sports");

		ClientResponse response = webResource.accept("application/json")
                   .get(ClientResponse.class);
		String actual=response.getEntity(String.class);
		String expected= "{\"message\":\"no project found\"}";
		//JSONObject actual = new JSONObject(response.getEntity(String.class));
		//JSONObject expected = new JSONObject("{\"projectName\":\"test project number 1\",\"projectCost\":5.5,\"projectUrl\":\"http:\\/\\/www.unity3d.com\"}");
		System.out.println("Output from Server .... \n");
		System.out.println(actual);
		JSONAssert.assertEquals(expected, actual, false);
	}
	
	@Test
	public void test_should_skip_not_enabled_project() throws JSONException, org.json.JSONException {
		//if project is not enabled, should return json message with no matching project found
		Client client = Client.create();

		WebResource webResource = client
		   .resource("http://localhost:8080/unity_pro/rest/service/requestproject?projectid=7&country=usa&number=29&keyword=sports");

		ClientResponse response = webResource.accept("application/json")
                   .get(ClientResponse.class);
		String actual=response.getEntity(String.class);
		String expected= "{\"message\":\"no project found\"}";
		//JSONObject actual = new JSONObject(response.getEntity(String.class));
		//JSONObject expected = new JSONObject("{\"projectName\":\"test project number 1\",\"projectCost\":5.5,\"projectUrl\":\"http:\\/\\/www.unity3d.com\"}");
		System.out.println("Output from Server .... \n");
		System.out.println(actual);
		JSONAssert.assertEquals(expected, actual, false);
	}
	
	/* implement logic may be failing*/
	@Test
	public void test_should_skip_project_if_no_projectUrl() throws JSONException, org.json.JSONException {
		//if project is not enabled, should return json message with no matching project found
		Client client = Client.create();

		WebResource webResource = client
		   .resource("http://localhost:8080/unity_pro/rest/service/requestproject?projectid=8&country=usa&number=29&keyword=sports");

		ClientResponse response = webResource.accept("application/json")
                   .get(ClientResponse.class);
		String actual=response.getEntity(String.class);
		String expected= "{\"message\":\"no project found\"}";
		//JSONObject actual = new JSONObject(response.getEntity(String.class));
		//JSONObject expected = new JSONObject("{\"projectName\":\"test project number 1\",\"projectCost\":5.5,\"projectUrl\":\"http:\\/\\/www.unity3d.com\"}");
		System.out.println("Output from Server .... \n");
		System.out.println(actual);
		JSONAssert.assertEquals(expected, actual, false);
	}
	
	/*
	 * TEST METHODS- POST
	 */
	
	@Test
	
	public void test_valid_json_post() throws JSONException, org.json.JSONException {
		// check POST valid json input in POST request
		Client client = Client.create();

		WebResource webResource = client
		   .resource("http://localhost:8080/unity_pro/rest/service/createproject");
		String input="{\"id\": 9,\"projectName\":\"test project number 9\",\"creationDate\": \"05122017 00:00:00\",\"expiryDate\": \"05272018 00:00:00\",\"enabled\": \"true\",\"targetCountries\": [\"RUSSIA\",\"GERMANY\"],\"projectCost\": 5.4,\"projectUrl\":\"http://www.testproject9.com\",\"targetKeys\":[{\"number\": 20,\"keyword\": \"reset\"},{\"number\": 32,\"keyword\": \"mix\"}]}";
		ClientResponse response = webResource.type("application/json")
                   .post(ClientResponse.class, input);
		String expected= "{\"id\": 9,\"projectName\":\"test project number 9\",\"creationDate\": \"05122017 00:00:00\",\"expiryDate\": \"05272018 00:00:00\",\"enabled\": \"true\",\"targetCountries\": [\"RUSSIA\",\"GERMANY\"],\"projectCost\": 5.4,\"projectUrl\":\"http://www.testproject9.com\",\"targetKeys\":[{\"number\": 20,\"keyword\": \"reset\"},{\"number\": 32,\"keyword\": \"mix\"}]}";
		System.out.println("Output from Server .... \n");
		String actual = response.getEntity(String.class);
		System.out.println(actual);
		//Assert.assertEquals(201, response.getStatus());
		JSONAssert.assertEquals(expected, actual, false);
	}
	
	
	@Test
	
	public void test_invalid_json_post() throws JSONException, org.json.JSONException {
		// check POST valid json input in POST request
		Client client = Client.create();

		WebResource webResource = client
		   .resource("http://localhost:8080/unity_pro/rest/service/createproject");
		String input="{\"projectName\":\"test project number 9\",\"creationDate\": \"05122017 00:00:00\",\"expiryDate\": \"05272018 00:00:00\",\"enabled\": \"true\",\"targetCountries\": [\"RUSSIA\",\"GERMANY\"],\"projectCost\": 5.4,\"projectUrl\":\"http://www.testproject9.com\",\"targetKeys\":[{\"number\": 20,\"keyword\": \"reset\"},{\"number\": 32,\"keyword\": \"mix\"}]}";
		ClientResponse response = webResource.type("application/json")
                   .post(ClientResponse.class, input);
		String expected= "{\"id\": 9,\"projectName\":\"test project number 9\",\"creationDate\": \"05122017 00:00:00\",\"expiryDate\": \"05272018 00:00:00\",\"enabled\": \"true\",\"targetCountries\": [\"RUSSIA\",\"GERMANY\"],\"projectCost\": 5.4,\"projectUrl\":\"http://www.testproject9.com\",\"targetKeys\":[{\"number\": 20,\"keyword\": \"reset\"},{\"number\": 32,\"keyword\": \"mix\"}]}";
		System.out.println("Output from Server .... \n");
		String actual = response.getEntity(String.class);
		System.out.println(actual);
		//Assert.assertEquals(201, response.getStatus());
		JSONAssert.assertEquals(expected, actual, false);
	}
}
