package com.codecool.videoservice.controller;

import com.codecool.videoservice.model.Recommendation;
import com.codecool.videoservice.repository.RecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/recommendation")
public class RecommendationController {

    @Autowired
    private RecommendationRepository recommendationRepository;

    @GetMapping("/{videoId}")
    public List<Recommendation> getRecommendationsByVideoId(@PathVariable("videoId") Long id) {
        return recommendationRepository.findAllByVideoId(id);
    }

    @PostMapping("/add")
    public List<Recommendation> saveNewRecommendation(@RequestBody Recommendation recommendation, @RequestParam("videoId") Long id) {
        recommendation.setVideoId(id);
        recommendationRepository.save(recommendation);
        return recommendationRepository.findAllByVideoId(id);
    }

    @PutMapping("/{id}")
    public Recommendation updateRecommendationById(@RequestBody Recommendation recommendation, @PathVariable("id") Long id) {
        recommendationRepository.updateById(recommendation, id);
        return recommendationRepository.findById(id).orElse(null);
    }

    @PutMapping("/update")
    public Recommendation updateRecommendation(@RequestBody Recommendation recommendation) {
        recommendationRepository.save(recommendation);
        return recommendationRepository.findById(recommendation.getId()).orElse(null);
    }
}
