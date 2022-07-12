package ch.bzz.schmuckShop.data;


import ch.bzz.schmuckShop.model.Customer;
import ch.bzz.schmuckShop.model.Item;
import ch.bzz.schmuckShop.model.Order;
import ch.bzz.schmuckShop.service.Config;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * reads and writes the data in the JSON-files
 */
public class DataHandler {
    private static DataHandler instance = null;
    private List<Item> itemList;
    private List<Customer> customerList;
    private List<Order> orderList;

    /**
     * private constructor defeats instantiation
     */
    private DataHandler() {
        setCustomerList(new ArrayList<>());
        readCustomerJSON();
        setItemList(new ArrayList<>());
        readItemJSON();
        setOrderList(new ArrayList<>());
        readOrderJSON();
    }

    /**
     * gets the only instance of this class
     * @return instance
     */
    public static DataHandler getInstance() {
        if (instance == null)
            instance = new DataHandler();
        return instance;
    }


    /**
     * reads all items
     * @return list of items
     */
    public List<Item> readAllItems() {
        return getItemList();
    }

    /**
     * reads a item by its uuid
     * @param itemUUID
     * @return the Item (null=not found)
     */
    public Item readItemByUUID(String itemUUID) {
        Item item = null;
        for (Item entry : getItemList()) {
            if (entry.getItemUUID().equals(itemUUID)) {
                item = entry;
            }
        }
        return item;
    }

    /**
     * inserts a new item into the itemList
     *
     * @param item the item to be saved
     */
    public void insertItem(Item item) {
        getItemList().add(item);
        writeItemJSON();
    }

    /**
     * updates the itemList
     */
    public void updateItem() {
        writeItemJSON();
    }

    /**
     * deletes a item identified by the itemUUID
     * @param itemUUID  the key
     * @return  success=true/false
     */
    public boolean deleteItem(String itemUUID) {
        Item item = readItemByUUID(itemUUID);
        if (item != null) {
            getItemList().remove(item);
            writeItemJSON();
            return true;
        } else {
            return false;
        }
    }

    /**
     * reads all publishers
     * @return list of items
     */
    public List<Customer> readAllCustomers() {
        return customerList;
    }

    /**
     * reads a publisher by its uuid
     * @param customerUUID
     * @return the Publisher (null=not found)
     */
    public Customer readCustomerByUUID(String customerUUID) {
        Customer customer = null;
        for (Customer entry : getCustomerList()) {
            if (entry.getCustomerUUID().equals(customerUUID)) {
                customer = entry;
            }
        }
        return customer;
    }

    /**
     * inserts a new publisher into the itemList
     *
     * @param customer the publisher to be saved
     */
    public void insertCustomer(Customer customer) {
        getCustomerList().add(customer);
        writeCustomerJSON();
    }

    /**
     * updates the publisherList
     */
    public void updateCustomer() {
        writeCustomerJSON();
    }

    /**
     * deletes a publisher identified by the customerUUID
     * @param customerUUID  the key
     * @return  success=true/false
     */
    public boolean deleteCustomer(String customerUUID) {
        Customer customer = readCustomerByUUID(customerUUID);
        if (customer != null) {
            getCustomerList().remove(customer);
            writeCustomerJSON();
            return true;
        } else {
            return false;
        }
    }









    /**
     * reads all orders
     * @return list of order
     */
    public List<Order> readAllOrders() {
        return orderList;
    }

    /**
     * reads a publisher by its uuid
     * @param orderUUID
     * @return the Publisher (null=not found)
     */
    public Order readOrderByUUID(String orderUUID) {
        Order order = null;
        for (Order entry : getOrderList()) {
            if (entry.getOrderUUID().equals(orderUUID)) {
                order = entry;
            }
        }
        return order;
    }

    /**
     * inserts a new publisher into the itemList
     *
     * @param order the publisher to be saved
     */
    public void insertOrder(Order order) {
        getOrderList().add(order);
        writeOrderJSON();
    }

    /**
     * updates the publisherList
     */
    public void updateOrder() {
        writeOrderJSON();
    }

    /**
     * deletes a publisher identified by the publisherUUID
     * @param orderUUID  the key
     * @return  success=true/false
     */
    public boolean deleteOrder(String orderUUID) {
        Order order = readOrderByUUID(orderUUID);
        if (order != null) {
            getOrderList().remove(order);
            writeCustomerJSON();
            return true;
        } else {
            return false;
        }
    }


    /**
     * reads the items from the JSON-file
     */
    private void readItemJSON() {
        try {
            String path = Config.getProperty("itemJSON");
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(path)
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Item[] items = objectMapper.readValue(jsonData, Item[].class);
            for (Item item : items) {
                getItemList().add(item);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * writes the itemList to the JSON-file
     */
    private void writeItemJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String bookPath = Config.getProperty("itemJSON");
        try {
            fileOutputStream = new FileOutputStream(bookPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getItemList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * reads the publishers from the JSON-file
     */
    private void readCustomerJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(
                            Config.getProperty("customerJSON")
                    )
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Customer[] customers = objectMapper.readValue(jsonData, Customer[].class);
            for (Customer customer : customers) {
                getCustomerList().add(customer);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * writes the publisherList to the JSON-file
     */
    private void writeCustomerJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String bookPath = Config.getProperty("customerJSON");
        try {
            fileOutputStream = new FileOutputStream(bookPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getItemList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }



















    /**
     * reads the publishers from the JSON-file
     */
    private void readOrderJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(
                            Config.getProperty("orderJSON")
                    )
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Order[] orders = objectMapper.readValue(jsonData, Order[].class);
            for (Order order : orders) {
                getOrderList().add(order);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * writes the publisherList to the JSON-file
     */
    private void writeOrderJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String bookPath = Config.getProperty("orderJSON");
        try {
            fileOutputStream = new FileOutputStream(bookPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getOrderList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * gets itemList
     *
     * @return value of itemList
     */

    private List<Item> getItemList() {
        return itemList;
    }

    /**
     * sets itemList
     *
     * @param itemList the value to set
     */

    private void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    /**
     * gets publisherList
     *
     * @return value of publisherList
     */

    private List<Customer> getCustomerList() {
        return customerList;
    }

    /**
     * sets publisherList
     *
     * @param customerList the value to set
     */

    private void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }


    private List<Order> getOrderList() {
        return orderList;
    }

    /**
     * sets orderList
     *
     * @param orderList the value to set
     */

    private void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

}