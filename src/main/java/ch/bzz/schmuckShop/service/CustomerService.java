package ch.bzz.schmuckShop.service;


import ch.bzz.schmuckShop.data.DataHandler;
import ch.bzz.schmuckShop.model.Customer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * services for reading, adding, changing and deleting publishers
 */
@Path("customer")
public class CustomerService {

    /**
     * reads a list of all publishers
     * @return  publishers as JSON
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listCustomers() {
        List<Customer> customerList = DataHandler.getInstance().readAllCustomers();
        return Response
                .status(200)
                .entity(customerList)
                .build();
    }

    /**
     * reads a publisher identified by the uuid
     * @param publisherUUID
     * @return publisher
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readPublisher(
            @QueryParam("uuid") String publisherUUID
    ) {
        int httpStatus = 200;
        Customer customer = DataHandler.getInstance().readPublisherByUUID(publisherUUID);
        if (customer == null) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity(customer)
                .build();
    }
}