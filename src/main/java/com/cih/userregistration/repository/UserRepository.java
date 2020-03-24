package com.cih.userregistration.repository;

import com.cih.userregistration.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByLastName(String lastName);

    @Query(value = "select u from User u")
    List<User> findWithPageable(Pageable pageable);
}
