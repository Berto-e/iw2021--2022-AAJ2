package com.example.application.views.dashboard;

/**
 * Simple DTO class for the inbox list to demonstrate complex object data
 */
public class ServiceHealth {

    private String city;

    private int input;

    private int output;

    private String theme;

    enum Status {
        EXCELLENT, OK, FAILING;
    }

    public ServiceHealth() {

    }

    public ServiceHealth(String city, int input, int output) {
        this.city = city;
        this.input = input;
        this.output = output;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getInput() {
        return input;
    }

    public void setInput(int input) {
        this.input = input;
    }

    public int getOutput() {
        return output;
    }

    public void setOutput(int output) {
        this.output = output;
    }

}
