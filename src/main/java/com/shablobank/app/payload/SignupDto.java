package com.shablobank.app.payload;
import com.shablobank.app.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupDto {
    private String lastname;
    private String firstname;
    private Role role;
    private String email;
    private String password;
}
