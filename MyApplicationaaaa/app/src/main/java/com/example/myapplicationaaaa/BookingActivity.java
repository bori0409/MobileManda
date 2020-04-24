package com.example.myapplicationaaaa;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    public static final String ROOM = "room";
    DatePickerDialog picker;
    Calendar calendar;
    int currentHour;
    int currentMinute;
    TimePickerDialog timePickerDialog;
    String amPm;
    int timeint;

    private static final String tag = "OPLOG";
    private static final String tag2 = "OPLOGY";
    private static final String tag3 = "TIMEFUCK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        intent.getExtras();

        Room room = (Room) intent.getSerializableExtra(ROOM);

        Log.d(tag2, "something NOW INI");
        Log.d(tag, " " + room.getId());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking);

        EditText fromTime = (EditText) findViewById(R.id.editText);
        fromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(BookingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {

                        fromTime.setText(String.format("%02d:%02d", hourOfDay, minutes));
                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();
            }
        });
        EditText toTime = (EditText) findViewById(R.id.editText4);
        toTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(BookingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {

                        toTime.setText(String.format("%02d:%02d", hourOfDay, minutes));
                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();
            }
        });
        EditText datetime = (EditText) findViewById(R.id.editText3);
        datetime.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(BookingActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                datetime.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                            }
                        }, year, month, day);
                picker.show();
            }
        }));

    }
    // EditText reason = findViewById(R.id.editText7);

    // Room room= new Room();
    public void makingTheBooking(View view) throws ParseException, JSONException {
        //RoomReservationService service = ApiSet.createReserstr();
        RoomReservationService service = ApiSet.createReser();
        Log.d(tag2, "button CLICKED MF");
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUsers = mAuth.getCurrentUser();

        Log.d(tag2, "Here " + currentUsers.getUid());
        Log.d(tag, "We are inside the method now");
        EditText datetime = (EditText) findViewById(R.id.editText3);
        EditText fromTime = (EditText) findViewById(R.id.editText);
        EditText toTime = (EditText) findViewById(R.id.editText4);
        EditText reason = (EditText) findViewById(R.id.reason);
        // Date to Epoch for FROM
        String datestringfrom = " " + datetime.getText() + " " + fromTime.getText();

        // geting the date FROM
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date datefrom = df.parse(datestringfrom);
        long epochwitht = datefrom.getTime();
        long realepochfrom = epochwitht / 1000;
        //Date to Epoch for TO


        String datestringto = " " + datetime.getText() + " " + toTime.getText();

        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date dateto = df2.parse(datestringto);
        long epochwitht2 = dateto.getTime();
        long realepochto = epochwitht2 / 1000;

        Log.d(tag3, "Time before EPACH FROM " + datetime.getText() + " " + fromTime.getText() + " TIME before epoach TO" + datetime.getText() + " " + toTime.getText());

        Log.d(tag, "dataformat" + datestringfrom);
        Log.w(tag, "dataformat" + datestringto);
        Log.d(tag2, "something");


        Log.d(tag, "Tova" + realepochfrom);
        //  Toast.makeText(getApplicationContext(),"Your booking is on:" + datetime.getText()+"  from / to "+fromTime.getText()+" / "+ toTime.getText() + reason.getText(),Toast.LENGTH_SHORT).show();
//CreateBooking();


        String reasome = reason.getText().toString();
        //RoomReservationService service = ApiSet.getRoomService();
        Reservation reservation = new Reservation();
        Intent intent = getIntent();
        intent.getExtras();

        Room room = (Room) intent.getSerializableExtra(ROOM);
        Log.d(tag, "Room should be this " + room.getName() + "  " + room.getId());
        //Creating the reservation model
        //JSONObject jsonObject;
        reservation.setPurpose(reasome);
        reservation.setFromTime(Math.toIntExact(realepochfrom));
        reservation.setToTime(Math.toIntExact(realepochto));
        reservation.setUserId(currentUsers.getUid());
        reservation.setRoomId(room.getId());

        reservation.setId(2);
        Log.d(tag, "OBJECT THAT IM SENDING IS " + reservation.getRoomId() + " " + reservation.getFromTime() + " TO " + reservation.getToTime());

        //  reservation.setRoomId(room.getId());
        //String userId = "56B";
        /*String jsonString = new JSONObject()
                .put("id", "56")
                .put("fromTime",realepochfrom )
                .put("toTime", realepochto)
                .put("userId",userId)
                .put("purpose",reasome)
                .put("roomId",room.getId())
                .toString();*/

        //Need to get the user id

        //reservation.setUserId(userId);
        //just to set the id
        // reservation.setId(556);
        //reservation
        Log.d(tag2, "something2");
        Call<Reservation> call = service.createReser(reservation);

        Log.d(tag2, "something3");
        Log.d(tag3, "OBJECT NEW" + reservation.toString());
        // kinda working

        call.enqueue(new Callback<Reservation>() {
            @Override
            public void onResponse(Call<Reservation> call, Response<Reservation> response) {
                Log.d(tag2,"something4");
                if (response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Reservation made successfully",Toast.LENGTH_LONG).show();
                    Log.d(tag,"This is in the IF" + response.code());
                   // finish();
                    Log.d(tag2,"something5");
                }else {
                    Log.d(tag,"This is in the ELSE" + response.code() + response.message());
                    Log.d(tag2, response.message());
                    Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG);
                    Toast.makeText(getApplicationContext(), "OPPS ERROR", Toast.LENGTH_SHORT);
                    Log.d(tag2,"object" +reservation.toString());
                }
            }

            @Override
            public void onFailure(Call<Reservation> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                Log.d(tag,"It is wrong AGAIG ON FAILURE ");
               Log.d(tag,"this is tghe  " + t.getMessage() +"    " + t.toString()+ "    "+t.getCause());
                Log.d(tag,"this is the object  " + reservation.getPurpose().toString() + " "+ reservation.getUserId());
                Log.d(tag ,reservation.toString());
                finish();
                Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);


                //

            }
        });
/*
        // TRY WITH SRTING
        JSONObject jsonString = new JSONObject();
        jsonString.put("id" , 2);
        jsonString.put("fromTime", reservation.getFromTime());
        jsonString.put("toTime", reservation.getToTime());
        jsonString.put("userId", reservation.getUserId());
        jsonString.put("purpose", reservation.getPurpose());
        jsonString.put("roomId", reservation.getRoomId());

        //Json try 2
        Call<String> stringCall = service.createReserstr(jsonString.toString());
       stringCall.enqueue(new Callback<String>() {
           @Override
           public void onResponse(Call<String> call, Response<String> response) {
               if(response.isSuccessful()){Toast.makeText(getApplicationContext(),"Reservation made successfully",Toast.LENGTH_LONG).show();
                   Log.d(tag,"This is in the IF" + response.code());
                    finish();

               }
               else {Log.d(tag,"This is in the ELSE" + response.code() + response.message());
                   Log.d(tag2, response.message());
                   Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG);
                   Log.d(tag," json " + jsonString.toString());

               }
           }

           @Override
           public void onFailure(Call<String> call, Throwable t) {

           }
       });
*/
    }
}


