package com.example.siliconlab.classes;

import java.util.ArrayList;
import java.util.List;

public class ModelCow {

    private String cowName;
    private int cowState;
    private String cowId;
    private String imgURI;
    private int age = 0 ;
    private String sensorId ;
    private String breed ;
    private int temp ;
    private List<String> diseaseHistory = new ArrayList<>();
    private int pregnancyState ;
    private String currentDisease ;


    public ModelCow(String cowName, int cowState, String cowId, String imgURI) {
        this.cowName = cowName;
        this.cowState = cowState;
        this.cowId = cowId;
        this.imgURI = imgURI;
    }

    public ModelCow ()
    {

    }

    public String getCowName() {
        return cowName;
    }

    public String getImgURI() {
        return imgURI;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public List<String> getDiseaseHistory() {
        return diseaseHistory;
    }

    public void setDiseaseHistory(List<String> diseaseHistory) {
        this.diseaseHistory = diseaseHistory;
    }

    public int getPregnancyState() {
        return pregnancyState;
    }

    public void setPregnancyState(int pregnancyState) {
        this.pregnancyState = pregnancyState;
    }

    public String getCurrentDisease() {
        return currentDisease;
    }

    public void setCurrentDisease(String currentDisease) {
        this.currentDisease = currentDisease;
    }

    public void setImgURI(String imgURI) {
        this.imgURI = imgURI;
    }

    public void setCowName(String cowName) {
        this.cowName = cowName;
    }

    public int getCowState() {
        return cowState;
    }

    public void setCowState(int cowState) {
        this.cowState = cowState;
    }

    public String getCowId() {
        return cowId;
    }

    public void setCowId(String cowId) {
        this.cowId = cowId;
    }
}
