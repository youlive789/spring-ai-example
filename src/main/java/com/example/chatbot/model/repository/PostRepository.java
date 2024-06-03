package com.example.chatbot.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.chatbot.model.entity.Post;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
    Post findFirstByUserIdOrderByIdDesc(Long userId);
}
