package com.example.myapplicationaaaa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import okhttp3.internal.Internal;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckingReserv extends AppCompatActivity {
    public static String userID = "";
    public static int count;
    private static final String reserve = "Reserve";

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checking_reserv);
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getUid();
    }

    @Override
    protected void onStart() {
        getAndShowMyReservations();
        super.onStart();
    }

    public void getAndShowMyReservations() {
        RoomReservationService service = ApiSet.getMyReservations();
        Call<List<Reservation>> getreservationsCall = service.getMyReservations(userID);
        getreservationsCall.enqueue(new Callback<List<Reservation>>() {
            @Override
            public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {
                if (response.isSuccessful()) {
                    List<Reservation> myReservations = response.body();
                    populateRecyclerView(myReservations);
                    Log.d(reserve, " body" + myReservations);

                    if (count != 0) {
                        TextView text = findViewById(R.id.reserve);
                        text.setText(" Your Reservations:");

                    } else {
                        TextView text = findViewById(R.id.reserve);
                        text.setText("   Sorry, you don't have any  reservations at the moment!  Just go back and create one!");

                    }
                }
                else {
                    Log.d(reserve, " Something went wrong" + response.code() + response.message());

                }            }

            @Override
            public void onFailure(Call<List<Reservation>> call, Throwable t) {
                Log.e(reserve, t.getMessage());
            }
        });
    }

    private void populateRecyclerView(List<Reservation> myReservations) {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewAdaptar adapter = new RecyclerViewAdaptar<>(myReservations);
        recyclerView.setAdapter(adapter);
        count = adapter.getItemCount();


        adapter.setOnItemClickListener(new RecyclerViewAdaptar.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object item) {
                String info;
                String infowhole = item.toString();
                String delims = "[ :]+";
                String[] token = infowhole.split(delims);
                Reservation reservation = new Reservation();
                Log.d(reserve, " 0: " + token[0] + " 1" + token[1] + " 2 " + token[2]);
                Intent gotomore = new Intent(getBaseContext(),MoreAndDelete.class);
                reservation.setId(Integer.valueOf(token[2]));
                gotomore.putExtra(MoreAndDelete.IDRe,reservation);
                startActivity(gotomore);
            }
        });
    }


}
