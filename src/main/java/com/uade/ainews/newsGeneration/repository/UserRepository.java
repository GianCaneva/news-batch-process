package com.uade.ainews.newsGeneration.repository;


import com.uade.ainews.newsGeneration.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}


