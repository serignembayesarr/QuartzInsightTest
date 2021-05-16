package com.quartzinsight.demo.controller;

import com.quartzinsight.demo.converter.Converter;
import com.quartzinsight.demo.dto.GameDto;
import com.quartzinsight.demo.dto.UserDto;
import com.quartzinsight.demo.model.Game;
import com.quartzinsight.demo.service.GameService;
import com.quartzinsight.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class GameController {

  @Autowired
  GameService service;

  @Autowired
  Converter converter;

  @Autowired
  UserService userService;

  @GetMapping("/api/store/games")
  public ResponseEntity <List<GameDto>> getGames(){

    List<GameDto> games =  service.findAllByAvailable();
    log.info("Request : findAll  games ");
    return new ResponseEntity<>(games, HttpStatus.OK);
  }

  @GetMapping("/api/store/games/{id}")
  public ResponseEntity<GameDto> getGameById(@PathVariable Long id){

    GameDto gameDto  =  service.findByIdAndAvailable(id);
    return new ResponseEntity<>(gameDto,HttpStatus.OK);

  }


  @PostMapping(value = "/api/store/games")
  public ResponseEntity<Void> addGame(@RequestBody GameDto gameDto) {
    service.save(gameDto);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @DeleteMapping("/api/store/games/{gameId}")
  public ResponseEntity<Void> deleteById(@PathVariable Long gameId){
    service.deleteById(gameId);
    return new ResponseEntity(HttpStatus.OK);

  }

  @GetMapping("/api/users/{userId}/games")
  public ResponseEntity<List<GameDto>> getGameByUserId(@PathVariable Long userId){
    UserDto userDto = userService.findById(userId);
    List<GameDto> games =  service.findByUsers_Id(userId);
    return new ResponseEntity<>(games,HttpStatus.OK);
  }



}