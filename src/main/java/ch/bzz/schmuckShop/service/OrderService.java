package ch.bzz.schmuckShop.service;


import ch.bzz.schmuckShop.data.DataHandler;
import ch.bzz.schmuckShop.model.Item;
import ch.bzz.schmuckShop.model.Order;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

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
    public Response listOrders() {
        List<Order> orderList = DataHandler.getInstance().readAllOrders();
        return Response
                .status(200)
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
    public Response readBook(@QueryParam("uuid") String orderUUID) {
        int httpStatus = 200;
        Order order = DataHandler.getInstance().readOrderByUUID(orderUUID);
        if (order == null) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity(order)
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
            @QueryParam("uuid") String bookUUID
    ){
        int httpStatus = 200;
        if (!DataHandler.getInstance().deleteOrder(bookUUID)){
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    } @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertOrder(
            @Valid @BeanParam Order order,
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("publisherUUID") String publisherUUID
    ) {

        order.setPublisherUUID(publisherUUID);

        DataHandler.insertOrder(order);
        return Response
                .status(200)
                .entity("")
                .build();
    }
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateBook(
            @Valid @BeanParam Order order,
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("orderUUID") String publisherUUID
    ) {
        int httpStatus = 200;
        Order oldOrder = DataHandler.readOrderByUUID(order.getOrderUUID());
        if (oldOrder != null) {
            oldOrder.setDateCreated(order.getDateCreated());
            oldOrder.setDateShipped(order.getDateShipped());
            oldOrder.setStatus(order.getStatus());
            oldOrder.setOrderUUID(order.getOrderUUID());

            DataHandler.updateOrder();
        } else {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }



}
