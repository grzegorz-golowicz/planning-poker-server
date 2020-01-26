package pl.icwt.model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Player {
    private final UUID id;
    private final String name;
    private LocalDateTime joinedAt;
    private LocalDateTime lastActivityAt;

    public Player(String name) {
        this.name = name;
        this.id = UUID.randomUUID();
        this.joinedAt = LocalDateTime.now();
        this.lastActivityAt = joinedAt;
    }

    public void recordActivity() {
        lastActivityAt = LocalDateTime.now();
    }
}
