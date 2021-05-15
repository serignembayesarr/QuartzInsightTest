package com.quartzinsight.demo.converter;

import com.quartzinsight.demo.dto.GameDto;
import com.quartzinsight.demo.dto.UserDto;
import com.quartzinsight.demo.model.Game;
import  com.quartzinsight.demo.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;


public class Converter {

    @Autowired
    ModelMapper modelMapper;

    public  UserDto convertTouserDto (User user){
        UserDto userDto = modelMapper.map(user,UserDto.class);
        return userDto;
    }
    public User convertToUserEntity (UserDto userDto){
        User user = modelMapper.map(userDto,User.class);
        return user;
    }

    public GameDto convertToGameDto(Game game){
        GameDto gameDto = modelMapper.map(game,GameDto.class);
        return gameDto;
    }

    public Game convertToGameEntity (GameDto gameDto){
        Game game = modelMapper.map(gameDto,Game.class);
        return game;
    }
}