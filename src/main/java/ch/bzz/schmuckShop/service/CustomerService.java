package ch.bzz.schmuckShop.service;


import ch.bzz.schmuckShop.data.DataHandler;
import ch.bzz.schmuckShop.model.Customer;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

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
    public Response listCustomers(
            @CookieParam("userRole") String userRole
    ) {
        List<Customer> customerList = null;
        int httpStatus;
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            httpStatus = 200;
            customerList = DataHandler.getInstance().readAllCustomers();
        }
        return Response
                .status(httpStatus)
                .entity(customerList)
                .build();
    }

    /**
     * reads a publisher identified by the uuid
     * @param customerUUID
     * @return publisher
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readPublisher(
            @QueryParam("uuid") String customerUUID,
            @CookieParam("userRole") String userRole
    ) {
        Customer customer = null;
        int httpStatus;
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            httpStatus = 200;
            customer = DataHandler.getInstance().readCustomerByUUID(customerUUID);
        }
        return Response
                .status(httpStatus)
                .entity(customer)
                .build();
    }

    /**
     * deletes a book identified by the uuid
     * @param customerUUID
     * @return book
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteCustomer(
            @QueryParam("uuid") String customerUUID
    ){
        int httpStatus = 200;
        if (!DataHandler.getInstance().deleteCustomer(customerUUID)){
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }


    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertCustomer(
            @Valid @BeanParam Customer customer,
            @CookieParam("userRole") String userRole
    ) {

        int httpStatus;
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            httpStatus = 200;
            customer.setCustomerUUID(UUID.randomUUID().toString());
            DataHandler.getInstance().insertCustomer(customer);
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateBook(
            @Valid @BeanParam Customer customer,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus;
        Customer oldCustomer = DataHandler.getInstance().readCustomerByUUID(customer.getCustomerUUID());

        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            httpStatus = 200;
            if (oldCustomer != null) {
                oldCustomer.setAdresse(customer.getAdresse());
                oldCustomer.setCreditCardInfo(customer.getCreditCardInfo());
                oldCustomer.setEmail(customer.getEmail());
                oldCustomer.setName(customer.getName());
                DataHandler.getInstance().updateCustomer();
            } else {
                httpStatus = 410;
            }
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }
}