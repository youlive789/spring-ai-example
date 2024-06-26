package com.example.chatbot.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.chatbot.model.entity.Writer;

@Repository
public interface WriterRepository extends CrudRepository<Writer, Long> {
    
}
