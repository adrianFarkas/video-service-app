package com.codecool.videoservice.repository;

import com.codecool.videoservice.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByVideoId(Long id);

    @Modifying
    @Query("UPDATE Comment " +
            "SET comment = :#{#comment.comment} " +
            "WHERE id = :id")
    void updateById(Comment comment, Long id);
}
