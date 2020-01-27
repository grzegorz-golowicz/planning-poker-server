package pl.icwt.services;

import pl.icwt.model.Player;
import pl.icwt.model.Room;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

@ApplicationScoped
public class RoomService {
    private static Map<UUID, Room> storage = new HashMap<>();

    private PlayerService playerService;

    @Inject
    public RoomService(PlayerService playerService) {
        this.playerService = playerService;
    }

    public Room createRoom(@NotNull Player owner, String roomName, String roomDesc, String password) {
        Room room = new Room(owner, roomName, roomDesc, password);
        RoomService.storage.put(room.getRoomId(), room);

        return room;
    }

    public Room getRoom(UUID id) {
        if (RoomService.storage.containsKey(id)) {
            return RoomService.storage.get(id);
        }

        throw new NoSuchElementException(String.format("Room [%s] not found!", id.toString()));
    }

    public void deleteRoom(UUID id) {
        if (RoomService.storage.containsKey(id)) {
            RoomService.storage.remove(id);
        }

        throw new NoSuchElementException(String.format("Room [%s] not found!", id.toString()));
    }

    public Room addPlayerToRoom(@NotNull Player player, @NotNull UUID roomId) {
        Room room = getRoom(roomId);
        room.addPlayer(player);

        return room;
    }
}
