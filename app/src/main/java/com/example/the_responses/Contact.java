package com.example.the_responses;

public class Contact {

    private String name;
    private String phoneNumber;
    private boolean isSelected;

    // Constructeur
    public Contact(String name, String phoneNumber, boolean isSelected) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.isSelected = isSelected;
    }

    // Getters et Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
