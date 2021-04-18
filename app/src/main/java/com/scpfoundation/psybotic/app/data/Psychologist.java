package com.scpfoundation.psybotic.app.data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class Psychologist implements Serializable {
    private String id;
    private Date createdDate;
    private String firstName;
    private String lastName;
    private String expertise;
    private String ageOfInterest;
    private String[] educations;
    private String[] titles;
    private String biography;
    private String imageURL;
    private boolean approved = false;
    private boolean rejected = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public String getAgeOfInterest() {
        return ageOfInterest;
    }

    public void setAgeOfInterest(String ageOfInterest) {
        this.ageOfInterest = ageOfInterest;
    }

    public String[] getEducations() {
        return educations;
    }

    public void setEducations(String[] educations) {
        this.educations = educations;
    }

    public String[] getTitles() {
        return titles;
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isRejected() {
        return rejected;
    }

    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Psychologist)) return false;
        Psychologist that = (Psychologist) o;
        return isApproved() == that.isApproved() && Objects.equals(getId(), that.getId()) && Objects.equals(getCreatedDate(), that.getCreatedDate()) && Objects.equals(getFirstName(), that.getFirstName()) && Objects.equals(getLastName(), that.getLastName()) && Objects.equals(getExpertise(), that.getExpertise()) && Objects.equals(getAgeOfInterest(), that.getAgeOfInterest()) && Arrays.equals(getEducations(), that.getEducations()) && Arrays.equals(getTitles(), that.getTitles()) && Objects.equals(getBiography(), that.getBiography()) && Objects.equals(getImageURL(), that.getImageURL());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getId(), getCreatedDate(), getFirstName(), getLastName(), getExpertise(), getAgeOfInterest(), getBiography(), getImageURL(), isApproved());
        result = 31 * result + Arrays.hashCode(getEducations());
        result = 31 * result + Arrays.hashCode(getTitles());
        return result;
    }
}
