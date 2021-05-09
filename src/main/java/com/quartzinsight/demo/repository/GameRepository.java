package com.quartzinsight.demo.repository;

import com.quartzinsight.demo.model.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends CrudRepository<Game,Long> {

    @Override
    public List<Game> findAll();


    public List<Game> findAllByAvailable(Integer available);

    @Override
    public void deleteById (Long id);

    @Override
    public Game save (Game game);

    Optional<Game> findByIdAndAvailable(Long id, Integer available);

    public List<Game> findByUsers_Id(Long id);}
