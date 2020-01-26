package pl.icwt.model;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.assertj.core.data.TemporalUnitLessThanOffset;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RoomTest {

    @Test
    void addPlayer_success() {
        Room room = getSimpleRoom();
        assertThat(room.getPlayers()).hasSize(1);

        Player player = new Player("Player1");
        room.addPlayer(player);
        assertThat(room.getPlayers()).hasSize(2).contains(player);
        assertThat(room.getLastActivityAt()).isCloseTo(LocalDateTime.now(), new TemporalUnitLessThanOffset(3, ChronoUnit.SECONDS));
    }

    @Test
    void getPlayer_success() {
        Room room = getSimpleRoom();
        Player player = new Player("Player1");
        room.addPlayer(player);

        Player returnedPlayer = room.getPlayer(player.getId());
        assertThat(player).isEqualTo(returnedPlayer);
    }

    @Test
    void getPlayer_nonExistingPlayer() {
        Room room = getSimpleRoom();
        assertThrows(NoSuchElementException.class,() -> room.getPlayer(UUID.randomUUID()));
    }

    @Test
    void removePlayer_success() {
        Room room = getSimpleRoom();
        Player player = new Player("Player1");
        room.addPlayer(player);

        room.removePlayer(player);
        assertThat(room.getPlayers()).hasSize(1).doesNotContain(player);
        assertThat(room.getLastActivityAt()).isCloseTo(LocalDateTime.now(), new TemporalUnitLessThanOffset(3, ChronoUnit.SECONDS));
    }

    @Test
    void removePlayer_nonExistingPlayer() {
        Room room = getSimpleRoom();
        Player player = new Player("Player1");
        assertThrows(NoSuchElementException.class,() -> room.removePlayer(player));
    }

    @Test
    void addCard_success() {
        Room room = getSimpleRoom();
        Player player = new Player("Player1");
        room.addPlayer(player);

        assertThat(room.getDeal()).isEmpty();
        Card card = new Card(CardValueEnum.INFINITY);
        room.addCard(player, card);
        assertThat(room.getDeal()).isNotEmpty().containsKey(player.getId());
        assertThat(room.getLastActivityAt()).isCloseTo(LocalDateTime.now(), new TemporalUnitLessThanOffset(3, ChronoUnit.SECONDS));
    }

    @Test
    void addCard_nonExistingPlayer() {
        Room room = getSimpleRoom();
        Player player = new Player("Player1");
        Card card = new Card(CardValueEnum.INFINITY);
        assertThrows(NoSuchElementException.class,() -> room.addCard(player, card));
    }

    @Test
    void resetDeal_success() {
        Room room = getSimpleRoom();
        Player player = new Player("Player1");
        room.addPlayer(player);

        assertThat(room.getDeal()).isEmpty();
        Card card = new Card(CardValueEnum.INFINITY);
        room.addCard(player, card);
        assertThat(room.getDeal()).isNotEmpty();

        room.resetDeal();
        assertThat(room.getDeal()).isEmpty();
    }

    private Room getSimpleRoom() {
        Player owner = new Player("RoomOwner");
        Room room = new Room(owner, "name", "desc", null);
        try {
            FieldUtils.writeField(room, "lastActivityAt", LocalDateTime.now().minusSeconds(30), true);
        } catch (IllegalAccessException ex) {
            fail("Cannot set lastActivityAt");
        }

        return room;
    }

}