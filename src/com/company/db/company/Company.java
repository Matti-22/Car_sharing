package com.company.db.company;

public class Company {
    private Integer ID;
    private String NAME;

    public Company(String NAME) {
        this.NAME = NAME;
    }

    public Company(Integer ID, String NAME){
        this.ID = ID;
        this.NAME = NAME;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }
}
