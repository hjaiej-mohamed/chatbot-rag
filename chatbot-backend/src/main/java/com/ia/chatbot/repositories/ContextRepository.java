package com.ia.chatbot.repositories;

import com.ia.chatbot.models.entities.Context;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContextRepository extends JpaRepository<Context,Long> {
}
