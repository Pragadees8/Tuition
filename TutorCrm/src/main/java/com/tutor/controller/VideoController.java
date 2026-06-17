package com.tutor.controller;

import com.tutor.dto.VideoRequestDTO;
import com.tutor.dto.VideoResponseDTO;
import com.tutor.service.VideoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@RestController
@RequestMapping("/api/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @PostMapping("/upload")
    public VideoResponseDTO uploadVideo(@ModelAttribute VideoRequestDTO dto) {
        return videoService.uploadVideo(dto);
    }

    @GetMapping("/all")
    public List<VideoResponseDTO> getAllVideos() {
        return videoService.getAllVideos();
    }

    @GetMapping("/{id}")
    public VideoResponseDTO getVideo(@PathVariable Long id) {
        return videoService.getVideoById(id);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadVideo(@PathVariable Long id) {
        return videoService.downloadVideo(id);
    }

    @DeleteMapping("/{id}")
    public String deleteVideo(@PathVariable Long id) {
        videoService.deleteVideo(id);
        return "Video deleted successfully";
    }
}