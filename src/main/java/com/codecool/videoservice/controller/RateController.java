package com.codecool.videoservice.controller;

import com.codecool.videoservice.dao.RateDao;
import com.codecool.videoservice.model.RateType;
import com.codecool.videoservice.model.VideoRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/rates")
public class RateController {

    @Autowired
    private RateDao rateDaoJpa;

    @GetMapping("/video")
    public List<VideoRate> getAllRateByVideo(@RequestParam Long id) {
        return rateDaoJpa.getAllRateByVideo(id);
    }


    @PostMapping("/video")
    public Map<RateType, Long> rateVideo(@RequestParam Long id, @RequestParam String rate) {
        rateDaoJpa.saveVideoRate(id, RateType.valueOf(rate.toUpperCase()));
        return rateDaoJpa.getCountOfRateByVideo(id);
    }

    @GetMapping("/user/video")
    public VideoRate getUserRateByVideo(@RequestParam Long id) {
        return rateDaoJpa.getUserRateByVideo(id);
    }

    @GetMapping("/video/count")
    public Map<RateType, Long> getCountOfRateByVideo(@RequestParam Long id) {
        return rateDaoJpa.getCountOfRateByVideo(id);
    }

    @DeleteMapping("/video")
    public Map<RateType, Long> deleteVideoRate(@RequestParam Long id) {
        rateDaoJpa.deleteVideoRate(id);
        return rateDaoJpa.getCountOfRateByVideo(id);
    }
}

