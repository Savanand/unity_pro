package com.unity.rest;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.main.JsonValidator;
import com.unity.Project;



/**
 * @author Aniket Savanand
 *
 */

@Path("/service")
public class JSONService {
	JSONObject res = new JSONObject();
	
	@GET
	@Path("/requestproject")
	@Produces(MediaType.APPLICATION_JSON)
	public String getProjectInJSON(@QueryParam("projectid") int projectid, @QueryParam("country") String country, 
			@QueryParam("number") int number, @QueryParam("keyword") String keyword, @Context UriInfo uriInfo) throws JsonParseException, JsonMappingException, IOException, JSONException, ParseException {
		//country= country.toUpperCase();
		
//		if(projectid<0){
//			System.out.println("Inside exception");
//	        throw new BadRequestException();
//		}

		System.out.println("URI Info="+
				  "Absolute Path"+uriInfo.getAbsolutePath()
				+ "Base URI"+uriInfo.getBaseUri()
				+ "Request URI"+uriInfo.getRequestUri()
				+ "");
		System.out.println("Scanned params are..."+" \nProject id"+ projectid+" "
				+ "\nCountry"+ country+" \nNumber"+ number+" \nKeyword"+ keyword);
//		ObjectMapper objectMapper = new ObjectMapper();
		List<Project> interResult= new ArrayList<Project>();
		try (FileInputStream fis = new FileInputStream("E:\\projects.txt")) {
	        JsonFactory jf = new JsonFactory();
	        JsonParser jp = jf.createParser(fis);
	        jp.setCodec(new ObjectMapper());
	        jp.nextToken();
	        while (jp.hasCurrentToken()) {
	            Project project = jp.readValueAs(Project.class);
	            jp.nextToken();
	            interResult.add(project);  // to get all lists
	        }
	    }
		
		System.out.println("Done- Checking all projects");
		printProjects(interResult);
		
		
		
		if(projectid<=0 && country==null && number<=0 && keyword==null){
			// return project with highest price
			System.out.println("True- no url params");
			System.out.println("Calculating  project with highest cost");
			Optional<Project> p = interResult.stream().max((o1,o2) -> Double.compare(o1.getProjectCost(),o2.getProjectCost()));
			if(p!=null && p.isPresent()){
				return generateResponse(p.get());
			}
		}
		
		boolean filterByCostFlag= false;   
		if(projectid>0){    // apply projectid filter if its present
			interResult = interResult.stream()
			    .filter(p -> p.getId()==projectid)
			    .collect(Collectors.toList());
			System.out.println("Done- Checking projects with exact id");
			printProjects(interResult);
			
		}
		else {  // set flag true if project id is not present, filter by cost later- at the end
			filterByCostFlag= true;
		}
		interResult = interResult.stream()
				.filter(p -> p.getEnabled().equals("true"))
			    .filter(p -> p.getProjectUrl()!=null)
			    .filter(p -> p.getProjectUrl()!="")
			    .collect(Collectors.toList());
		System.out.println("Done- Checking projects with enabled true and filtering projecturl with null");
		printProjects(interResult);
		interResult = interResult.stream()
			    .filter(p -> checkIfExpired(p))
			    .collect(Collectors.toList());
		System.out.println("Done- Checking projects for expiry date ");
		printProjects(interResult);
		
		if(country!=null){
			interResult = interResult.stream()
				    .filter(p -> Arrays.asList(p.getTargetCountries()).contains(country.toUpperCase())).collect(Collectors.toList());
			System.out.println("Done- Checking projects for country");
			printProjects(interResult);
					
		}
		if(number>0){
			interResult = interResult.stream()
				    .filter(p -> p.getTargetKeys()
				    	.stream().anyMatch(q -> q.getNumber()>=number))
				    .collect(Collectors.toList());
			System.out.println("Done- Checking projects for number");
			printProjects(interResult);
			
		} 
		if(keyword!=null){
			interResult = interResult.stream()
				    .filter(p -> p.getTargetKeys()
				    		.stream().anyMatch(q -> q.getKeyword().equals(keyword)))
				    .collect(Collectors.toList());
			System.out.println("Done- Checking projects for keyword");
			printProjects(interResult);

		}
		if(filterByCostFlag){
			Optional<Project> p = interResult.stream().max((o1,o2) -> Double.compare(o1.getProjectCost(),o2.getProjectCost()));
			if(p!=null && p.isPresent()){
				return generateResponse(p.get());
			}
		}
		if(!interResult.isEmpty()){
			System.out.println("True: result is not empty");
			printProjects(interResult);
			return generateResponse(interResult.get(0));
		}
		else{
			System.out.println("True: no projects found");
			printProjects(interResult);
			res.put("message", "no project found");
			return res.toString(); 
		}
	}

	@POST
	@Path("/createproject")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createProjectInJSON(String Project) throws JsonGenerationException, JsonMappingException, IOException, ProcessingException {

		
		
		ObjectMapper mapper = new ObjectMapper();
		Project filter = mapper.readValue(Project, Project.class);
		
		System.out.println("id=" + filter.getId());
		if (filter.getId()<=0){
			System.out.println("Inside exception");
	        throw new BadRequestException();
		}
	    System.out.println(mapper.writeValueAsString(filter));
	    
	    if(!validInputJson(mapper.writeValueAsString(filter))){
//	    if(!validInputJson(Project)){

	    	return Response.status(400).entity(mapper.writeValueAsString(filter)).build();
	    }
	    
	  //  ClassLoader classLoader = getClass().getClassLoader();
	 //   System.out.println(classLoader.getResource("resources/projects.txt").getFile());
		mapper.writeValue(new FileOutputStream("E:\\projects.txt", true), filter);
		System.out.println("--Done--");
		return Response.status(201).entity(mapper.writeValueAsString(filter)).build();

	}
	
	public boolean validInputJson(String jsonData) throws IOException, ProcessingException{
			String jsonSchema="{"+
				   "\"$schema\": \"http://json-schema.org/draft-04/schema#\","+
				    "\"definitions\": {},"+
				    "\"id\": \"http://example.com/example.json\","+
				    "\"properties\": {"+
				        "\"creationDate\": {"+
				            "\"id\": \"/properties/creationDate\","+
				            "\"type\": \"string\""+
				        "},"+
				        "\"enabled\": {"+
				            "\"id\": \"/properties/enabled\","+
				            "\"type\": \"string\""+
				        "},"+
				        "\"expiryDate\": {"+
				            "\"id\": \"/properties/expiryDate\","+
				            "\"type\": \"string\""+
				        "},"+
				        "\"id\": {"+
				            "\"id\": \"/properties/id\","+
				            "\"type\": \"integer\""+
				        "},"+
				        "\"projectCost\": {"+
				            "\"id\": \"/properties/projectCost\","+
				            "\"type\": \"number\""+
				        "},"+
				        "\"projectName\": {"+
				            "\"id\": \"/properties/projectName\","+
				            "\"type\": \"string\""+
				        "},"+
				        "\"projectUrl\": {"+
				            "\"id\": \"/properties/projectUrl\","+
				            "\"type\": \"string\""+
				        "},"+
				        "\"targetCountries\": {"+
				            "\"id\": \"/properties/targetCountries\","+
				            "\"items\": {"+
				                "\"id\": \"/properties/targetCountries/items\","+
				                "\"type\": \"string\""+
				            "},"+
				            "\"type\": \"array\""+
				        "},"+
				        "\"targetKeys\": {"+
				            "\"id\": \"/properties/targetKeys\","+
				            "\"items\": {"+
				                "\"id\": \"/properties/targetKeys/items\","+
				                "\"properties\": {"+
				                    "\"keyword\": {"+
				                        "\"id\": \"/properties/targetKeys/items/properties/keyword\","+
				                        "\"type\": \"string\""+
				                    "},"+
				                    "\"number\": {"+
				                        "\"id\": \"/properties/targetKeys/items/properties/number\","+
				                        "\"type\": \"integer\""+
				                    "}"+
				                "},"+
				                "\"type\": \"object\""+
				            "},"+
				            "\"type\": \"array\""+
				        "}"+
				    "},"+
				    "\"required\": ["+
			        "\"id\""+
			    "],"+
				    "\"type\": \"object\""+
				"}";
	       final JsonNode schema = JsonLoader.fromString(jsonSchema);
	       final JsonNode data = JsonLoader.fromString(jsonData);
	       final JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
	       JsonValidator validator = factory.getValidator();

	       ProcessingReport report = validator.validate(schema, data);
	       System.out.println(report.isSuccess());
	       return report.isSuccess();
	}
	
	public boolean checkIfExpired(Project p){
		SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy h:m:s");
		Date today_ = Calendar.getInstance().getTime(); 
		String today = sdf.format(today_);
		boolean isExpired=true;
		try {
			isExpired=sdf.parse(today).before(sdf.parse(p.getExpiryDate()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Checking project="+ p.getId()+ " and is expired="+isExpired);
		return isExpired;
		
	}
	
	public void printProjects(List<Project> li){
		
		ListIterator<Project> it= li.listIterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
	}
	
	public String generateResponse(Project p) throws JSONException{
		
		res.put("projectName", p.getProjectName());
		res.put("projectCost", p.getProjectCost());
		res.put("projectUrl", p.getProjectUrl());
		
		return res.toString();
	}
	
	public boolean isAlpha(String name) {
	    char[] chars = name.toCharArray();

	    for (char c : chars) {
	        if(!Character.isLetter(c)) {
	            return false;
	        }
	    }

	    return true;
	}
}
