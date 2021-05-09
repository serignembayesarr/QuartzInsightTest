package com.quartzinsight.demo.repository;

import com.quartzinsight.demo.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Query("select distinct u from User u join fetch u.friendsId fid where fid = :id")
    public List<User> findByFriendsIdContaining(Long id);
}
