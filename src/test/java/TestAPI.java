import com.jayway.restassured.RestAssured;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.parsing.Parser;
import com.jayway.restassured.response.Response;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.basic;
import static org.codehaus.groovy.tools.shell.util.Logger.io;
import static org.hamcrest.Matchers.*;


public class TestAPI {
    protected static void setupRestAssured() {
        RestAssured.baseURI = "https://api.quickblox.com";
    }

    @BeforeTest
    public void setUp() {
        setupRestAssured();
    }

    @Test(description = "Create session", priority = 1)
    public void sessionCreation(){
        Map<String,String> post = new HashMap<>();
        post.put("application_id", "76077");
        post.put("auth_key", "4M6nWT7TjY45vEc");
        post.put("nonce", "12348");
        post.put("timestamp", "1551537895");
        post.put("user[login]", "MytestUser");
        post.put("user[password]", "MyDochka1");
        post.put("signature", "f11458874ca398ab8c04ea1664d72cd839ebcb91");

        given().
                contentType(ContentType.JSON)
                .body(post)
                .when().post("/session.json")
                .then().statusCode(201);

    }



}

