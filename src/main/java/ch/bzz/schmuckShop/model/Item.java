package ch.bzz.schmuckShop.model;

import javax.ws.rs.FormParam;

public class Item {



    @FormParam("itemUUID")
    private String itemUUID;

    @FormParam("name")
    private String name;

    @FormParam("description")
    private String description;

    @FormParam("price")
    private int price;

    public String getItemUUID() {
        return itemUUID;
    }

    public void setItemUUID(String itemUUID) {
        this.itemUUID = itemUUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
