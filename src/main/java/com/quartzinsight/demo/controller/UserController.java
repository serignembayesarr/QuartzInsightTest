package com.quartzinsight.demo.controller;

import com.quartzinsight.demo.converter.Converter;
import com.quartzinsight.demo.dto.GameDto;
import com.quartzinsight.demo.dto.UserDto;
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
    Converter converter;


    @GetMapping("/api/users")
    private ResponseEntity<List<UserDto>> getUsers(){

        List<UserDto> users =  userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping(value = "/api/users")
    public ResponseEntity<Void> addUser(@RequestBody UserDto userDto) {
        userService.save(userDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        UserDto userDto = userService.findById(id);
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }



    @PostMapping(value = "/api/users/{userId}/games")
    public ResponseEntity<Void> addGameToUser(@PathVariable Long userId, @RequestBody GameDto gameDto) {

        UserDto userDto = userService.findById(userId);
        userDto.getGameIds().add(gameDto.getId().intValue());
        userService.save(userDto);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PostMapping(value = "/api/users/{userId}/friends")
    public ResponseEntity addFriendToUser(@PathVariable Long userId, @RequestBody UserDto friendDto) {

        UserDto userDto = userService.findById(userId);
        UserDto friendToadd = userService.findById(friendDto.getId());
        if(friendDto.hashCode() == friendToadd.hashCode() && friendDto.equals(friendToadd)){
            userDto.getFriendsId().add(friendDto.getId());
            friendDto.getFriendsId().add(userId);
            userService.save(userDto);
            userService.save(friendDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong information about friend");

    }

    @DeleteMapping(value = "/api/users/{userId}/games/{gameId}")
    public ResponseEntity<Void> deleteGameFromUser(@PathVariable Long userId, @PathVariable Long gameId) {

        UserDto userDto = userService.findById(userId);
        userDto.getGameIds().remove(gameId.intValue());
        userService.save(userDto);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping(value = "/api/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {

        UserDto userDto = userService.findById(userId);
        userService.deleteById(userId);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping(value = "/api/users/{userId}/friends/{friendId}")
    public ResponseEntity<Void> deleteFriendFromUser(@PathVariable Long userId, @PathVariable Long friendId) {

        UserDto userDto = userService.findById(userId);
        UserDto friend = userService.findById(friendId);
        userDto.getFriendsId().remove(friendId);
        friend.getFriendsId().remove(userId);
        userDto.getFriendsId().remove(friendId);
        userService.save(userDto);
        userService.save(friend);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/api/users/{userId}/friends")
    public ResponseEntity<List<UserDto>> getFriendsById(@PathVariable Long userId){

        List<UserDto> users =  userService.findByFriends_Id(userId);
        return  new ResponseEntity<>(users,HttpStatus.OK);
    }


}