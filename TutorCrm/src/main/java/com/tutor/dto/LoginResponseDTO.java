package com.tutor.dto;

public class LoginResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String role; // <-- change from Role to String
    private String token;
    private String message;

    public LoginResponseDTO(
            Long id,
            String name,
            String email,
            String role, // <-- change from Role to String
            String token,
            String message
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.token = token;
        this.message = message;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRole() { return role; } // <-- String
    public String getToken() { return token; }
    public String getMessage() { return message; }
}
