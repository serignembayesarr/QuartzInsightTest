package com.quartzinsight.demo.service;

import com.quartzinsight.demo.model.Game;
import com.quartzinsight.demo.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    @Autowired
    GameRepository gameRepository;

    public List<Game> findAllByAvailable(Integer id){
        return (List<Game>) gameRepository.findAllByAvailable(id);
    }

    public Game save (Game game){
        return gameRepository.save(game);
    }

    public void deleteById (Long id){
        gameRepository.deleteById(id);
    }

    public Optional<Game> findByIdAndAvailable(Long id){
        return gameRepository.findByIdAndAvailable(id,1);
    }

    public Optional<Game> findById(Long id){
        return gameRepository.findById(id);
    }


    public List<Game> findByUsers_Id(Long id){
        return gameRepository.findByUsers_Id(id);
    }
}
