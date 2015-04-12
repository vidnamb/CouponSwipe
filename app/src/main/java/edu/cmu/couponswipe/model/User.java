package edu.cmu.couponswipe.model;

/**
 * Created by sparshith on 10/4/15.
 */
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private String prefDistance;
    private String prefCategory;

    public User() {
    }

    public User(String firstName, String lastName, String email, String phoneNumber, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }


    public String getPrefDistance() {
        return prefDistance;
    }

    public void setPrefDistance(String prefDistance) {
        this.prefDistance = prefDistance;
    }

    public String getPrefCategory() {
        return prefCategory;
    }

    public void setPrefCategory(String prefCategory) {
        this.prefCategory = prefCategory;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
