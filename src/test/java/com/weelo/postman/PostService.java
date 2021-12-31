package com.weelo.postman;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.*;

public class PostService {

    @Before
    public void before(){
        RestAssured.baseURI = "https://postman-echo.com/";
    }

    private Map<String, String> responseData(){
        Map<String, String> data = new HashMap<>();
        data.put("name", "Pedro");
        data.put("email", "pedro@correo.com");
        data.put("phone", "1111111");

        return data;
    }

    @Test
    public void postServicesSuccess(){

        given()
                .log().all()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(responseData())
                .post("/post")
                .then()
                .log().ifValidationFails()
                .body("$", hasKey("data")).and()
                .body("data", hasKey("phone")).and()
                .body("data", hasKey("name")).and()
                .body("data", hasKey("email")).and()
                .body("$", hasKey("json")).and()
                .body("json", hasKey("phone")).and()
                .body("json", hasKey("name")).and()
                .body("json", hasKey("email"))
                .log().all();
    }

    @Test
    public void postServicesValidationStatusCode200(){

        given()
                .log().all()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(responseData())
                .post("/post")
                .then()
                .assertThat()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void postServicesQuantityFields(){

        given()
                .log().all()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(responseData())
                .post("/post")
                .then()
                .assertThat()
                .body("size()", is(7))
                .log().all();
    }

    @Test
    public void postServicesValidationSchema(){
        given()
                .log().all()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(responseData())
                .post("/post")
                .then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/postService.json"))
                .log().all();
    }

    @Test
    public void postServicesValidationFields(){
        given()
                .log().all()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(responseData())
                .post("/post")
                .then()
                .assertThat()
                .body("$", hasKey("args")).and()
                .body("data", hasKey("phone")).and()
                .body("data", hasKey("name")).and()
                .body("data", hasKey("email")).and()
                .body("$", hasKey("files")).and()
                .body("$", hasKey("form")).and()
                .body("headers", hasKey("x-forwarded-proto")).and()
                .body("headers", hasKey("x-forwarded-port")).and()
                .body("headers", hasKey("host")).and()
                .body("headers", hasKey("x-amzn-trace-id")).and()
                .body("headers", hasKey("content-length")).and()
                .body("headers", hasKey("accept")).and()
                .body("headers", hasKey("content-type")).and()
                .body("headers", hasKey("user-agent")).and()
                .body("headers", hasKey("accept-encoding")).and()
                .body("$", hasKey("json")).and()
                .body("json", hasKey("phone")).and()
                .body("json", hasKey("name")).and()
                .body("json", hasKey("email")).and()
                .body("$", hasKey("url"))
                .log().all();
    }

    @Test
    public void getServicesValidationFieldsAndValues(){
        given()
                .log().all()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(responseData())
                .post("/post")
                .then()
                .assertThat()
                .body("data.phone", equalTo("1111111")).and()
                .body("data.name", equalTo("Pedro")).and()
                .body("data.email", equalTo("pedro@correo.com")).and()
                .body("headers.x-forwarded-proto", equalTo("https")).and()
                .body("headers.x-forwarded-port", equalTo("443")).and()
                .body("headers.host", equalTo("postman-echo.com")).and()
                .body("headers.content-length", equalTo("61")).and()
                .body("headers.accept", equalTo("application/json, application/javascript, text/javascript, text/json")).and()
                .body("headers.content-type", equalTo("application/json; charset=UTF-8")).and()
                .body("headers.accept-encoding", equalTo("gzip,deflate")).and()
                .body("json.phone", equalTo("1111111")).and()
                .body("json.name", equalTo("Pedro")).and()
                .body("json.email", equalTo("pedro@correo.com")).and()
                .body("url", equalTo("https://postman-echo.com/post"))
                .log().all();
    }

    @Test
    public void postServicesWithoutParameters(){

        given()
                .log().all()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .post("/post")
                .then()
                .log().ifValidationFails()
                .body("args", equalTo(Collections.EMPTY_MAP)).and()
                .body("data", equalTo(Collections.EMPTY_MAP)).and()
                .body("files", equalTo(Collections.EMPTY_MAP)).and()
                .body("form", equalTo(Collections.EMPTY_MAP)).and()
                .body("json", equalTo(null))
                .log().all();
    }
}