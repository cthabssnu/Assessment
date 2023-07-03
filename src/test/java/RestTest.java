import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class RestTest {

    @Test
    public void createUserAndValidate() {
        User user = new User();
        user.setId(43433);
        user.setUsername("323243431qw");
        user.setFirstName("RRRR");
        user.setLastName("LLL");
        user.setEmail("we@gmail.com");
        user.setPassword("23dwewe");
        user.setPhone("2324433");
        user.setUserStatus(0);

        Gson gson = new Gson();
        String userJson = gson.toJson(user);

        given()
                .contentType(ContentType.JSON)
                .body(userJson)
                .when()
                .post("https://petstore.swagger.io/v2/user/createWithArray")
                .then()
                .statusCode(200);

        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .when()
                        .get("https://petstore.swagger.io/v2/user/" + user.getUsername())
                        .then()
                        .extract()
                        .response();

        Assert.assertEquals(response.statusCode(), 200);

        User retrievedUser = gson.fromJson(response.getBody().asString(), User.class);
        Assert.assertEquals(retrievedUser.getUsername(), user.getUsername());
        Assert.assertEquals(retrievedUser.getEmail(), user.getEmail());

        Response response1 =
                given()
                        .contentType(ContentType.JSON)
                        .when()
                        .get("https://petstore.swagger.io/v2/user/nonexistentuser")
                        .then()
                        .extract()
                        .response();

        Assert.assertEquals(response1.statusCode(), 404);

        Response response2 =
                given()
                        .contentType(ContentType.JSON)
                        .when()
                        .get("https://petstore.swagger.io/v2/user/invalid/username")
                        .then()
                        .extract()
                        .response();

        Assert.assertEquals(response2.statusCode(), 400);

        User duplicateUser = new User();
        duplicateUser.setId(43434);
        duplicateUser.setUsername(user.getUsername());
        String duplicateUserJson = gson.toJson(duplicateUser);

        Response response3 =
                given()
                        .contentType(ContentType.JSON)
                        .body(duplicateUserJson)
                        .when()
                        .post("https://petstore.swagger.io/v2/user/createWithArray")
                        .then()
                        .extract()
                        .response();

        Assert.assertNotEquals(response3.statusCode(), 200);
    }
}
