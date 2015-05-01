package edu.cmu.couponswipe.model;

import java.util.ArrayList;

/**
 * Created by sparshith on 10/4/15.
 */
public class User {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private double lastLongitude;
    private double lastLatitude;
    private int dealRadius;
    private ArrayList<String> dealCategories;
    private String updatedAt;
    private String createdAt;

    public User() {
        this.dealRadius = 10;
        this.dealCategories = new ArrayList<String>();
    }

    public User(String firstName, String lastName, String email, String phoneNumber, String password) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.dealRadius = 10;
        this.dealCategories = new ArrayList<String>();
    }

    public User(String firstName, String lastName, String email, String phoneNumber, String lastLatitude,
                String lastLongitude, int dealRadius, String dealCategories, String updatedAt, String createdAt) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.lastLatitude = Double.parseDouble(lastLatitude);
        this.lastLongitude = Double.parseDouble(lastLongitude);
        this.dealRadius = dealRadius;
        this.dealCategories = new ArrayList<String>();
        String[] categories = dealCategories.split(",");
        for(String cat: categories)
            this.dealCategories.add(cat);
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
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

    public String getDealCategoriesString() {

        StringBuilder dealCategoriesSB = new StringBuilder();
        for(String dealCategory: dealCategories) {
            dealCategoriesSB.append(dealCategory);
            dealCategoriesSB.append(",");
        }
        if(dealCategoriesSB.length()>0)
            dealCategoriesSB.deleteCharAt(dealCategoriesSB.length()-1);
        return dealCategoriesSB.toString();
    }

    public String getUpdatedAt() { return updatedAt;    }

    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt;    }

    public String getCreatedAt() { return createdAt;    }

    public void setCreatedAt(String createdAt) { this.createdAt = createdAt;    }

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
