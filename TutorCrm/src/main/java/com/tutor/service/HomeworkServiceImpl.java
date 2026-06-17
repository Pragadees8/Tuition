
package com.tutor.service;



import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutor.config.JwtProvider;
import com.tutor.entity.Homework;
import com.tutor.entity.Subject;
import com.tutor.entity.Teacher;
import com.tutor.entity.User;
import com.tutor.enums.HomeworkStatus;
import com.tutor.repository.AdminRepository;
import com.tutor.repository.HomeworkRepository;
import com.tutor.repository.StudentRepository;
import com.tutor.repository.SubjectRepository;
import com.tutor.repository.TeacherRepository;
import com.tutor.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class HomeworkServiceImpl implements HomeworkService {

	@Autowired
    private  HomeworkRepository homeworkRepository;
	@Autowired
    private  TeacherRepository teacherRepository;
	@Autowired
    private  StudentRepository studentRepository;
	@Autowired
    private  SubjectRepository subjectRepository;
	@Autowired
    private  AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProvider jwtProvider;



    
    @Override
    public Homework saveHomework(
            Homework request,
            Long teacherId,
            Long subjectId,
            HttpServletRequest http) {

        String token = null;
        if (token == null) {
            if (http.getCookies() != null) {
                for (Cookie cookie : http.getCookies()) {
                    if (cookie.getName().equals("access_token")) {
                        token = cookie.getValue();
                        System.out.println(token);
                    }
                }
            }
        }
        if (token == null) {
            throw new RuntimeException("You are not Authenticated ...");
        }

        Claims payload = jwtProvider.getPayload(token);
        String username = payload.getSubject();
        System.out.println(username);

        User loginUser = userRepository.findByUsername(username).orElseThrow(
                () ->  new RuntimeException(" user is not found ...")
        );
        Teacher teacher = teacherRepository.findById(teacherId)
              .orElseThrow(() -> new RuntimeException("Teacher not found"));;
        

//        Teacher teacher = teacherRepository.findByEmail(loginUser.getEmail())
//                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        Homework hw = new Homework();
        hw.setTitle(request.getTitle());
        hw.setDescription(request.getDescription());

        //  FIX: use request date if provided
        hw.setAssignedDate(
                request.getAssignedDate() != null
                        ? request.getAssignedDate()
                        : LocalDate.now()
        );

        hw.setDocumentUrl(request.getDocumentUrl());
        hw.setDueDate(request.getDueDate());
        hw.setStatus(HomeworkStatus.ASSIGNED);
        hw.setCreatedAt( LocalDateTime.now().withNano(0) );
        hw.setUpdatedAt( LocalDateTime.now().withNano(0) );
        hw.setCreatedBy(loginUser.getUsername());
        hw.setUpdatedBy(loginUser.getUsername());
        hw.setTeacher(teacher);
        hw.setSubject(subject);

        return homeworkRepository.save(hw);
    }

    // ================= READ =================
    @Override
    public List<Homework> getAllHomework() {
        return homeworkRepository.findAll();
    }

    @Override
    public Homework getHomeworkBystudentId(Long studentId) {
        return homeworkRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Homework not found with id: " + studentId));
    }

    // ================= UPDATE =================
    @Override
    public Homework updateHomework(Long homeworkId, Homework request, Long adminId) {

        Homework existing = homeworkRepository.findById(homeworkId)
                .orElseThrow(() -> new RuntimeException("Homework not found"));

//        Admin admin = adminRepository.findById(adminId)
//                .orElseThrow(() -> new RuntimeException("Admin not found"));

        existing.setTitle(request.getTitle());
        existing.setDescription(request.getDescription());

        //  FIXES YOU MISSED
        existing.setAssignedDate(request.getAssignedDate());
        existing.setDueDate(request.getDueDate());
        existing.setStatus(request.getStatus());
//        existing.setCreatedBy(request.getCreatedBy());

//        existing.setAdmin(admin);

        return homeworkRepository.save(existing);
    }

    // ================= DELETE =================
    @Override
    public void deleteHomework(Long id) {
        Homework hw = homeworkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Homework not found"));
        homeworkRepository.delete(hw);
    }

	@Override
	public Homework getHomeworkById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
    
    

//    // ================= CREATE =================
//    @Override
//    public Homework saveHomework(
//            Homework request,
//            Long teacherId,
//            Long studentId,
//            Long subjectId,
//            Long adminId) {
//
//        Teacher teacher = teacherRepository.findById(teacherId)
//                .orElseThrow(() -> new RuntimeException("Teacher not found"));
//
//        Student student = studentRepository.findById(studentId)
//                .orElseThrow(() -> new RuntimeException("Student not found"));
//
//        Subject subject = subjectRepository.findById(subjectId)
//                .orElseThrow(() -> new RuntimeException("Subject not found"));
//
//        Admin admin = adminRepository.findById(adminId)
//                .orElseThrow(() -> new RuntimeException("Admin not found"));
//
//        Homework hw = new Homework();
//        hw.setTitle(request.getTitle());
//        hw.setDescription(request.getDescription());
//
//        // 🔥 FIX: use request date if provided
//        hw.setAssignedDate(
//                request.getAssignedDate() != null
//                        ? request.getAssignedDate()
//                        : LocalDate.now()
//        );
//
//        hw.setDueDate(request.getDueDate());
//        hw.setStatus(request.getStatus());              // 🔥 FIX
//        hw.setSubmittedby(request.getSubmittedby());    // 🔥 FIX
//        hw.setTeacher(teacher);
//        hw.setStudent(student);
//        hw.setSubject(subject);
//        hw.setAdmin(admin);
//
//        return homeworkRepository.save(hw);
//    }
//
//    // ================= READ =================
//    @Override
//    public List<Homework> getAllHomework() {
//        return homeworkRepository.findAll();
//    }
//
//    @Override
//    public Homework getHomeworkById(Long id) {
//        return homeworkRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Homework not found with id: " + id));
//    }
//
//    // ================= UPDATE =================
//    @Override
//    public Homework updateHomework(Long homeworkId, Homework request, Long adminId) {
//
//        Homework existing = homeworkRepository.findById(homeworkId)
//                .orElseThrow(() -> new RuntimeException("Homework not found"));
//
//        Admin admin = adminRepository.findById(adminId)
//                .orElseThrow(() -> new RuntimeException("Admin not found"));
//
//        existing.setTitle(request.getTitle());
//        existing.setDescription(request.getDescription());
//
//        // 🔥 FIXES YOU MISSED
//        existing.setAssignedDate(request.getAssignedDate());
//        existing.setDueDate(request.getDueDate());
//        existing.setStatus(request.getStatus());
//        existing.setSubmittedby(request.getSubmittedby());
//
//        existing.setAdmin(admin);
//
//        return homeworkRepository.save(existing);
//    }
//
//    // ================= DELETE =================
//    @Override
//    public void deleteHomework(Long id) {
//        Homework hw = homeworkRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Homework not found"));
//        homeworkRepository.delete(hw);
//    }
//}
