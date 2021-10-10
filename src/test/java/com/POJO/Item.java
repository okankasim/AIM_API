package com.POJO;

public class Item {
    private String createdAt;
    private String description;
    private String price;
    private String sku;
    private String updatedAt;

    public Item(String sku, String description, String price) {
        this.sku = sku;
        this.description = description;
        this.price = price;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "Item{" +
                "createdAt='" + createdAt + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", sku='" + sku + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
