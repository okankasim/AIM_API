package com.POJO;

public class SKUItem {
    private Item Item;
    private ResponseMetadata ResponseMetadata;
    private int RetryAttempts;

    public SKUItem(Item item, ResponseMetadata responseMetadata, int retryAttempts) {
        Item = item;
        ResponseMetadata = responseMetadata;
        RetryAttempts = retryAttempts;
    }


    public Item getItem() {
        return Item;
    }

    public ResponseMetadata getResponseMetadata() {
        return ResponseMetadata;
    }

    public int getRetryAttempts() {
        return RetryAttempts;
    }

    @Override
    public String toString() {
        return "CustomResponse{" +
                "item=" + Item +
                ", ResponseMetadata=" + ResponseMetadata +
                ", RetryAttempts=" + RetryAttempts +
                '}';
    }
}
