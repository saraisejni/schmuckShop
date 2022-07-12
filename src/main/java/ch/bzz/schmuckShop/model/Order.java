package ch.bzz.schmuckShop.model;

import javax.ws.rs.FormParam;

public class Order {

    @FormParam("orderUUID")
    private String orderUUID;

    @FormParam("dateCreated")
    private String dateCreated;

    @FormParam("dateShipped")
    private String dateShipped;

    @FormParam("status")
    private String status;

    public String getOrderUUID() {
        return orderUUID;
    }

    public void setOrderUUID(String orderUUID) {
        this.orderUUID = orderUUID;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateShipped() {
        return dateShipped;
    }

    public void setDateShipped(String dateShipped) {
        this.dateShipped = dateShipped;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

