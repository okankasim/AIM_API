package com;

import com.POJO.Item;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class JsonReader {
    public static Item getItemFromJsonFile(String filePath)  {
        Item item = null;
        try{
            Gson gson = new Gson();
            FileReader fileReader = new FileReader(filePath);
            item = gson.fromJson(fileReader, Item.class);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return item;
    }
}
