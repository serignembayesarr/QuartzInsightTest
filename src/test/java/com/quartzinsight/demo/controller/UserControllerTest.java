package com.quartzinsight.demo.controller;

import com.quartzinsight.demo.dto.GameDto;
import com.quartzinsight.demo.dto.UserDto;
import com.quartzinsight.demo.exception.GameNotFoundException;
import com.quartzinsight.demo.exception.UserNotFoundException;
import com.quartzinsight.demo.model.User;
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
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class UserControllerTest {


    @InjectMocks
    UserController userController;


    @Mock
    UserService userService;
    @Mock
    GameService gameService;

    GameDto gameDto = new GameDto().builder()
            .id(1L)
            .title("fifa")
            .url("fifatest.com")
            .build();
    GameDto gameDto1 = new GameDto().builder()
            .id(2L)
            .title("nba")
            .url("nba.com")
            .build();


    UserDto userDto = UserDto.builder()
            .username("Tamsir")
            .email("tamsir@gmail.com")
            .id(1L)
            .gameIds(new HashSet<>())
            .friendsId(new HashSet<>())
            .build();

    UserDto userDto1 = UserDto.builder()
            .username("Sall")
            .email("sall@gmail.com")
            .id(2L)
            .gameIds(new HashSet<>())
            .friendsId(new HashSet<>())
            .build();

    User user1 = User.builder()
            .username("serigne")
            .email("serigne@gmail.com")
            .id(2L)
            .gameIds(new HashSet<>())
            .friendsId(new HashSet<>())
            .build();



    User user = User.builder()
            .username("sarr")
            .email("sarr@gmail.com")
            .id(1L)
            .gameIds(new HashSet<>())
            .friendsId(new HashSet<>())
            .build();



    List<UserDto> userDtoList = Arrays.asList(userDto,userDto1);

    @Test
    void addUser() {
        Mockito.when(userService.save(userDto)).thenReturn(new User());
        ResponseEntity<Void> returnValues = userController.addUser(userDto);
        assertAll("Should return OK",
                () -> assertEquals(returnValues.getStatusCode(), HttpStatus.CREATED)
        );
    }

    @Test
    void getUserById() {
        Mockito.when(userService.findAll()).thenReturn(userDtoList);
        ResponseEntity<List<UserDto>> returnValue = userController.getUsers();
        assertAll(
                ()->assertEquals(returnValue.getBody().get(0).getId(),1L),
                ()->assertEquals(returnValue.getBody().get(0).getEmail(),"tamsir@gmail.com"),
                ()->assertEquals(returnValue.getBody().get(0).getUsername(),"Tamsir"),
                ()->assertEquals(returnValue.getStatusCode(), HttpStatus.OK)

        );
    }

    @Test
    void addGameToUser() {
        Mockito.when(userService.findById(anyLong())).thenReturn(userDto);
        Mockito.when(gameService.findByIdAndAvailable(anyLong())).thenReturn(gameDto);
        Mockito.when(userService.save(any())).thenReturn(user);
        ResponseEntity returnValue = userController.addGameToUser(1L,gameDto);
        assertEquals(returnValue.getStatusCode(),HttpStatus.OK);
    }

    @Test
    void addGameToUserShouldReturnBadRequest() {
        Mockito.when(userService.findById(anyLong())).thenReturn(userDto);
        Mockito.when(gameService.findByIdAndAvailable(anyLong())).thenReturn(gameDto1);
        ResponseEntity returnValue = userController.addGameToUser(1L,gameDto);
        assertEquals(returnValue.getStatusCode(),HttpStatus.BAD_REQUEST);
    }

    @Test
    void addGameToUserShouldThrowUserNotfoundException() {
        Mockito.when(userService.findById(anyLong())).thenThrow(new UserNotFoundException("User does not exist"));
        assertThrows(UserNotFoundException.class,()-> userController.addGameToUser(10L,gameDto));
    }

    @Test
    void addGameToUserShouldThrowGameNotfoundException() {
        Mockito.when(userService.findById(anyLong())).thenReturn(userDto);
        Mockito.when(gameService.findByIdAndAvailable(anyLong())).thenThrow(new GameNotFoundException("Game does not exist"));
        assertThrows(GameNotFoundException.class,()-> userController.addGameToUser(1L,gameDto));
    }

    @Test
    void addFriendToUser() {
        Mockito.when(userService.findById(1L)).thenReturn(userDto);
        Mockito.when(userService.findById(2L)).thenReturn(userDto1);
        Mockito.when(userService.save(userDto)).thenReturn((user));
        Mockito.when(userService.save(userDto1)).thenReturn(user);
        ResponseEntity returnValue = userController.addFriendToUser(1L,userDto1);
        assertAll(
                ()->assertEquals(returnValue.getStatusCode(),HttpStatus.OK)
        );

    }

    @Test
    void getFriendsById() {
        Mockito.when(userService.findByFriends_Id(anyLong())).thenReturn(Arrays.asList(userDto));
        ResponseEntity<List<UserDto>> returnValue = userController.getFriendsById(2L);
        assertEquals(returnValue.getStatusCode(),HttpStatus.OK);

    }

    @Test
    void getFriendsByIdShouldReturnUserNotFoundException() {
        Mockito.when(userService.findByFriends_Id(anyLong())).thenThrow(new UserNotFoundException("User does not exist"));
        assertThrows(UserNotFoundException.class,()-> userController.getFriendsById(3L));

    }
}