package com.ia.chatbot.repositories;

import com.ia.chatbot.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    @Query(value = """
    SELECT u.* FROM t_users as u 
    LEFT JOIN t_users_roles ur on ur.user_id=u.id
    LEFT JOIN t_roles r on r.id=ur.role_id
    WHERE r.name='ADMIN'
""",nativeQuery = true)
    List<User> findAdminUsers();
}
