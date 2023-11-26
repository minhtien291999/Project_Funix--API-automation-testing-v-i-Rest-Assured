package com.api.auto.testcase;

import static org.testng.Assert.*;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.api.auto.utils.PropertiesFileUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class TC_API_CreateWork {
	private String token;
	private Response response;
	private ResponseBody responseBody;
	private JsonPath jsonBody;
	
	//Chúng ta có thể tự tạo data
	private String myWork = PropertiesFileUtils.getProperty("myWork");
	private String myExperience = PropertiesFileUtils.getProperty("myExperience");
	private String myEducation =PropertiesFileUtils.getProperty("myEducation");
	@BeforeClass
	public void init() {
		//init data
		String baseUrl = PropertiesFileUtils.getProperty("baseurl");
		String createWorkPath =PropertiesFileUtils.getProperty("createWorkPath");
		String token=PropertiesFileUtils.getToken("token");
		
		RestAssured.baseURI=baseUrl;
		RestAssured.basePath=createWorkPath;
		//make body
		Map<String, Object>body= new HashMap<String, Object>();
		body.put("nameWork",myWork);
		body.put("experience",myExperience);
		body.put("education",myEducation);
		
		RequestSpecification request = RestAssured.given()
				.contentType(ContentType.JSON)
				.header("token",token)
		        .body(body);
		
		response = request.post();
		responseBody=response.body();
		jsonBody=responseBody.jsonPath();
		
		System.out.println(" "+responseBody.asPrettyString());
	}
	
	@Test(priority = 0)
	public void TC01_Validate201Created() {
		Assert.assertEquals(response.statusCode(), 201);
	}
	
	@Test(priority = 1)
	public void TC02_ValidateWorkId() {
		assertTrue(responseBody.asString().contains("id"),"No id");
	}
	
	@Test(priority = 2)
	public void TC03_ValidateNameOfWorkMatched() {
		Assert.assertEquals(jsonBody.getString("nameWork"), myWork);
	}
	
	@Test(priority = 3)
	public void TC03_ValidateExperienceMatched() {
		Assert.assertEquals(jsonBody.getString("experience"), myExperience);
	}

	@Test(priority = 4)
	public void TC03_ValidateEducationMatched() {
		Assert.assertEquals(jsonBody.getString("education"), myEducation);
	}

}
