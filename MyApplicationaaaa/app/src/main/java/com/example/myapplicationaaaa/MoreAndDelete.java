package com.example.myapplicationaaaa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MoreAndDelete extends AppCompatActivity {
    public static final String IDRe = "  ";
    public static  Integer ID;
    private static final String more = "moretosee";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_and_delete);


        Intent intent = getIntent();
        intent.getExtras();
        Reservation reservation = (Reservation) intent.getSerializableExtra(IDRe);
        ID = reservation.getId();
        getOneReservetion();
        Reservation getone = new Reservation();
    }
public void Delete(View vie){
    RoomReservationService service1 = ApiSet.deleteOne();
    Call<Reservation> deleteOneCall= service1.deleteOne(ID);
    deleteOneCall.enqueue(new Callback<Reservation>() {
        @Override
        public void onResponse(Call<Reservation> call, Response<Reservation> response) {
            if (response.isSuccessful()) {
                Log.d(more, " " + response.code());
                Toast.makeText(getApplicationContext()," Deleted succsessfully, Redirecting...", Toast.LENGTH_SHORT);
                finish();
                Intent intent = new Intent(getApplicationContext(),CheckingReserv.class);
                startActivity(intent);
            }else { Toast.makeText(getApplicationContext()," Sorr, something went wrong...", Toast.LENGTH_SHORT);

                Log.d(more, " ELSE" + response.code());
            }
        }

        @Override
        public void onFailure(Call<Reservation> call, Throwable t) {
            Log.d(more, " Fail " + t.getMessage());
            finish();
            Intent intent = new Intent(getApplicationContext(),CheckingReserv.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext()," Deleted succsessfully, Redirecting...", Toast.LENGTH_SHORT);
        }
    });
}
    public void getOneReservetion(){
        RoomReservationService service = ApiSet.getMyOneReservation();
        Call<Reservation> getMyReservationCall = service.getMyOneReservation(ID);
        getMyReservationCall.enqueue(new Callback<Reservation>() {
            @Override
            public void onResponse(Call<Reservation> call, Response<Reservation> response) {
                if (response.isSuccessful()) {

                    Reservation myreservation =response.body();
                    Log.d(more, " my object " + myreservation.getId() + " "+ myreservation.getToTime() + " " + myreservation.getRoomId());
                    TextView whenfrom = findViewById(R.id.whenfrom);
                    whenfrom.setText(myreservation.getFromTime().toString());
                    TextView whento = findViewById(R.id.whento);
                    whento.setText(myreservation.getToTime().toString());
                    TextView purpose = findViewById(R.id.textView13);
                    purpose.setText(myreservation.getPurpose().toString());
                    TextView number = findViewById(R.id.textView14);
                    number.setText(myreservation.getRoomId().toString());
                }
            }

            @Override
            public void onFailure(Call<Reservation> call, Throwable t) {
                Toast.makeText(getApplicationContext()," FAILED",Toast.LENGTH_LONG);

            }


        });

    }
}
