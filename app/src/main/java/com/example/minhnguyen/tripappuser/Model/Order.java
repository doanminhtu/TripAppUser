package com.example.minhnguyen.tripappuser.Model;

public class Order {
    private String PlaceID;
    private String PlaceName;
    private String Address;

    public Order(){
    }

    public Order(String placeID, String placeName, String address) {
        PlaceID = placeID;
        PlaceName = placeName;
        Address = address;
    }

    public String getPlaceID() {
        return PlaceID;
    }

    public void setPlaceID(String placeID) {
        PlaceID = placeID;
    }

    public String getPlaceName() {
        return PlaceName;
    }

    public void setPlaceName(String placeName) {
        PlaceName = placeName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
