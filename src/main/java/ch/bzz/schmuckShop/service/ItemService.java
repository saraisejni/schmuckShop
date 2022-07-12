package ch.bzz.schmuckShop.service;


import ch.bzz.schmuckShop.data.DataHandler;
import ch.bzz.schmuckShop.model.Item;
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
@Path("item")
public class ItemService {

    /**
     * reads a list of all books
     * @return  books as JSON
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listItems(
            @CookieParam("userRole") String userRole
    ) {
        List<Item> itemList = null;
        int httpStatus;
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            httpStatus = 200;
            itemList = DataHandler.getInstance().readAllItems();
        }
        return Response
                .status(httpStatus)
                .entity(itemList)
                .build();
    }

    /**
     * reads a book identified by the uuid
     * @param itemUUID
     * @return book
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readItem(
            @QueryParam("uuid") String itemUUID,
            @CookieParam("userRole") String userRole
    ) {
        Item item = null;
        int httpStatus;
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            httpStatus = 200;
            item = DataHandler.getInstance().readItemByUUID(itemUUID);
        }
        return Response
                .status(httpStatus)
                .entity(item)
                .build();
    }

    /**
     * deletes a book identified by the uuid
     * @param itemUUID
     * @return book
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response delteItem(
            @QueryParam("uuid") String itemUUID,
            @CookieParam("userRole") String userRole
    ){
        int httpStatus;
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            httpStatus = 200;
            if (!DataHandler.getInstance().deleteItem(itemUUID)) {
                httpStatus = 410;
            }
        }

        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }


    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertItem(
            @Valid @BeanParam Item item,
            @NotEmpty
            //@Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("itemUUID") String itemUUID,
            @CookieParam("userRole") String userRole
    ) {

        int httpStatus;
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            httpStatus = 200;
            item.setItemUUID(UUID.randomUUID().toString());
            DataHandler.getInstance().insertItem(item);
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
            @Valid @BeanParam Item item,
            @NotEmpty
            //@Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("itemUUID") String itemUUID,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus;
        Item oldItem = DataHandler.getInstance().readItemByUUID(itemUUID);

        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            httpStatus = 200;
            if (oldItem != null) {
                oldItem.setDescription(item.getDescription());
                oldItem.setName(item.getName());
                oldItem.setPrice(item.getPrice());

                DataHandler.getInstance().updateItem();
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