package ch.bzz.schmuckShop.service;


import ch.bzz.schmuckShop.data.DataHandler;
import ch.bzz.schmuckShop.model.Order;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

/**
 * services for reading, adding, changing and deleting books
 */
@Path("order")
public class OrderService {
    /**
     * reads a list of all books
     * @return  books as JSON
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listOrders(
            @CookieParam("userRole") String userRole
    ) {

        List<Order> orderList = null;
        int httpStatus;
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            httpStatus = 200;
            orderList = DataHandler.getInstance().readAllOrders();
        }
        return Response
                .status(httpStatus)
                .entity(orderList)
                .build();
    }
    /**
     * reads a book identified by the uuid
     * @param orderUUID
     * @return book
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readBook(
            @QueryParam("uuid") String orderUUID,
            @CookieParam("userRole") String userRole
    ) {
        Order order = null;
        int httpStatus;
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            httpStatus = 200;
            order = DataHandler.getInstance().readOrderByUUID(orderUUID);
        }
        return Response
                .status(httpStatus)
                .entity(order)
                .build();
    }

    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertOrder(
            @Valid @BeanParam Order order,
            @NotEmpty
            //@Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("publisherUUID") String orderUUID,
            @CookieParam("userRole") String userRole

    ) {

        int httpStatus;
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            httpStatus = 200;
            order.setOrderUUID(UUID.randomUUID().toString());
            DataHandler.getInstance().insertOrder(order);
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
            @Valid @BeanParam Order order,
            @NotEmpty
            //@Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("orderUUID") String orderUUID,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus;
        Order oldOrder = DataHandler.getInstance().readOrderByUUID(order.getOrderUUID());

        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            httpStatus = 200;
            if (oldOrder != null) {
                oldOrder.setDateCreated(order.getDateCreated());
                oldOrder.setDateShipped(order.getDateShipped());
                oldOrder.setStatus(order.getStatus());
                oldOrder.setOrderUUID(order.getOrderUUID());

                DataHandler.getInstance().updateOrder();
            } else {
                httpStatus = 410;
            }
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }



    /**
     * deletes a book identified by the uuid
     * @param orderUUID
     * @return book
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteBook(
            @QueryParam("uuid") String orderUUID,
            @CookieParam("userRole") String userRole
    ){

        int httpStatus;
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            httpStatus = 200;
            if (!DataHandler.getInstance().deleteOrder(orderUUID)){
                httpStatus = 410;
            }
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }
}