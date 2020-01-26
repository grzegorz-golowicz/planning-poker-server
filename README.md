# planning-poker-server 

Simple planing poker server
This project is test drive of  Quarkus, the Supersonic Subatomic Java Framework.

## Assumptions
* as simple as possible
* no database - all operations in memory
* player exist only in one room - there is no profile - you create player while joining the room
* players can see the cards when the last person (excluding the owner) places the card

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./gradlew quarkusDev
```

## Endpoints
This server expose REST(ish) API.

### Get all possible cards in deck 
`GET /deck`


### Create new room
`POST /rooms`
```json
{
  "name": "room name",
  "description": "room description",
  "password": "passwordToJoinRoom"
}
```

### Get room details (including current game/deal) 
`GET /rooms/[room.Id]`

### Remove room 
`GET /rooms/[room.Id]`

### Join room
`PUT /rooms/[room.Id]/player`
{
  "name": "player name",
  "password": "passwordToJoinRoom"
}

### Remove player 
`DELETE /rooms/[room.Id]/player/[player.id]`

* player can remove himself - leave room
* owner can remove any player
* if owner leave, room will be deleted

### Put a card
`PUT /rooms/[room.Id]/player/[player.id]/deal`
```json
{
  "card": {
    "id": "VAL_1"
  }
}
```

### Reset deal (only owner of the room can do this) ??
`DELETE /rooms/[room.Id]/deal`