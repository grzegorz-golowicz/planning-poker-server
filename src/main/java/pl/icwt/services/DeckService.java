package pl.icwt.services;

import pl.icwt.model.Card;
import pl.icwt.model.CardValueEnum;

import javax.enterprise.context.ApplicationScoped;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class DeckService {

    public List<Card> getDeck() {
        return Arrays.stream(CardValueEnum.values()).map(cardValueEnum -> new Card(cardValueEnum)).collect(Collectors.toList());
    }
}
