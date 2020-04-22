package com.example.myapplicationaaaa;

import java.lang.annotation.Retention;

import retrofit2.Retrofit;

public class ApiSet {
    private ApiSet(){

    }
    private  static final String BASE_URL  = "http://anbo-roomreservationv3.azurewebsites.net/api/";
    public  static RoomReservationService getRoomService(){
        return RetrofitClient.getClient(BASE_URL).create(RoomReservationService.class);
    }

}
