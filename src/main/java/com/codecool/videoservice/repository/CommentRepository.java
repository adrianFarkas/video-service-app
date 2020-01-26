package com.codecool.videoservice.repository;

import com.codecool.videoservice.model.Comment;
import com.codecool.videoservice.model.CommentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT NEW com.codecool.videoservice.model.CommentDetails(c)" +
            "FROM Comment c " +
            "WHERE c.video.id = :id " +
            "ORDER BY c.creationDate DESC")
    List<CommentDetails> findAllByVideoId(Long id);

    @Query("SELECT NEW com.codecool.videoservice.model.CommentDetails(c)" +
            "FROM Comment c " +
            "WHERE c.id = :id")
    CommentDetails getCommentById(Long id);

    @Modifying
    @Query("UPDATE Comment " +
            "SET comment = :#{#comment.comment} " +
            "WHERE id = :id")
    void updateById(Comment comment, Long id);
}
