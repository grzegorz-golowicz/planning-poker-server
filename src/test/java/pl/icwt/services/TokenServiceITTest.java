package pl.icwt.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.quarkus.security.ForbiddenException;
import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;
import pl.icwt.model.Player;
import pl.icwt.model.Room;

import javax.inject.Inject;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class TokenServiceITTest {

    @Inject
    private TokenService tokenService;

    @Inject
    private RoomService roomService;

    @ConfigProperty(name = "token.secret")
    private String secret;

    @ConfigProperty(name = "token.issuer")
    private String issuer;

    @Test
    void getToken_success() {
        Player player = new Player("TestPlayer");
        Room room = roomService.createRoom(player, "", "","");

        String token = tokenService.getToken(player, room);
        assertNotNull(token);

        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .build(); //Reusable verifier instance
        DecodedJWT jwt = verifier.verify(token);

        assertEquals(jwt.getClaim(TokenService.CLAIM_PLAYER_ID).asString(), player.getId().toString());
        assertEquals(jwt.getClaim(TokenService.CLAIM_ROOM_ID).asString(), room.getRoomId().toString());
    }

    @Test
    void getPlayerByToken_invalidToken() {

        assertThrows(ForbiddenException.class, () -> {
            tokenService.getPlayerByToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.ell1VtdlyQP3-yYwr07ecG34mUe27gc-h8Y-yoKufo0");
        });
    }

    @Test
    void getPlayerByToken_validToken() {

        Player player = new Player("TestPlayer");
        Room room = roomService.createRoom(player, "", "","");

        String token = tokenService.getToken(player, room);
        Player playerResp = tokenService.getPlayerByToken(token);

        assertSame(player, playerResp);
    }

    @Test
    void getPlayerByToken_validTokenDeletedPlayer() {

        Player player = new Player("TestPlayer");
        Room room = roomService.createRoom(player, "", "","");

        String token = tokenService.getToken(player, room);
        room.removePlayer(player);

        assertThrows(NoSuchElementException.class, () -> {
            tokenService.getPlayerByToken(token);
        });
    }
}