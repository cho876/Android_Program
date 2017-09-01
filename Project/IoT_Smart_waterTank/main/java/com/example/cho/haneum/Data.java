package com.example.cho.haneum;

/**
 * 희망 / 현재 온도, 탁도, 수위, 히터, 급수, 배수 상태 Data
 * Getter / Setter
 */
public class Data {
    private String setTemp;
    private String setTurb;
    private String curTemp;
    private String curTurb;
    private String ctlLevel;
    private String ctlHeat;
    private String ctlIn;
    private String ctlOut;

    public String getCtlHeat() {
        return ctlHeat;
    }

    public String getCtlIn() {
        return ctlIn;
    }

    public String getCtlLevel() {
        return ctlLevel;
    }

    public String getCtlOut() {
        return ctlOut;
    }

    public String getCurTemp() {
        return curTemp;
    }

    public String getCurTurb() {
        return curTurb;
    }

    public String getSetTemp() {
        return setTemp;
    }

    public String getSetTurb() {
        return setTurb;
    }

    public void setCtlHeat(String ctlHeat) {
        this.ctlHeat = ctlHeat;
    }

    public void setCtlIn(String ctlIn) {
        this.ctlIn = ctlIn;
    }

    public void setCtlLevel(String ctlLevel) {
        this.ctlLevel = ctlLevel;
    }

    public void setCtlOut(String ctlOut) {
        this.ctlOut = ctlOut;
    }

    public void setCurTemp(String curTemp) {
        this.curTemp = curTemp;
    }

    public void setCurTurb(String curTurb) {
        this.curTurb = curTurb;
    }

    public void setSetTemp(String setTemp) {
        this.setTemp = setTemp;
    }

    public void setSetTurb(String setTurb) {
        this.setTurb = setTurb;
    }
}
