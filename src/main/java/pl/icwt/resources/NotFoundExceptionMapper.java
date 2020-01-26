package pl.icwt.resources;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.NoSuchElementException;

@Provider
public class NotFoundExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<NoSuchElementException> {
    @Override
    public Response toResponse(NoSuchElementException exception) {
        return Response.status(Response.Status.NOT_FOUND).entity(exception).build();
    }
}
