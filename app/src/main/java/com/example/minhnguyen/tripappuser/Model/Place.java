package com.example.minhnguyen.tripappuser.Model;

public class Place {
    private String Name, Image, Description, Address, CityID;

    public Place(){

    }

    public Place(String name, String image, String description, String address, String cityID) {
        Name = name;
        Image = image;
        Description = description;
        Address = address;
        CityID = cityID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCityID() {
        return CityID;
    }

    public void setCityID(String cityID) {
        CityID = cityID;
    }
}
