package com.aim_tests;

import com.ConfigReader;
import com.JsonReader;
import com.POJO.HTTPHeaders;
import com.POJO.Item;
import com.POJO.ResponseMetadata;
import com.POJO.SKUItem;
import com.Utilities;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapper;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
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
    //POST
    @Test
    public void VerifyCreateANewItemWithValidBody2() {
        Item item = new Item("fdb1234", "new item", "44.44");
        item.setPrice("544.55");
        response = util.createGivenItem(item);
        System.out.println(response.getBody());
        Assert.assertEquals(200, response.statusCode());
        assertEquals("new item", response.jsonPath().prettyPeek().getString("description"));
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

    // Following test serializes the reponse object with help of SKUItem class
    // and the deserializes data and makes a post/update request with updated data
    @Test
    public void getGivenSKUItemAndUpdate(){
        Response response = RestAssured.given().when().get(ConfigReader.getEnvProperty("uri") + "skus/" + "f6adb84e-c273-4b15-b180-f30803sait44");
        SKUItem skuItem = response.as(SKUItem.class);
        System.out.println(skuItem);
        String server = skuItem.getResponseMetadata().getHTTPHeaders().getServer();
        System.out.println(server);
        Item item = new Item( skuItem.getItem().getSku(), skuItem.getItem().getDescription(), "55.66");
        Response res2  = RestAssured.given().body(item).when().post(ConfigReader.getEnvProperty("uri") + "skus");
        System.out.println(res2.statusCode());
        System.out.println(res2.jsonPath().prettyPeek());
        System.out.println(res2.prettyPeek());
        System.out.println(res2.contentType());
    }
    @Test
    public void practiceNewSKUItem() {
        Item item = new Item( "SKUItem123", "deserialize and serialize item", "55.66");
        Response res  = RestAssured.given().body(item).when().post(ConfigReader.getEnvProperty("uri") + "skus");
        Item item2 = res.as(Item.class);
        System.out.println(item2);
        Response res2= RestAssured.given().when().get(ConfigReader.getEnvProperty("uri") + "skus/" + item2.getSku());
        res2.prettyPrint();
        SKUItem skuItem = res2.as(SKUItem.class);
        Assert.assertEquals(skuItem.getItem().getSku(), "SKUItem123");
        Assert.assertEquals(res2.statusCode(), 200);
        Assert.assertEquals(res2.contentType(), "application/json");
    }
    @Test
    public void createAnItemFromJsonFile(){
        String path = "src/test/resources/testData.json";
        Item item = JsonReader.getItemFromJsonFile(path);
        Response res  = RestAssured.given().body(item).when().post(ConfigReader.getEnvProperty("uri") + "skus");
        Item item2 = res.as(Item.class);
        res.prettyPrint();
        Assert.assertEquals(res.statusCode(), 200);
        Assert.assertEquals(res.contentType(), "application/json");
        Assert.assertEquals(item2.getPrice(), "99.99");
        Assert.assertEquals(item2.getDescription(), "Item from json file");
        Assert.assertEquals(item2.getSku(), "item-123-4-567");
    }
}
