package com.quartzinsight.demo.controller;


import com.quartzinsight.demo.dto.GameDto;
import com.quartzinsight.demo.dto.UserDto;
import com.quartzinsight.demo.exception.GameNotFoundException;
import com.quartzinsight.demo.exception.UserNotFoundException;
import com.quartzinsight.demo.service.GameService;
import com.quartzinsight.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class GameControllerTest {


    GameDto gameDto = new GameDto().builder()
            .id(1L)
            .title("fifa")
            .url("fifatest.com")
            .build();
    GameDto gameDto1 = new GameDto().builder()
            .id(1L)
            .title("nba")
            .url("nba.com")
            .build();


    List<GameDto> games = Arrays.asList(gameDto,gameDto1);


    @InjectMocks
    GameController gameController;

    @Mock
    GameService gameService;
    @Mock
    UserService userService;



    @Test
    void getGames() {
        Mockito.when(gameService.findAllByAvailable()).thenReturn(games);
        ResponseEntity<List<GameDto>>returnValues = gameController.getGames();
        assertAll("Should return the same list",
                () -> assertEquals(returnValues.getBody().get(0).getId(),gameDto.getId()),
                () -> assertEquals(returnValues.getBody().get(0).getTitle(),gameDto.getTitle()),
                () -> assertEquals(returnValues.getBody().get(0).getUrl(),gameDto.getUrl()),
                () -> assertEquals(returnValues.getStatusCode(),HttpStatus.OK)
        );
    }

    @Test
    void getGames1() {
        Mockito.when(gameService.findAllByAvailable()).thenReturn(games);
        ResponseEntity<List<GameDto>>returnValues = gameController.getGames();
        assertAll("Should return the same list",
                () -> assertEquals(returnValues.getBody().get(1).getId(),gameDto1.getId()),
                () -> assertEquals(returnValues.getBody().get(1).getTitle(),gameDto1.getTitle()),
                () -> assertEquals(returnValues.getBody().get(1).getUrl(),gameDto1.getUrl()),
                () -> assertEquals(returnValues.getStatusCode(),HttpStatus.OK)
        );
    }

    @Test
    void getGameById() {
        Mockito.when(gameService.findByIdAndAvailable(1L)).thenReturn(gameDto);
        ResponseEntity<GameDto>returnValues = gameController.getGameById(1L);
        assertAll("Should return fifa",
                () -> assertEquals(returnValues.getBody().getId(),gameDto.getId()),
                () -> assertEquals(returnValues.getBody().getTitle(),gameDto.getTitle()),
                () -> assertEquals(returnValues.getBody().getUrl(),gameDto.getUrl()),
                () -> assertEquals(returnValues.getStatusCode(),HttpStatus.OK)
        );

    }

    @Test
    void getGameByIdThrowException() {
        Mockito.when(gameService.findByIdAndAvailable(anyLong())).thenThrow(new GameNotFoundException("Game does not exist"));

        assertThrows(GameNotFoundException.class,()-> gameController.getGameById(10L));
    }

    @Test
    void addGame() {
        Mockito.when(gameService.save(gameDto)).thenReturn(gameDto);
        ResponseEntity<Void>returnValues = gameController.addGame(gameDto);
        assertEquals(returnValues.getStatusCode(),HttpStatus.CREATED);

    }



    @Test
    void getGameByUserId() {
        Mockito.when(userService.findById(anyLong())).thenReturn(new UserDto());
        Mockito.when(gameService.findByUsers_Id(1L)).thenReturn(games);
        ResponseEntity<List<GameDto>>returnValues = gameController.getGameByUserId(1L);
        assertAll("Should return the same list",
                () -> assertEquals(returnValues.getBody().get(1).getId(),gameDto1.getId()),
                () -> assertEquals(returnValues.getBody().get(1).getTitle(),gameDto1.getTitle()),
                () -> assertEquals(returnValues.getBody().get(1).getUrl(),gameDto1.getUrl()),
                () -> assertEquals(returnValues.getStatusCode(),HttpStatus.OK)
        );
    }

    @Test
    void getGameByUserIdShouldReturnExeption() {
        Mockito.when(userService.findById(anyLong())).thenThrow(new UserNotFoundException("User does not exist"));
        assertThrows(UserNotFoundException.class,()-> gameController.getGameByUserId(10L));
    }
}