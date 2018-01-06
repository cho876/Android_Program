package com.example.skcho.smartcarrier;

/**
 * Created by skCho on 2017-12-28.
 * <p>
 * Shopping Cart Information DAO
 */

public class CartDAO {
    private String speed;       // Cart speed
    private String distance;   //  Distance between cart and yourself
    private String weight;    // Weight inside cart

    public String getDistance() {
        return distance;
    }

    public String getSpeed() {
        return speed;
    }

    public String getWeight() {
        return weight;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
