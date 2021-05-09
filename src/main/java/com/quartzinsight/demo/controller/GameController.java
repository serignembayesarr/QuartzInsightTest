package com.quartzinsight.demo.controller;

import com.quartzinsight.demo.model.Game;
import com.quartzinsight.demo.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class GameController {

    @Autowired
    GameService service;

    @GetMapping("/api/store/games")
    public ResponseEntity <List<Game>> getGames(){

        List<Game> games =  service.findAllByAvailable(1);
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @GetMapping("/api/store/games/{id}")
    public ResponseEntity<Optional<Game>> getGameById(@PathVariable Long id){

        Optional <Game> game =  service.findByIdAndAvailable(id);
        return new ResponseEntity<>(game,HttpStatus.OK);

    }


    @PostMapping(value = "/api/store/games")
    public ResponseEntity<Void> ajouterProduit(@RequestBody Game game) {
        Game save = service.save(game);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/api/store/games/{gameId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long gameId){
        Optional<Game> game = service.findByIdAndAvailable(gameId);
        if(game.isPresent()){
            game.get().setAvailable(0);
            service.save(game.get());
        }
        return new ResponseEntity(HttpStatus.OK);

    }

    @GetMapping("/api/users/{userId}/games")
    public ResponseEntity<List<Game>> getGameByUserId(@PathVariable Long userId){
        List<Game> games =  service.findByUsers_Id(userId);
        return new ResponseEntity<List<Game>>(games,HttpStatus.OK);
    }

}
