package pl.icwt.resources;

import pl.icwt.dtos.CardDTO;
import pl.icwt.resources.builders.DeckBuilder;
import pl.icwt.services.DeckService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/deck")
@Produces(MediaType.APPLICATION_JSON)
public class DeckResource {

    private final DeckService deckService;
    private final DeckBuilder deckBuilder;

    @Inject
    public DeckResource(DeckService deckService, DeckBuilder deckBuilder) {
        this.deckService = deckService;
        this.deckBuilder = deckBuilder;
    }

    @GET
    public List<CardDTO> getDeck() {

        return deckBuilder.toDTOList(deckService.getDeck());
    }
}
