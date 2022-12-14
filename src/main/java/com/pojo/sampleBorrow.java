package com.pojo;

public class sampleBorrow {
    private int borrowID;
    private int sampleID;
    private String belongUnit;
    private int userID;
    private String borrowTime;
    private String returnTime;
    private String borrowMan;
    private String borrowReason;
    private String inState;
    private String outState;
    private String borrowAddress;
    private String sampleName;


    public String getInState() {
        return inState;
    }

    public String getBorrowAddress() {
        return borrowAddress;
    }

    public void setBorrowAddress(String borrowAddress) {
        this.borrowAddress = borrowAddress;
    }

    public void setInState(String inState) {
        this.inState = inState;
    }

    public String getOutState() {
        return outState;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public void setOutState(String outState) {
        this.outState = outState;
    }

    public int getBorrowID() {
        return borrowID;
    }

    public void setBorrowID(int borrowID) {
        this.borrowID = borrowID;
    }

    public int getSampleID() {
        return sampleID;
    }

    public void setSampleID(int sampleID) {
        this.sampleID = sampleID;
    }

    public String getBelongUnit() {
        return belongUnit;
    }

    public void setBelongUnit(String belongUnit) {
        this.belongUnit = belongUnit;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(String borrowTime) {
        this.borrowTime = borrowTime;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public String getBorrowMan() {
        return borrowMan;
    }

    public void setBorrowMan(String borrowMan) {
        this.borrowMan = borrowMan;
    }

    public String getBorrowReason() {
        return borrowReason;
    }

    public void setBorrowReason(String borrowReason) {
        this.borrowReason = borrowReason;
    }

    public String getBorrowTele() {
        return borrowTele;
    }

    public void setBorrowTele(String borrowTele) {
        this.borrowTele = borrowTele;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    private String borrowTele;
    private String remarks;

}
