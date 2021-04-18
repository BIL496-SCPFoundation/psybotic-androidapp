package com.scpfoundation.psybotic.app.data;

import java.util.Objects;

public class User {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String googleId;
    private Character gender;
    private String city;
    private String maritalStatus;
    private String imageUrl;
    private String deviceToken;
    private Double[] location;
    private double mentalState;
    private boolean admin = false;
    private boolean psychologist = false;

    public boolean isPsychologist() {
        return psychologist;
    }

    public void setPsychologist(boolean psychologist) {
        this.psychologist = psychologist;
    }

    public boolean isAdmin() {
        return this.admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public double getMentalState() {
        return mentalState;
    }

    public void setMentalState(double mentalState) {
        this.mentalState = mentalState;
    }

    public Double[] getLocation() {
        return location;
    }

    public void setLocation(Double[] location) {
        this.location = location;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id.equals(user.id) &&
                Objects.equals(getFirstName(), user.getFirstName()) &&
                Objects.equals(getLastName(), user.getLastName()) &&
                Objects.equals(getEmail(), user.getEmail()) &&
                Objects.equals(getGoogleId(), user.getGoogleId()) &&
                Objects.equals(getGender(), user.getGender()) &&
                Objects.equals(getCity(), user.getCity()) &&
                Objects.equals(getMaritalStatus(), user.getMaritalStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getFirstName(), getLastName(), getEmail(), getGoogleId(), getGender(), getCity(),
                getMaritalStatus());
    }
}
