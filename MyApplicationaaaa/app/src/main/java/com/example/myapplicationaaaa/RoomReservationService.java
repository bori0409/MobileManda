package com.example.myapplicationaaaa;

import java.lang.reflect.Array;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RoomReservationService {
    @GET("Rooms/free/{time}")
    Call<List<Room>> getFreeRooms (@Path("time") int time);
    @GET("Rooms")
    Call<List<Room>> getAllRooms();
    @POST("reservations")
    Call<Reservation> createReser(@Body Reservation reservation);
    @POST ("reservations")
    Call<String> createReserstr(String string);

}

