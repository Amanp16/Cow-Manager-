package com.example.siliconlab.classes;

public class User {

    private String uid ;
    private String name ;
    private String mobile ;
    private int age = 20 ;
    private int healthyCount = 0 ;
    private int linkedCow = 0 ;

    public User ()
    {

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHealthyCount() {
        return healthyCount;
    }

    public void setHealthyCount(int healthyCount) {
        this.healthyCount = healthyCount;
    }

    public int getLinkedCow() {
        return linkedCow;
    }

    public void setLinkedCow(int linkedCow) {
        this.linkedCow = linkedCow;
    }

}
