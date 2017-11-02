package edu.shu.bowling.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.util.Date;
import edu.shu.bowling.common.PasswordComplexityException;
import edu.shu.bowling.common.PhoneNumberException;
import edu.shu.bowling.common.EmailException;
import edu.shu.bowling.common.UserIDException;

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
        // user name can contain upper,lower,digit, and a few specials
        if (userId.matches("^([0-9a-zA-Z .\\-_]{2,20})$")) {
            this.userId = userId;
        }
        else {
            throw new UserIDException("User name contains illegal characters.");
        }
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
        //regex from https://howtodoinjava.com/regex/java-regex-validate-email-address/
        if (email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            this.email = email;
        }
        else {
            throw new EmailException("Email address is not in the correct format.");
        }
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        // exactly 10 digits with no special characters
        if (phone.matches("^[0-9]{10}$")) {
            this.phone = phone;
        }
        else {
            throw new PhoneNumberException("Phone number is not in the correct format.");
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        /*
        ^                 # start-of-string
        (?=.*[0-9])       # a digit must occur at least once
        (?=.*[a-z])       # a lower case letter must occur at least once
        (?=.*[A-Z])       # an upper case letter must occur at least once
        (?=.*[@#$%^&+=])  # a special character must occur at least once
        (?=\S+$)          # no whitespace allowed in the entire string
        .{8,}             # anything, at least eight places though
        $                 # end-of-string
        */
        if (password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")) {
            this.password = Password.hashPassword(password);
        }
        else {
            throw new PasswordComplexityException("Password is not complex enough.");
        }
    }

}
