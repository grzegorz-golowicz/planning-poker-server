package pl.icwt.services;

import pl.icwt.model.Player;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PlayerService {

    public Player createPlayer(String name) {
        return new Player(name);
    }
}
