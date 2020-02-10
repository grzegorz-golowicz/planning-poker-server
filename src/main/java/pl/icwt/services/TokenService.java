package pl.icwt.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.quarkus.security.ForbiddenException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import pl.icwt.model.Player;
import pl.icwt.model.Room;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class TokenService {

    public final static String CLAIM_SUB = "sub";
    public final static String CLAIM_PLAYER_ID = "player_id";
    public final static String CLAIM_ROOM_ID = "room_id";

    @ConfigProperty(name = "token.secret")
    String secret;

    @ConfigProperty(name = "token.issuer")
    String issuer;

    @Inject
    RoomService roomService;

    private Algorithm algorithm;

    private JWTVerifier verifier;

    @PostConstruct
    public void postConstruct() {
        algorithm = Algorithm.HMAC256(secret);
        verifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .build();
    }

    public String getToken(final Player player, final Room room) {

        String token = JWT.create()
                .withIssuer(issuer)
                .withClaim(CLAIM_SUB, player.getName())
                .withClaim(CLAIM_ROOM_ID, room.getRoomId().toString())
                .withClaim(CLAIM_PLAYER_ID, player.getId().toString())
                .sign(algorithm);

        return token;
    }

    public Player getPlayerByToken(String token) {
        Map<String, Claim> claimMap;

        try {
            claimMap = decodeToken(token);
        } catch (JWTVerificationException ex) {
            throw new ForbiddenException("Invalid token!");
        }

        if (!claimMap.containsKey(CLAIM_PLAYER_ID) || !claimMap.containsKey(CLAIM_ROOM_ID)) {
            throw new ForbiddenException("Invalid token! Wrong player/room.");
        }

        UUID playerId;
        UUID roomId;
        try {
            playerId = UUID.fromString(claimMap.get(CLAIM_PLAYER_ID).asString());
            roomId = UUID.fromString(claimMap.get(CLAIM_ROOM_ID).asString());
        } catch (IllegalArgumentException ex) {
            throw new ForbiddenException("Invalid token! Wrong player/room.", ex);
        }

        final Room room = roomService.getRoom(roomId);

        return room.getPlayer(playerId);
    }

    private Map<String, Claim> decodeToken(String token) {
        DecodedJWT jwt = verifier.verify(token);

        return jwt.getClaims();
    }
}
