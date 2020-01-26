package pl.icwt.dtos;


import pl.icwt.model.Card;
import pl.icwt.model.Player;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class RoomDTO {

    private UUID roomId;
    private Player owner;
    private String name;
    private String description;
    private List<Player> players;
    private HashMap<UUID, Card> deal;
    private LocalDateTime lastUpdate;
}
