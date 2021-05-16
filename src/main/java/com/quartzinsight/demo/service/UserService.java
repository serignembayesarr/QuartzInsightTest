package com.quartzinsight.demo.service;

import com.quartzinsight.demo.converter.Converter;
import com.quartzinsight.demo.dto.UserDto;
import com.quartzinsight.demo.exception.UserNotFoundException;
import com.quartzinsight.demo.model.User;
import com.quartzinsight.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    Converter converter;

    public List<UserDto> findAll(){

        return ((List<User>) userRepository.findAll())
                .stream()
                .map(converter::convertTouserDto)
                .collect(Collectors.toList());
    }

    public User save(UserDto userDto){

        User user = converter.convertToUserEntity(userDto);
        return userRepository.save(user);
    }

    public UserDto findById (Long id){

        User user =  userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException("The user with id : "+id+" does not exist"));
        return converter.convertTouserDto(user);
    }

    public void deleteById (Long id){
        User user =  userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException("The user with id : "+id+" does not exist"));
        userRepository.deleteById(user.getId());
    }

    public List<UserDto> findByFriends_Id(Long id){

        return userRepository.findByFriendsIdContaining(id)
                .stream()
                .map(converter::convertTouserDto)
                .collect(Collectors.toList());
    }




}