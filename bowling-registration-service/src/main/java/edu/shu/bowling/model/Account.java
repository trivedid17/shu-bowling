package edu.shu.bowling.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.util.Date;
import edu.shu.bowling.common.PasswordLengthException;


@Entity
public class Account {
    @Id
    @Column(name = "user_id")
    @Size(min=2,max = 20,  message = "User ID must be between {min} and {max} characters.")
    private String userId;

    @Column(name = "first_name")
    @Size(min=2,max = 20,  message = "First Name must be between {min} and {max} characters.")
    private String firstName;

    @Column(name = "last_name")
    @Size(min=2,max = 30,  message = "Last Name must be between {min} and {max} characters.")
    private String lastName;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "email")
    @Size(min=4,max = 30,  message = "Email must be between {min} and {max} characters.")
    private String email;

    @Column(name = "phone")
    @Size(min=10,max=10,message = "Phone number should be {min} integers.")
    private String phone;

    @Column(name = "password")
    @Size(min=8,max=64,message = "Password should be {min} characters.")
    private String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password.length() > 7) {
            this.password = Password.hashPassword(password);
        }
        else {
            throw new PasswordLengthException("Password is too short.");
        }
    }

}
