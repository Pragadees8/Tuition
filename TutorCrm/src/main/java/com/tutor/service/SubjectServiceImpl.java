package com.tutor.service;

import com.tutor.config.JwtProvider;
import com.tutor.entity.Admin;
import com.tutor.entity.Subject;
import com.tutor.entity.User;
import com.tutor.repository.AdminRepository;
import com.tutor.repository.SubjectRepository;
import com.tutor.repository.UserRepository;
import com.tutor.service.SubjectService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {
	@Autowired
    private  SubjectRepository subjectRepository;
	@Autowired
	AdminRepository adminRepository;
	  @Autowired
	    private JwtProvider jwtProvider;

	    @Autowired
	    private UserRepository userRepository;


	  @Override
	    public Subject createSubject(Subject subject, HttpServletRequest http) {

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
	                () -> new RuntimeException(" Login User not Found ...")
	        );

	         Subject newSubject = new Subject();
	         newSubject.setSubjectName(subject.getSubjectName());
	         newSubject.setDescription(subject.getDescription());
	         newSubject.setLevel(subject.getLevel());
	         newSubject.setCreatedBy(loginUser.getUsername());
	        newSubject.setUpdatedBy(loginUser.getUsername());
	        newSubject.setCreatedAt(LocalDateTime.now().withNano(0));
	        newSubject.setUpdatedAt(LocalDateTime.now().withNano(0));

	//
//	         newSubject.setCreatedByAdmin(admin);

	         return subjectRepository.save(newSubject);

	    }
    @Override
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    @Override
    public Subject getSubjectById(Long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found with id: " + id));
    }

    @Override
    public Subject updateSubject(Long id, Subject subject,Long adminId) {
    	 Admin admin = adminRepository.findById(adminId)
		            .orElseThrow(() -> new RuntimeException("Admin not found"));

        Subject existing = getSubjectById(id);
        existing.setSubjectName(subject.getSubjectName());
        existing.setDescription(subject.getDescription());
        existing.setLevel(subject.getLevel());
//        existing.setCreatedByAdmin(admin);
        return subjectRepository.save(existing);
    }

    @Override
    public void deleteSubject(Long id) {
        subjectRepository.deleteById(id);
    }
}
