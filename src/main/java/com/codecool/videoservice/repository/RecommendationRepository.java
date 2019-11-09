package com.codecool.videoservice.repository;

import com.codecool.videoservice.model.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {

    List<Recommendation> findAllByVideoId(Long id);

    @Modifying
    @Query("UPDATE Recommendation " +
            "SET comment = :#{#recommendation.comment}, rating = :#{#recommendation.rating} " +
            "WHERE id = :id")
    void updateById(Recommendation recommendation, Long id);
}
