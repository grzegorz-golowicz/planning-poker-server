package pl.icwt.model;


import lombok.Getter;

import java.time.LocalDateTime;
import java.util.*;

@Getter
public class Room {

    private UUID roomId;
    private Player owner;
    private String name;
    private String description;
    private String password;
    private List<Player> players = new ArrayList<>();
    private Map<UUID, Card> deal = new HashMap<>();
    private LocalDateTime lastActivityAt;

    public Room(Player owner, String name, String description, String password) {
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.password = password;
        this.roomId = UUID.randomUUID();
        addPlayer(owner);
        recordActivity();
    }

    public void addPlayer(Player player) {
        players.add(player);
        recordActivity();
    }

    public Player getPlayer(UUID playerId) {

        return players.stream()
                .filter(player -> player.getId().equals(playerId))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    public void removePlayer(Player player) {
        if (players.contains(player)) {
            players.remove(player);
            return;
        }

        throw new NoSuchElementException(String.format("Player [%s] not found!", player.getId().toString()));
    }

    public void addCard(Player player, Card card) {
        if (!players.contains(player)) {
            throw new NoSuchElementException(String.format("Player [%s] not found!", player.getId().toString()));
        }

        if (deal.containsKey(player.getId())) {
            throw new IllegalStateException(String.format("The player [%s] has already participated in the deal!", player.getId().toString()));
        }

        deal.put(player.getId(), card);
        recordActivity();
    }

    public void resetDeal() {
        deal = new HashMap<>();
        recordActivity();
    }

    private void recordActivity() {
        lastActivityAt = LocalDateTime.now();
    }
}
