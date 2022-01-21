package com.company.db.car;

public class Car {
    private Integer ID;
    private String NAME;
    private Integer COMPANY_ID;
    private boolean isRent = false;

    public Car(Integer ID, String NAME, Integer COMPANY_ID, boolean isRent) {
        this.ID = ID;
        this.NAME = NAME;
        this.COMPANY_ID = COMPANY_ID;
        this.isRent = isRent;
    }

    public Car(String NAME, Integer COMPANY_ID, boolean isRent) {
        this.NAME = NAME;
        this.COMPANY_ID = COMPANY_ID;
        this.isRent = isRent;
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

    public Integer getCOMPANY_ID() {
        return COMPANY_ID;
    }

    public void setCOMPANY_ID(Integer COMPANY_ID) {
        this.COMPANY_ID = COMPANY_ID;
    }

    public boolean isRent() {
        return isRent;
    }

    public void setRent(boolean rent) {
        isRent = rent;
    }
}
