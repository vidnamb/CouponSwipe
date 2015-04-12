package edu.cmu.couponswipe.model;

/**
 * Created by sparshith on 10/4/15.
 */
public class User {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private int prefDistance;
    private String prefCategories;

    public User() {
    }

    public User(int userId, String firstName, String lastName, String email, String phoneNumber, String password, int prefDistance, String prefCategories) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.prefDistance = prefDistance;
        this.prefCategories = prefCategories;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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

    public int getPrefDistance() {
        return prefDistance;
    }

    public void setPrefDistance(int prefDistance) {
        this.prefDistance = prefDistance;
    }

    public String getPrefCategories() {
        return prefCategories;
    }

    public void setPrefCategories(String prefCategories) {
        this.prefCategories = prefCategories;
    }

}
