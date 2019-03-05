import com.jayway.restassured.RestAssured;
import com.jayway.restassured.filter.log.RequestLoggingFilter;
import com.jayway.restassured.response.ValidatableResponse;
import io.qameta.allure.*;
import org.testng.TestListenerAdapter;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
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


@Listeners({TestListenerAdapter.class})
@Epic("API testing")
@Feature("API testing")

public class TestAPI extends Calculating_Signatures {
    protected static void setupRestAssured() {
        RestAssured.baseURI = "https://api.quickblox.com";
    }

    @BeforeTest
    public void setUp() {
        setupRestAssured();
    }

    String string_request = "application_id=76077&auth_key=4M6nWT7TjY45vEc&nonce=12699&timestamp=1551679855&user[login]=MytestUser&user[password]=MyDochka1";
    String authorization_secret = "BpfxuSMG8EVRENU";
    String signature = Calculating_Signatures.calculateHMAC(string_request, authorization_secret);

    @Test(description = "create session", priority = 1)
    @Severity(SeverityLevel.NORMAL)
    @Story("Session creation")
    public void sessionCreation(){
        Map<String,String> post = new HashMap<>();
        post.put("application_id", "76077");
        post.put("auth_key", "4M6nWT7TjY45vEc");
        post.put("nonce", "12499");
        post.put("timestamp", "1551622435");
        post.put("user[login]", "MytestUser");
        post.put("user[password]", "MyDochka1");
        post.put("signature", "c826677047ec5386537d749c10d75450f9627ebf");

        ValidatableResponse response = given()
                .filter(new RequestLoggingFilter())
                .contentType(ContentType.JSON)
                .body(post)
                .when().post("/session.json")
                .then().log().ifError()
                .assertThat().statusCode(201);

        response.log().all().extract().response();
    }

    @Test(description = "user sign in", priority = 2)
    @Severity(SeverityLevel.NORMAL)
    @Story("User Login")
    public void userSignIn(){
        Map<String,String> post = new HashMap<>();
        post.put("user[login]", "MytestUser");
        post.put("user[password]", "MyDochka1");
        post.put("provider", "facebook");
        post.put("token", "b41ce6f1187b0953b45c28464e6ce6504001292d");

        ValidatableResponse response = given()
                .filter(new RequestLoggingFilter())
                .contentType(ContentType.JSON)
                .body(post)
                .when().post("/login.json")
                .then().log().ifError()
                .body(containsString("user"));

        response.log().all().extract().response();
    }

    @Test(description = "session info", priority = 3)
    @Severity(SeverityLevel.NORMAL)
    @Story("Get Session info")
    public void sessionInfo(){
        Map<String,String> post = new HashMap<>();
        post.put("token", "b41ce6f1187b0953b45c28464e6ce6504001292d");

        given()
                .filter(new RequestLoggingFilter())
                .contentType(ContentType.JSON)
                .body(post)
                .when().get("/session.json")
                .then().log().ifError()
                .assertThat().statusCode(200);
    }

    @Test(description = "user sign out", priority = 4)
    @Severity(SeverityLevel.NORMAL)
    @Story("User Sign out")
    public void userSignOut(){
        Map<String,String> post = new HashMap<>();
        post.put("token", "b41ce6f1187b0953b45c28464e6ce6504001292d");

        given()
                .filter(new RequestLoggingFilter())
                .contentType(ContentType.JSON)
                .body(post)
                .when().delete("/login.json")
                .then().log().ifError()
                .assertThat().statusCode(200);
    }

    @Test(description = "destroy session", priority = 5)
    @Severity(SeverityLevel.NORMAL)
    @Story("Session destroyed")
    public void destroySession(){
        Map<String,String> post = new HashMap<>();
        post.put("token", "b41ce6f1187b0953b45c28464e6ce6504001292d");

        given()
                .filter(new RequestLoggingFilter())
                .contentType(ContentType.JSON)
                .body(post)
                .when().delete("/session.json")
                .then().log().ifError()
                .assertThat().statusCode(200);
    }

    @Test(description = "session info after destroy", priority = 6)
    @Severity(SeverityLevel.NORMAL)
    @Story("Get Session info after destroy")
    public void sessionInfoAfterDestroy(){
        Map<String,String> post = new HashMap<>();
        post.put("token", "b41ce6f1187b0953b45c28464e6ce6504001292d");

        given()
                .filter(new RequestLoggingFilter())
                .contentType(ContentType.JSON)
                .body(post)
                .when().get("/session.json")
                .then().log().ifError()
                .assertThat().statusCode(401);

    }
}

