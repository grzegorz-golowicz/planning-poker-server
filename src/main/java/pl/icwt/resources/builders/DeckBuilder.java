package pl.icwt.resources.builders;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import pl.icwt.dtos.CardDTO;
import pl.icwt.model.Card;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.lang.reflect.Type;
import java.util.List;

@ApplicationScoped
public class DeckBuilder {

    private ModelMapper mapper;

    @Inject
    public DeckBuilder(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public List<CardDTO> toDTOList(List<Card> cardList) {
        Type type = new TypeToken<List<CardDTO>>() {}.getType();

        return mapper.map(cardList, type);
    }
}
