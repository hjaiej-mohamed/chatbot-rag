package com.ia.chatbot.repositories;

import com.ia.chatbot.models.entities.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiscussionRepository  extends JpaRepository<Discussion,Long> {
    @Query("SELECT d FROM Discussion d WHERE d.user.id=:userId order by d.id desc")
    List<Discussion> findByUserId(Long userId);
}
