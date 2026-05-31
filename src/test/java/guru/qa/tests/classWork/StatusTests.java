package guru.qa.tests.classWork;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class StatusTests {

    /*
    1. Сделать запрос по: https://selenoid.autotests.cloud/#/
    2. Получить ответ вида: {"total":5,"used":1,"queued":0,"pending":0,"browsers":{"chrome":{"127.0":{},"128.0":{"user1":{"count":1,"sessions":[{"id":"ae743b13dac7a86969b0eabc96c7e319","container":"0402993d995d932323f522a95841c3112677958d08450d6bcbaab0fe0d339351","containerInfo":{"id":"0402993d995d932323f522a95841c3112677958d08450d6bcbaab0fe0d339351","ip":"172.18.0.4"},"vnc":true,"screen":"1920x1080x24","caps":{"browserName":"chrome","version":"128.0","screenResolution":"1920x1080x24","enableVNC":true,"videoScreenSize":"1920x1080","name":"Manual session","labels":{"manual":"true"},"sessionTimeout":"60m"},"started":"2026-05-31T07:40:49.274699567Z"}]}}},"firefox":{"124.0":{},"125.0":{}},"opera":{"108.0":{},"109.0":{}}}}
    3. проверить что total = 5
     */

    @Test
    void totalAmountTest() {
        get("https://selenoid.autotests.cloud/status") // https://selenoid.autotests.cloud/
                .then()
                .body("total", equalTo(5));
    }

    @Test
    void totalAmountTestWithResponseLog() {
        get("https://selenoid.autotests.cloud/status") // https://selenoid.autotests.cloud/
                .then()
                .log().all()
                .body("total", equalTo(5));
    }

    @Test
    void totalAmountTestWithAllLog() {
        given()
                .log().all()
                .when()
                .get("https://selenoid.autotests.cloud/status") // https://selenoid.autotests.cloud/
                .then()
                .log().all()
                .body("total", equalTo(5));
    }

    @Test
    void totalAmountTestWithDifferentLog() {
        given()
                .log().uri()
                .log().method()
                .log().headers()
                .when()
                .get("https://selenoid.autotests.cloud/status") // https://selenoid.autotests.cloud/
                .then()
                .log().status()
                .log().body()
                .body("total", equalTo(5));
    }

    @Test
    void checkStatusCode200() {
        given()
                .log().uri()
                .when()
                .get("https://selenoid.autotests.cloud/status") // https://selenoid.autotests.cloud/
                .then()
                .log().status()
                .statusCode(200);
    }

    @Test
    void checkRequiredFieldsTest() {
        given()
                .log().uri()
                .when()
                .get("https://selenoid.autotests.cloud/status") // https://selenoid.autotests.cloud/
                .then()
                .log().status()
                .log().body()
                .body("", hasKey("total"))
                .body("", hasKey("used"))
                .body("", hasKey("queued"))
                .body("", hasKey("pending"))
                .body("", hasKey("browsers"));
    }

    @Test
    void checkCromeVersionTest() {
        given()
                .log().uri()
                .when()
                .get("https://selenoid.autotests.cloud/status") // https://selenoid.autotests.cloud/
                .then()
                .log().status()
                .log().body()
                .body("browsers.chrome", hasKey("127.0"))
                .body("browsers.chrome", hasKey("128.0"))
                .body("browsers.firefox", hasKey("124.0"));

    }

    @Test
    void checkSchemaTest() {
        given()
                .log().uri()
                .when()
                .get("https://selenoid.autotests.cloud/status") // https://selenoid.autotests.cloud/
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/status_response_schema.json"));

    }

}
