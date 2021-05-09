package com.quartzinsight.demo.controller;

import com.quartzinsight.demo.model.Game;
import com.quartzinsight.demo.model.User;
import com.quartzinsight.demo.service.GameService;
import com.quartzinsight.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    GameService gameService;


    @GetMapping("/api/users")
    public ResponseEntity<List<User>> getGames() {

        List<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping(value = "/api/users")
    public ResponseEntity<Void> addUser(@RequestBody User user) {

        userService.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @PostMapping(value = "/api/users/{userId}/games")
    public ResponseEntity<Void> addGameToUser(@PathVariable Long userId, @RequestBody Game game) {
        Optional<User> user = userService.findById(userId);
        if (user.isPresent()) {
            user.get().getGameIds().add(game.getId().intValue());
            userService.save(user.get());
        }
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PostMapping(value = "/api/users/{userId}/friends")
    public ResponseEntity<Void> addFriendToUser(@PathVariable Long userId, @RequestBody User friend) {
        Optional<User> user = userService.findById(userId);
        if (user.isPresent()) {
            user.get().getFriendsId().add(friend.getId());
            friend.getFriendsId().add(userId);
            userService.save(user.get());
            userService.save(friend);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/api/users/{userId}/games/{gameId}")
    public ResponseEntity<Void> deleteGameFromUser(@PathVariable Long userId, @PathVariable Long gameId) {
        Optional<User> user = userService.findById(userId);
        if (user.isPresent()) {
            user.get().getGameIds().remove(gameId.intValue());
            userService.save(user.get());
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping(value = "/api/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        Optional<User> user = userService.findById(userId);
        if (user.isPresent()) {
            userService.deleteById(userId);
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping(value = "/api/users/{userId}/friends/{friendId}")
    public ResponseEntity<Void> deleteFriendFromUser(@PathVariable Long userId, @PathVariable Long friendId) {
        Optional<User> user = userService.findById(userId);
        if (user.isPresent()) {
            user.get().getFriendsId().remove(friendId.intValue());
            userService.save(user.get());
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/api/users/{userId}/friends")
    public ResponseEntity<List<User>> getFriendsById(@PathVariable Long userId) {

        List<User> users = userService.findByFriends_Id(userId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}