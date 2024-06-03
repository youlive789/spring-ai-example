package com.example.chatbot.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.chatbot.model.entity.User;

@Repository
interface UserRepository extends CrudRepository<User, Long> {
    
}
