package com.example.siliconlab.classes;

import java.util.ArrayList;
import java.util.List;

public class ModelBarn {

    private String userId ;
    private String barnName;
    private int barnState;
    private List<ModelCow> cowList = new ArrayList<>();
    private String address ;

    public ModelBarn(String barnName, int barnState) {
        this.barnName = barnName;
        this.barnState = barnState;
    }

    public ModelBarn ()
    {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<ModelCow> getCowList() {
        return cowList;
    }

    public void setCowList(List<ModelCow> cowList) {
        this.cowList = cowList;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBarnName() {
        return barnName;
    }

    public void setBarnName(String barnName) {
        this.barnName = barnName;
    }

    public int getBarnState() {
        return barnState;
    }

    public void setBarnState(int barnState) {
        this.barnState = barnState;
    }
}
