package com.ia.chatbot.repositories;

import com.ia.chatbot.models.entities.QuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionsAnswerRepository  extends JpaRepository<QuestionAnswer,Long> {
    List<QuestionAnswer> findQuestionAnswerByDiscussion_Id(Long discussionId);
}
