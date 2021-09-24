package com;

import com.POJO.Item;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Map;

public class Utilities {
    public Response getAllItems(){
        Response response = RestAssured.given().when().get(ConfigReader.getEnvProperty("uri") + "skus");
      return response;
    }
    public Response getGivenSKUItem(String sku){
        Response response = RestAssured.given().when().get(ConfigReader.getEnvProperty("uri") + "skus/" + sku);
        return response;
    }
    public Response createGivenItem(Item item){
        Response response = RestAssured.given()
                .header("Content-type", "application/json")
                .and()
                .body(item)
                .when()
                .post(ConfigReader.getEnvProperty("uri") + "skus")
                .then()
                .extract().response();
        return response;
    }

    public Response createGivenItem(Map<String,String> item){
        Response response = RestAssured.given()
                .header("Content-type", "application/json")
                .and()
                .body(item)
                .when()
                .post(ConfigReader.getEnvProperty("uri") + "skus")
                .then()
                .extract().response();
        return response;
    }
    public Response deleteGivenItem(String sku){
        Response response = RestAssured
                .when().delete(ConfigReader.getEnvProperty("uri") + "skus/" + sku);
        return response;
    }

}
