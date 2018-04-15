package com.dhaval.enerbet;

public class energy_class {

    String timestamp;
    int mac;
    float energy;



    public energy_class(){}

    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String time) {
        this.timestamp = time;
    }

    public float getEnergy() {
        return energy;
    }
    public void setEnergy(float energy) {
        this.energy = energy;
    }

    public int getMac() {
        return mac;
    }
    public void setMac(int mac) {
        this.mac = mac;
    }

}
