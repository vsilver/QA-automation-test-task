import com.google.gson.Gson;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.filter.log.RequestLoggingFilter;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.ResponseBody;
import com.jayway.restassured.response.ValidatableResponse;
import com.jcraft.jsch.Session;
import io.qameta.allure.*;
import org.testng.TestListenerAdapter;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.parsing.Parser;
import com.jayway.restassured.response.Response;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.*;
import static org.codehaus.groovy.tools.shell.util.Logger.io;
import static org.hamcrest.Matchers.*;


@Listeners({TestListenerAdapter.class})
@Epic("API testing")
@Feature("API testing")

public class TestAPI {
    protected static void setupRestAssured() {
        RestAssured.baseURI = "https://api.quickblox.com";
    }

    @BeforeTest
    public void setUp() {
        setupRestAssured();
    }

    String string_request = "application_id=76077&auth_key=4M6nWT7TjY45vEc&nonce=29999";
    String authorization_secret = "BpfxuSMG8EVRENU";

    @Test(description = "create session", priority = 1)
    @Severity(SeverityLevel.NORMAL)
    @Story("Session creation")
    public void sessionCreation(){
        Map<String,String> post = new HashMap<>();

        long timestamp = new Date().getTime() / 1000;

        post.put("application_id", "76077");
        post.put("auth_key", "4M6nWT7TjY45vEc");
        post.put("nonce", "29999");
        post.put("timestamp", timestamp + "");

        string_request += "&timestamp=" + timestamp;

        try {
            String sign = Calculating_Signatures.calculateHMAC_SHA(string_request, authorization_secret);
            post.put("signature", sign);
        } catch (SignatureException e) {
            e.printStackTrace();
        }

        Response response1 = given()
                .filter(new RequestLoggingFilter())
                .contentType(ContentType.JSON)
                .body(post)
                .when().post("/session.json")
                .then().log().ifError()
                .assertThat().statusCode(201)
                .extract().response();

        System.out.println(response1.path("session.token").toString());

    }

    @Test(description = "user sign in", priority = 2)
    @Severity(SeverityLevel.NORMAL)
    @Story("User Login")
    public void userSignIn(){
        Map<String,String> post = new HashMap<>();
        post.put("user[login]", "MytestUser");
        post.put("user[password]", "MyDochka1");
        post.put("provider", "facebook");
        post.put("token", "7f30592aa0875260fcb0997ef96167f6a901292d");

        ValidatableResponse response1 = given()
                .filter(new RequestLoggingFilter())
                .contentType(ContentType.JSON)
                .body(post)
                .when().post("/login.json")
                .then().log().ifError()
                .body(containsString("user"));

        response1.log().all().extract().response();
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



