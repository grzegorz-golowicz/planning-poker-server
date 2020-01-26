package pl.icwt.model;

import lombok.Getter;

@Getter
public class Card {

    private CardValueEnum cardValueEnum;

    private final String id;

    private final String label;

    public Card(CardValueEnum cardValueEnum) {
        this.cardValueEnum = cardValueEnum;
        this.id = cardValueEnum.name();
        this.label = cardValueEnum.label;
    }
}
