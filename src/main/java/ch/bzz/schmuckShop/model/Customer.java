package ch.bzz.schmuckShop.model;

import javax.ws.rs.FormParam;

public class Customer {


    @FormParam("customerUUID")
    private String customerUUID;

    @FormParam("name")
    private String name;

    @FormParam("adresse")
    private String adresse;

    @FormParam("email")
    private String email;

    @FormParam("creditCardInfo")
    private String creditCardInfo;


    public String getCustomerUUID() {
        return customerUUID;
    }

    public void setCustomerUUID(String customerUUID) {
        this.customerUUID = customerUUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreditCardInfo() {
        return creditCardInfo;
    }

    public void setCreditCardInfo(String creditCardInfo) {
        this.creditCardInfo = creditCardInfo;
    }
}

