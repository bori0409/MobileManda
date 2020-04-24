package com.example.myapplicationaaaa;

import java.lang.reflect.Array;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RoomReservationService {
    @GET("Rooms/free/{time}")
    Call<List<Room>> getFreeRooms (@Path("time") int time);
    @GET("Reservations/{reservertionId}")
    Call<Reservation> getMyOneReservation (@Path("reservertionId") int Id);
    @GET("Rooms")
    Call<List<Room>> getAllRooms();
    @GET("Rooms/{id}")
    Call<Room> getOneRoom(@Path("id") int Id);
   @Headers("Content-Type: application/json-patch+json")
    @POST("reservations")
    Call<Reservation> createReser(@Body Reservation reservation);
    @Headers("Content-Type: application/json")
    @POST ("Reservations")
    Call<String> createReserstr(@Body String reservation);
    @DELETE("Reservations/{id}")
    Call<Reservation> deleteOne (@Path("id") int Id);
    @GET("Reservations/user/{userId}")
    Call<List<Reservation>> getMyReservations  (@Path("userId") String userId);
//("userId")) string userId
}

