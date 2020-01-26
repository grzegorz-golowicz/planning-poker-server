package pl.icwt.dtos;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PlayerDTO {
    private UUID id;
    private String name;
    private LocalDateTime joinedAt;
    private LocalDateTime lastActivityAt;
}
