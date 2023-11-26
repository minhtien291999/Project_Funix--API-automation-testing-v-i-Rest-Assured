package com.api.auto.testcase;

import static org.testng.Assert.*;
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

public class TC_API_Login {
	private String account,password;
	private Response response;
	private ResponseBody responseBody;
	private JsonPath jsonBody;
	
	@BeforeClass
	public void init() {
		//init data
		String baseUrl=PropertiesFileUtils.getProperty("baseurl");
		String loginPath= PropertiesFileUtils.getProperty("loginPath");
		account = PropertiesFileUtils.getProperty("account");
		password = PropertiesFileUtils.getProperty("password");
		
		RestAssured.baseURI=baseUrl;
		//make body
		Map<String,Object>body =new HashMap<String,Object>();
		body.put("account", account);
		body.put("password", password);
		
		RequestSpecification request = RestAssured.given()
				.contentType(ContentType.JSON)
				.body(body);
		response =  request.post(loginPath);
		responseBody = response.body();
		jsonBody = responseBody.jsonPath();
		// in responseBody
		System.out.println(" " + responseBody.asPrettyString());
	}


	@Test(priority = 0)
	public void TC01_Validate200Ok() {
		Assert.assertEquals(response.statusCode(), 200,"Status Check Failed!");
	}
	
	
	@Test(priority = 1)
	public void TC02_ValidateMessage() {
		 // kiểm chứng response body có chứa trường message hay không
        // kiểm chứng trường message có = "Đăng nhập thành công
		assertTrue(responseBody.asString().contains("message"),"Message field check Faied");
		String expectedMsg = jsonBody.get("message");
		String actualMsg ="Đăng nhập thành công";
		Assert.assertEquals(actualMsg,expectedMsg,"Check for mismatched message field values");
	}
	
	@Test(priority = 2)
	public void TC03_ValidateToken() {
		 // kiểm chứng response body có chứa trường token hay không
		assertTrue(responseBody.asString().contains("token"),"token field check Faied");
		String token = jsonBody.get("token");
		// Lưu token
		PropertiesFileUtils file = new PropertiesFileUtils();
		file.saveToken(token);
	}
	
	@Test(priority = 3)
	public void TC05_ValidateUserType() {
         // kiểm chứng response body có chứa thông tin user và trường type hay không
         // kiểm chứng trường type có phải là “UNGVIEN”
		assertTrue(responseBody.asString().contains("user"),"user field check Faied");	
		assertTrue(responseBody.asString().contains("type"),"type field check Faied");
		String result = jsonBody.getString("user.type");
		Assert.assertEquals(result,"UNGVIEN","Check for mismatched message field values");
	}
	
	@Test(priority = 4)
	public void TC06_ValidateAccount() {
          // kiểm chứng response chứa thông tin user và trường account hay không
          // Kiểm chứng trường account có khớp với account đăng nhập
          // kiểm chứng response chứa thông tin user và trường password hay không
          // Kiểm chứng trường password có khớp với password đăng nhập
		assertTrue(responseBody.asString().contains("account"),"account field check Faied");	
		assertTrue(responseBody.asString().contains("password"),"password field check Faied");
		
		String userAccount = jsonBody.getString("user.account");
		Assert.assertEquals(userAccount,account,"Check for mismatched account field values");
		
		String userPassWord = jsonBody.getString("user.password");
		Assert.assertEquals(userPassWord,password,"Check for mismatched password field values");
	}
}

