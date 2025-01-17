import com.thoughtworks.gauge.Step;
import io.restassured.response.Response;
import org.json.JSONObject;
import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

public class ApiTestImplementation {
    private Response response;
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    @Step("Get all posts from JSONPlaceholder")
    public void getAllPosts() {
        response = given()
            .baseUri(BASE_URL)
            .when()
            .get("/posts");
    }

    @Step("Verify get posts response status code is <statusCode>")
    public void verifyGetStatusCode(String statusCode) {
        assertEquals(Integer.parseInt(statusCode), response.getStatusCode());
    }

    @Step("Get a specific post with id <id>")
    public void getSpecificPost(String id) {
        response = given()
            .baseUri(BASE_URL)
            .when()
            .get("/posts/" + id);
    }

    @Step("Verify the post title contains <expectedTitle>")
    public void verifyPostTitle(String expectedTitle) {
        String actualTitle = response.jsonPath().getString("title");
        assertTrue(actualTitle.contains(expectedTitle));
    }

    @Step("Create a new post with title <title> and body <body>")
    public void createNewPost(String title, String body) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("title", title);
        requestBody.put("body", body);
        requestBody.put("userId", 1);

        response = given()
            .baseUri(BASE_URL)
            .header("Content-type", "application/json")
            .body(requestBody.toString())
            .when()
            .post("/posts");
    }

    @Step("Verify post creation status code is <statusCode>")
    public void verifyPostStatusCode(String statusCode) {
        assertEquals(Integer.parseInt(statusCode), response.getStatusCode());
    }

    @Step("Verify created post contains correct title <expectedTitle>")
    public void verifyCreatedPostTitle(String expectedTitle) {
        String actualTitle = response.jsonPath().getString("title");
        assertEquals(expectedTitle, actualTitle);
    }
}
