package com.tutor.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.tutor.enums.Role;

public class SecurityUtil {

    private SecurityUtil() {}

    public static Long getCurrentUserId() {
    	    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    	    if (principal instanceof String) {
    	        return Long.parseLong((String) principal); // ✅ safe conversion
    	    } else if (principal instanceof Long) {
    	        return (Long) principal;
    	    } else {
    	        throw new RuntimeException("Invalid principal type: " + principal.getClass());
    	    }
    	}


//        Authentication auth =
//                SecurityContextHolder.getContext().getAuthentication();
//
//        if (auth == null || auth.getPrincipal() == null) {
//            throw new RuntimeException("User not authenticated");
//        }
//
//        return (Long) auth.getPrincipal();
    

    public static Role getCurrentUserRole() {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth.getAuthorities().isEmpty()) {
            throw new RuntimeException("User role not found");
        }

        String role = auth.getAuthorities()
                .iterator()
                .next()
                .getAuthority();

        return Role.valueOf(role);
    }
}
