package com.tutor.service;

import com.tutor.dto.VideoRequestDTO;
import com.tutor.dto.VideoResponseDTO;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VideoService {

    VideoResponseDTO uploadVideo(VideoRequestDTO dto);

    List<VideoResponseDTO> getAllVideos();

    VideoResponseDTO getVideoById(Long id);

    ResponseEntity<Resource> downloadVideo(Long id);

    void deleteVideo(Long id);
}