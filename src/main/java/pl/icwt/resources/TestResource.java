package pl.icwt.resources;

import pl.icwt.model.Player;
import pl.icwt.model.Room;
import pl.icwt.services.TokenService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
public class TestResource {

    private final TokenService tokenService;

    @Inject
    public TestResource(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GET
    public String getTestToken() {
        Player player = new Player("test");
        Room room = new Room(player, "", "","");

        return tokenService.getToken(player, room);
    }
}
