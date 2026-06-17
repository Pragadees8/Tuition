package com.tutor.service;

import com.tutor.dto.VideoRequestDTO;
import com.tutor.dto.VideoResponseDTO;
import com.tutor.entity.Subject;
import com.tutor.entity.Teacher;
import com.tutor.entity.Video;
import com.tutor.enums.Status;
import com.tutor.repository.SubjectRepository;
import com.tutor.repository.TeacherRepository;
import com.tutor.repository.VideoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    private final String uploadDir = "uploads/videos/";

    @Override
    public VideoResponseDTO uploadVideo(VideoRequestDTO dto) {

        try {

            Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));

            Subject subject = subjectRepository.findById(dto.getSubjectId())
                    .orElseThrow(() -> new RuntimeException("Subject not found"));

            File folder = new File(uploadDir);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            String fileName = System.currentTimeMillis() + "_" + dto.getFile().getOriginalFilename();

            Path filePath = Paths.get(uploadDir + fileName);

            Files.copy(dto.getFile().getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            Video video = new Video();
            video.setTitle(dto.getTitle());
            video.setDescription(dto.getDescription());
            video.setFileName(fileName);
            video.setFilePath(filePath.toString());
            video.setTeacher(teacher);
            video.setSubject(subject);
            video.setUploadedAt(LocalDateTime.now());
            video.setStatus(Status.ACTIVE);

            Video saved = videoRepository.save(video);

            VideoResponseDTO res = new VideoResponseDTO();
            res.setId(saved.getId());
            res.setTitle(saved.getTitle());
            res.setDescription(saved.getDescription());
            res.setFileName(saved.getFileName());
            res.setTeacherId(saved.getTeacher().getId());
            res.setSubjectId(saved.getSubject().getId());
            res.setUploadedAt(saved.getUploadedAt());
            res.setStatus(saved.getStatus());
            res.setVideoUrl("/api/video/download/" + saved.getId());

            return res;

        } catch (Exception e) {
            throw new RuntimeException("Video upload failed");
        }
    }

    @Override
    public List<VideoResponseDTO> getAllVideos() {

        List<Video> videos = videoRepository.findAll();
        List<VideoResponseDTO> list = new ArrayList<>();

        for (Video v : videos) {

            VideoResponseDTO dto = new VideoResponseDTO();

            dto.setId(v.getId());
            dto.setTitle(v.getTitle());
            dto.setDescription(v.getDescription());
            dto.setFileName(v.getFileName());
            dto.setTeacherId(v.getTeacher().getId());
            dto.setSubjectId(v.getSubject().getId());
            dto.setUploadedAt(v.getUploadedAt());
            dto.setStatus(v.getStatus());
            dto.setVideoUrl("/api/video/download/" + v.getId());

            list.add(dto);
        }

        return list;
    }

    @Override
    public VideoResponseDTO getVideoById(Long id) {

        Video v = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found"));

        VideoResponseDTO dto = new VideoResponseDTO();

        dto.setId(v.getId());
        dto.setTitle(v.getTitle());
        dto.setDescription(v.getDescription());
        dto.setFileName(v.getFileName());
        dto.setTeacherId(v.getTeacher().getId());
        dto.setSubjectId(v.getSubject().getId());
        dto.setUploadedAt(v.getUploadedAt());
        dto.setStatus(v.getStatus());
        dto.setVideoUrl("/api/video/download/" + v.getId());

        return dto;
    }

    @Override
    public ResponseEntity<Resource> downloadVideo(Long id) {

        try {

            Video video = videoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Video not found"));

            Path path = Paths.get(video.getFilePath());

            Resource resource = new UrlResource(path.toUri());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + video.getFileName() + "\"")
                    .body(resource);

        } catch (Exception e) {
            throw new RuntimeException("Download failed");
        }
    }

    @Override
    public void deleteVideo(Long id) {

        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found"));

        try {
            Files.deleteIfExists(Paths.get(video.getFilePath()));
        } catch (Exception e) {
            System.out.println("File delete failed");
        }

        videoRepository.deleteById(id);
    }
}