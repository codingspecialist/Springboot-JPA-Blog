package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer>{
	
}
