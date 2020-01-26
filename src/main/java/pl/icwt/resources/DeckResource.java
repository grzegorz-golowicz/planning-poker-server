package pl.icwt.resources;

import pl.icwt.model.Card;
import pl.icwt.services.DeckService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/deck")
public class DeckResource {

    @Inject
    private DeckService deckService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Card> getDeck() {

        return deckService.getDeck();
    }
}
