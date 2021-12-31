package com.weelo.postman;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import java.util.Collections;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;

public class GetService {

    @Before
    public void before(){
        RestAssured.baseURI = "https://postman-echo.com/";
    }

    @Test
    public void getServicesSuccess(){
        given()
                .get("/get?test=value&other=33")
                .then()
                .assertThat()
                .body("args.test", equalTo("value")).and()
                .body("args.other", equalTo("33"))
                .log().all();
    }

    @Test
    public void getServicesWithVariousParameters(){
        given()
                .get("/get?test=value&other=33&var1=1&var2=2&var3=3")
                .then()
                .assertThat()
                .body("args.test", equalTo("value")).and()
                .body("args.other", equalTo("33")).and()
                .body("args.var1", equalTo("1")).and()
                .body("args.var2", equalTo("2")).and()
                .body("args.var3", equalTo("3"))
                .log().all();
    }

    @Test
    public void getServicesValidationStatusCode200(){
        given()
                .get("/get?test=value&other=33")
                .then()
                .assertThat()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void getServicesQuantityFields(){
        given()
                .get("/get?test=value&other=33")
                .then()
                .assertThat()
                .body("size()", is(3))
                .log().all();
    }

    @Test
    public void getServicesValidationSchema(){
        given()
                .get("/get?test=value&other=33")
                .then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/getService.json"))
                .log().all();
    }

    @Test
    public void getServicesValidationFields(){
        given()
                .get("/get?test=value&other=33")
                .then()
                .assertThat()
                .body("args", hasKey("test")).and()
                .body("args", hasKey("other")).and()
                .body("headers", hasKey("x-forwarded-proto")).and()
                .body("headers", hasKey("x-forwarded-port")).and()
                .body("headers", hasKey("host")).and()
                .body("headers", hasKey("x-amzn-trace-id")).and()
                .body("headers", hasKey("accept")).and()
                .body("headers", hasKey("user-agent")).and()
                .body("headers", hasKey("accept-encoding")).and()
                .body("$", hasKey("url"))
                .log().all();
    }

    @Test
    public void getServicesValidationFieldsAndValues(){
        given()
                .get("/get?test=value&other=33")
                .then()
                    .assertThat()
                    .body("args.test", equalTo("value")).and()
                    .body("args.other", equalTo("33")).and()
                    .body("headers.x-forwarded-proto", equalTo("https")).and()
                    .body("headers.x-forwarded-port", equalTo("443")).and()
                    .body("headers.host", equalTo("postman-echo.com")).and()
                    .body("headers.accept", equalTo("*/*")).and()
                    .body("headers.accept-encoding", equalTo("gzip,deflate")).and()
                    .body("url", equalTo("https://postman-echo.com/get?test=value&other=33"))
                    .log().all();
    }

    @Test
    public void getServicesWithoutParameters(){
        given()
                .get("/get")
                .then()
                .assertThat()
                .body("args", equalTo(Collections.EMPTY_MAP))
                .log().all();
    }

    @Test
    public void servicesDifferentGet(){
        given()
                .get("/abc")
                .then()
                .statusCode(404)
                .log().all();
    }
}