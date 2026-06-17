package com.tutor.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tutor.dto.AttendanceRequestDTO;
import com.tutor.dto.AttendanceResponseDTO;
import com.tutor.entity.Attendance;
import com.tutor.entity.Student;
import com.tutor.entity.Teacher;
import com.tutor.repository.AttendanceRepository;
import com.tutor.repository.StudentRepository;
import com.tutor.repository.TeacherRepository;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepo;

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private TeacherRepository teacherRepo;

    // ✅ CREATE
    @Override
    public AttendanceResponseDTO saveAttendance(AttendanceRequestDTO dto) {

        Student student = studentRepo.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException(
                        "Student ID not found: " + dto.getStudentId()));

        Teacher teacher = teacherRepo.findById(dto.getTeacherId())
                .orElseThrow(() -> new RuntimeException(
                        "Teacher ID not found: " + dto.getTeacherId()));

        Attendance attendance = new Attendance();
        attendance.setDate(dto.getDate());
        attendance.setStatus(dto.getStatus());
        attendance.setStudent(student);
        attendance.setTeacher(teacher);

        Attendance saved = attendanceRepo.save(attendance);

        AttendanceResponseDTO res = new AttendanceResponseDTO();
        res.setId(saved.getId());
        res.setDate(saved.getDate());
        res.setStatus(saved.getStatus());
        res.setStudentId(student.getId());
        res.setTeacherId(teacher.getId());

        return res;
    }

    // ✅ GET ALL
    @Override
    public List<AttendanceResponseDTO> getAllAttendances() {

        List<Attendance> list = attendanceRepo.findAll();
        List<AttendanceResponseDTO> responseList = new ArrayList<>();

        for (Attendance a : list) {
            AttendanceResponseDTO res = new AttendanceResponseDTO();
            res.setId(a.getId());
            res.setDate(a.getDate());
            res.setStatus(a.getStatus());
            res.setStudentId(a.getStudent().getId());
            res.setTeacherId(a.getTeacher().getId());
            responseList.add(res);
        }

        return responseList;
    }

    // ✅ GET BY ID
    @Override
    public AttendanceResponseDTO getAttendanceById(Long id) {

        Attendance a = attendanceRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));

        AttendanceResponseDTO res = new AttendanceResponseDTO();
        res.setId(a.getId());
        res.setDate(a.getDate());
        res.setStatus(a.getStatus());
        res.setStudentId(a.getStudent().getId());
        res.setTeacherId(a.getTeacher().getId());

        return res;
    }

    // ✅ UPDATE
    @Override
    public AttendanceResponseDTO updateAttendance(Long id, AttendanceRequestDTO dto) {

        Attendance attendance = attendanceRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));

        Student student = studentRepo.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Teacher teacher = teacherRepo.findById(dto.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        attendance.setDate(dto.getDate());
        attendance.setStatus(dto.getStatus());
        attendance.setStudent(student);
        attendance.setTeacher(teacher);

        Attendance updated = attendanceRepo.save(attendance);

        AttendanceResponseDTO res = new AttendanceResponseDTO();
        res.setId(updated.getId());
        res.setDate(updated.getDate());
        res.setStatus(updated.getStatus());
        res.setStudentId(student.getId());
        res.setTeacherId(teacher.getId());

        return res;
    }

    // ✅ DELETE
    @Override
    public void deleteAttendance(Long id) {
        attendanceRepo.deleteById(id);
    }
}
