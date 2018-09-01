package com.example.minhnguyen.tripappuser.Model;

import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class CusJourney {
   private String phone;
   private String name;
   private String tourname;
   private List<Order> places;

   public CusJourney(){
   }


    public CusJourney(String phone, String name, String tourname, List<Order> places) {
        this.phone = phone;
        this.name = name;
        this.tourname = tourname;
        this.places = places;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTourname() {
        return tourname;
    }

    public void setTourname(String tourname) {
        this.tourname = tourname;
    }

    public List<Order> getPlaces() {
        return places;
    }

    public String getlistPlaces() {
      String textplace="";
      StringBuilder sb=new StringBuilder(100);
       for (int i=0; i< places.size()-1; i++){
           if (sb.length()+places.get(i).getPlaceName().length()>=90) {
               textplace = sb.append("...").toString();
               return textplace;
           }
           else {
               textplace = sb.append(places.get(i).getPlaceName()).toString();
               textplace = sb.append("\n").toString();
           }
       }
       int lastplace=places.size()-1;
        if (sb.length()+places.get(lastplace).getPlaceName().length()>=90)
            textplace=sb.append("...").toString();
        else
            textplace = sb.append(places.get(lastplace).getPlaceName()).toString();
       return textplace;
    }

    public void setPlaces(List<Order> places) {
        this.places = places;
    }
}
