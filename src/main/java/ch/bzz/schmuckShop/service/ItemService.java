package ch.bzz.schmuckShop.service;


import ch.bzz.schmuckShop.data.DataHandler;
import ch.bzz.schmuckShop.model.Item;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

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
    public Response listBooks() {
        List<Item> bookList = DataHandler.getInstance().readAllItems();
        return Response
                .status(200)
                .entity(bookList)
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
    public Response readBook(@QueryParam("uuid") String itemUUID) {
        int httpStatus = 200;
        Item item = DataHandler.getInstance().readItemByUUID(itemUUID);
        if (item == null) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity(item)
                .build();
    }

}