package com.aim_tests;

import com.ConfigReader;
import com.POJO.Item;
import com.Utilities;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Tests {
    Utilities util = new Utilities();
    Response response;
    Map<String,String> item;
    @Test
    public void VerifyAllAvailableItems() {
        response = util.getAllItems();
        Assert.assertEquals(200, response.statusCode());
    }

    @Test
    public void VerifyGivenItemExist() {
        response = util.getGivenSKUItem("f6adb84e-c273-4b15-b180-f30803dadc73");
        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals("sait dinc", response.jsonPath().get("Item.description"));
        response.prettyPrint();
    }

    //POST
    @Test
    public void VerifyCreateANewItemWithValidBody() {
        Item item = new Item("f6adb84e-c273-4b15-b180-f30803sait44", "sait dinc", "44.44");
        response = util.createGivenItem(item);
        Assert.assertEquals(200, response.statusCode());
        assertEquals("sait dinc", response.jsonPath().prettyPeek().getString("description"));
        assertEquals(response.headers().getValue("Content-Type"), "application/json");
    }
    @Test
    public void VeirfyNewItemCannotBeCreatedWithMissingSKU(){
        item = new HashMap<>();
        item.put("description", "missing sku");
        item.put("price", "4.99");
        response = util.createGivenItem(item);
        assertEquals(400, response.statusCode());
    }
    @Test // should say missing description and sku
    public void VeirfyNewItemCannotBeCreatedWithMissingSKUAndDescription(){
        item = new HashMap<>();
        item.put("price", "4.99");
        response = util.createGivenItem(item);
        assertEquals(400, response.statusCode());
        response.getBody().peek();
    }
    @Test
    public void VeirfyNewItemCannotBeCreatedWithMissingPrice(){
        item = new HashMap<>();
        item.put("sku", "random sku");
        item.put("description", "missing sku");
        response = util.createGivenItem(item);
        assertEquals(400, response.statusCode());
        response.getBody().peek();
    }
    @Test // should say missing price and description
    public void VeirfyNewItemCannotBeCreatedWithMissingPriceAndDescription(){
        item = new HashMap<>();
        item.put("sku", "random sku");
        response = util.createGivenItem(item);
        assertEquals(400, response.statusCode());
        response.getBody().peek();
    }
    @Test // should say missing SKU value or invalid sku instead it returns internal error
    public void VeirfyNewItemCannotBeCreatedWithMissingSKUValue(){
        item = new HashMap<>();
        item.put("sku", "");
        item.put("description", "missing sku");
        item.put("price", "4.99");
        response = util.createGivenItem(item);
        assertEquals(400, response.statusCode(), response.jsonPath().prettyPeek().getString("message"));
        response.getBody().peek();
    }
    @Test // should return invalid uri or some more relevant error message instead it displays "Missing Authentication Token" which does not make sense
    public void VeirfyNewItemCannotBeCreatedWithWrongUri(){
        item = new HashMap<>();
        item.put("sku", "f6adb84e-c273-4b15-b180-f30803ssdd44");
        item.put("description", "missing sku");
        item.put("price", "4.99");
        Response response = RestAssured.given()
                .header("Content-type", "application/json")
                .and()
                .body(item)
                .when()
                .post(ConfigReader.getEnvProperty("uri") + "skus/"+ item.get("sku"))
                .then()
                .extract().response();
        assertEquals(403, response.statusCode(), response.jsonPath().prettyPeek().getString("message"));

        response.getBody().peek();
    }

    // DELETE
    @Test
    public void VerifyGivenItemDeleted() {
        response = util.deleteGivenItem("f6adb84e-c273-4b15-b180-f30803sait44");
        Assert.assertEquals(200, response.statusCode());
    }

    @Test // Previously deleted item cannot be deleted again since there is no more that item so the status code cannot be 200
    public void VerifyPreviouslyDeletedItem() {
        response = util.deleteGivenItem("f6adb84e-c273-4b15-b180-f30803sait44");
        assertEquals(404, response.statusCode(), "This Item previously deleted");
    }

    @Test // There is no such an SKU so it should not be returning 200
    public void VerifyInvalidItemCannotBeDeleted() {
        response = util.deleteGivenItem("this is not a valid SKU");
        assertEquals(404, response.statusCode(), "This Item not a valid item");
    }

    @Test // deleted items should not return 200 when get request happens
    public void VerifyGetRequestToDeletedItem(){
        response = util.getGivenSKUItem("f6adb84e-c273-4b15-b180-f30803sait44");
        assertEquals(404, response.statusCode(), "This Item already deleted and should return 404 status code");
    }



}
