package com.quartzinsight.demo.service;

import com.quartzinsight.demo.model.User;
import com.quartzinsight.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> findAll(){

        return (List<User>) userRepository.findAll();
    }

    public User save(User user){
        return userRepository.save(user);
    }

    public Optional<User> findById (Long id){
        return userRepository.findById(id);
    }

    public void deleteById (Long id){
        userRepository.deleteById(id);
    }

    public List<User> findByFriends_Id(Long id){
        return userRepository.findByFriendsIdContaining(id);
    }
}
