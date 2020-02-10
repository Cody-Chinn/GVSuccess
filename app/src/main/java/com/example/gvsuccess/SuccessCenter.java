package com.example.gvsuccess;

public class SuccessCenter {
    private String successCenterCode;
    private String address;
    private boolean open;
    private int numAvailableTutors;

    public SuccessCenter(String successCenterCode,
                         String address, boolean open,
                         int numAvailableTutors) {
        this.successCenterCode = successCenterCode;
        this.address = address;
        this.open = open;
        this.numAvailableTutors = numAvailableTutors;
    }

    public String getSuccessCenterCode() {
        return successCenterCode;
    }

    public void setSuccessCenterCode(String successCenterCode) {
        this.successCenterCode = successCenterCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public int getNumAvailableTutors() {
        return numAvailableTutors;
    }

    public void setNumAvailableTutors(int numAvailableTutors) {
        this.numAvailableTutors = numAvailableTutors;
    }
}
