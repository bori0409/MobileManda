package com.example.myapplicationaaaa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.math.RoundingMode;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    public static final String UIDS = "uis";
    private static final String roomstag = "MYROOMS";
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getAndShowAllFreeRooms();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // updateUI(currentUser);
        Log.d(roomstag, "current user" + currentUser);
       /* if (currentUser==null){
            Log.d(roomstag,"current user is null logg " + currentUser);
            Intent log = new Intent(getBaseContext(),LogIn.class);
            startActivity(log);
        }*/

    }

    private void getAndShowAllFreeRooms() {
        RoomReservationService service = ApiSet.getRoomService();
        //  Call<List<Room>> getAllRooms = service.getAllRooms();
        Call<List<Room>> getFreeRoomsCall = service.getFreeRooms(Math.toIntExact(System.currentTimeMillis() / 1000));
        getFreeRoomsCall.enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful()) {
                    List<Room> allfreerooms = response.body();

                    Log.d(roomstag, allfreerooms.toString());
                    populateRecyclerView(allfreerooms);

                } else {
                    String message = " Problem" + response.code() + " " + response.message();
                    Log.d(roomstag, message);

                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                Log.e(roomstag, t.getMessage());

            }
        });
    }

    private void populateRecyclerView(List<Room> allfreerooms) {
        RecyclerView recyclerView = findViewById(R.id.mainRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewAdaptar adapter = new RecyclerViewAdaptar<>(allfreerooms);
        recyclerView.setAdapter(adapter);
        String str;
        Intent intent = getIntent();
        String UID = (String) intent.getStringExtra(UIDS);
        adapter.setOnItemClickListener(new RecyclerViewAdaptar.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object item) {
                // if (UIDS!= "uis"){
                // Room room = Room item;
                FirebaseUser currentUsers = mAuth.getCurrentUser();
                Intent info = getIntent();
                String email = info.getStringExtra("email");
                //Log.d(roomstag, email + "  is that ");

                Log.d(roomstag, "Current user is logg " + currentUsers.getUid());
                if (currentUsers == null) {
                    Log.d(roomstag, "Current user is logg " + currentUsers.getUid());
                    Intent log = new Intent(getBaseContext(), LogIn.class);
                    startActivity(log);
                } else {
                    Log.d(roomstag, "Clicked " + position + "  and position " + adapter.getItemId(position));
                    Toast.makeText(getApplicationContext(), "Click" + position + " " + adapter.getItemId(position), Toast.LENGTH_LONG);
                    Room roomchoice = new Room();
                    roomchoice.setId(position);


                    //adapter.getd(Math.toIntExact(position));
                    Intent intent = new Intent(getBaseContext(), BookingActivity.class);
                    intent.putExtra(BookingActivity.ROOM, roomchoice);
                    FirebaseUser user = mAuth.getCurrentUser();
                    Log.d(roomstag, "user " + user);


                    startActivity(intent);
                }
               /* else {
                    Log.d(roomstag,mAuth.getUid()+"  is that ");
                    Intent log = new Intent(getBaseContext(),LogIn.class);

                    startActivity(log);
                }*/
            }

            //  }
        });


    }
}