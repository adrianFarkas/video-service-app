package com.codecool.videoservice.controller;

import com.codecool.videoservice.model.Comment;
import com.codecool.videoservice.repository.CommentRepository;
import com.codecool.videoservice.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private EntityManager entityManager;

    @GetMapping
    public List<Comment> getCommentsByVideoId(@RequestParam("videoId") Long id) {
        return commentRepository.findAllByVideoId(id);
    }

    @PostMapping
    public List<Comment> addNewComment(@RequestBody Comment comment, @RequestParam("videoId") Long id) {
        comment.setVideo(videoRepository.findById(id).orElse(null));
        commentRepository.save(comment);
        entityManager.clear();
        return commentRepository.findAllByVideoId(id);
    }

    @PutMapping("/{id}")
    public Comment updateComment(@RequestBody Comment comment, @PathVariable("id") Long id) {
        commentRepository.updateById(comment, id);
        return commentRepository.findById(id).orElse(null);
    }
}