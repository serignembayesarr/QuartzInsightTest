package com.quartzinsight.demo.service;

import com.quartzinsight.demo.converter.Converter;
import com.quartzinsight.demo.dto.GameDto;
import com.quartzinsight.demo.exception.GameNotFoundException;
import com.quartzinsight.demo.model.Game;
import com.quartzinsight.demo.repository.GameRepository;
import com.quartzinsight.demo.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameService {

    @Autowired
    GameRepository gameRepository;
    @Autowired
    Converter converter;

    public List<GameDto> findAllByAvailable(){
        return  gameRepository.findAllByAvailable(ServiceUtils.AVAILABLE)
                .stream()
                .map(converter::convertToGameDto)
                .collect(Collectors.toList());
    }

    public GameDto save (GameDto gameDto){
        Game game = converter.convertToGameEntity(gameDto);
        return converter.convertToGameDto( gameRepository.save(game));
    }

    public void deleteById (Long id){
        Game game = gameRepository.findById(id)
                .orElseThrow(()->new GameNotFoundException("Game with id : "+id+" does not exist"));
        game.setAvailable(ServiceUtils.UNAVAILABLE);
        gameRepository.save(game);
    }

    public GameDto findByIdAndAvailable(Long id){
        Game game = gameRepository.findByIdAndAvailable(id,ServiceUtils.AVAILABLE)
                .orElseThrow(()->new GameNotFoundException("Game with id : "+id+" does not exist"));
        return converter.convertToGameDto(game);
    }

    public GameDto findById(Long id){
        Game game = gameRepository.findById(id)
                .orElseThrow(()->new GameNotFoundException("Game with id : "+id+" does not exist"));
        return converter.convertToGameDto(game);
    }


    public List<GameDto> findByUsers_Id(Long id){
        return gameRepository.findByUsers_Id(id)
                .stream()
                .map(converter::convertToGameDto)
                .collect(Collectors.toList());
    }







}