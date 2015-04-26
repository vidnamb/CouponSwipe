package edu.cmu.couponswipe.model;

import java.util.ArrayList;

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
    private double lastLongitude;
    private double lastLatitude;
    private int dealRadius;
    private ArrayList<String> dealCategories;

    public User() {
        this.dealRadius = 10;
        this.dealCategories = new ArrayList<String>();
        dealCategories.add("food-and-drink");
        dealCategories.add("things-to-do");
        dealCategories.add("beauty-and-spas");
        dealCategories.add("health-and-fitness");
        dealCategories.add("home-improvement");
        dealCategories.add("local-services");
        dealCategories.add("shopping");
        dealCategories.add("automotive");
        dealCategories.add("auto-and-home-improvement");
        dealCategories.add("baby-kids-and-toys");
        dealCategories.add("basics");
        dealCategories.add("electronics");
        dealCategories.add("entertainment");
        dealCategories.add("food-and-drink");
        dealCategories.add("health-and-beauty");
        dealCategories.add("home-and-garden");
        dealCategories.add("men");
        dealCategories.add("sports-and-outdoors");
        dealCategories.add("women");
    }

    public User(int userId, String firstName, String lastName, String email, String phoneNumber, String password) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
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

    public double getLastLatitude() {
        return lastLatitude;
    }

    public void setLastLatitude(double lastLatitude) {
        this.lastLatitude = lastLatitude;
    }

    public double getLastLongitude() {
        return lastLongitude;
    }

    public void setLastLongitude(double lastLongitude) {
        this.lastLongitude = lastLongitude;
    }

    public int getDealRadius() {
        return dealRadius;
    }

    public void setDealRadius(int dealRadius) {
        this.dealRadius = dealRadius;
    }

    public ArrayList<String> getDealCategories() {
        return dealCategories;
    }

    public void setDealCategories(ArrayList<String> dealCategories) {
        this.dealCategories = dealCategories;
    }

    public void addDealCategory(String dealCategory) {
        if(!this.dealCategories.contains(dealCategory))
            this.dealCategories.add(dealCategory);
    }

    public void removeDealCategory(String dealCategory){
        this.dealCategories.remove(dealCategory);
    }

    public String getName() {
        return this.firstName + " " + this.getLastName();
    }

}
