package com.example.bubbletravel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class InvitationActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int CALENDAR_ACTIVITY_REQUEST_CODE = 2;
    private Spinner spinner;
    private EditText editTextDeparting, editTextReturning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation);

        spinner = (Spinner) findViewById(R.id.spinner);

        editTextDeparting = (EditText) findViewById(R.id.editTextDeparting);
        editTextReturning = (EditText) findViewById(R.id.editTextReturning);

        editTextDeparting.setOnClickListener(this);
        editTextReturning.setOnClickListener(this);

        List<String> city = new ArrayList<>();
        city.add(0, "Destination");
        city.add("London");
        city.add("Paris");
        city.add("New York");
        city.add("Rome");
        city.add("Tokyo");
        city.add("Berlin");

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, city);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {

                if (parent.getItemAtPosition(position).equals("Destination")) {
                    //do nothing
                } else {
                    if (parent.getItemAtPosition(position).equals("London")) {
                        String city = spinner.getSelectedItem().toString();
                        Intent intent = new Intent(InvitationActivity.this, FindCityWeatherActivity.class);
                        intent.putExtra("cityKey", city);
                        startActivity(intent);
                    }
                    if (parent.getItemAtPosition(position).equals("Paris")) {
                        String city = spinner.getSelectedItem().toString();
                        Intent intent = new Intent(InvitationActivity.this, FindCityWeatherActivity.class);
                        intent.putExtra("cityKey", city);
                        startActivity(intent);
                    }
                    if (parent.getItemAtPosition(position).equals("New York")) {
                        String city = spinner.getSelectedItem().toString();
                        Intent intent = new Intent(InvitationActivity.this, FindCityWeatherActivity.class);
                        intent.putExtra("cityKey", city);
                        startActivity(intent);
                    }
                    if (parent.getItemAtPosition(position).equals("Rome")) {
                        String city = spinner.getSelectedItem().toString();
                        Intent intent = new Intent(InvitationActivity.this, FindCityWeatherActivity.class);
                        intent.putExtra("cityKey", city);
                        startActivity(intent);
                    }
                    if (parent.getItemAtPosition(position).equals("Tokyo")) {
                        String city = spinner.getSelectedItem().toString();
                        Intent intent = new Intent(InvitationActivity.this, FindCityWeatherActivity.class);
                        intent.putExtra("cityKey", city);
                        startActivity(intent);
                    }
                    if (parent.getItemAtPosition(position).equals("Berlin")) {
                        String city = spinner.getSelectedItem().toString();
                        Intent intent = new Intent(InvitationActivity.this, FindCityWeatherActivity.class);
                        intent.putExtra("cityKey", city);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //get Departing
        if (getIntent().hasExtra("editTextDeparting")) {
            String date1 = getIntent().getStringExtra("editTextDeparting");
            editTextDeparting.setText(date1);
        }

        if (getIntent().hasExtra("editTextReturning")) {
            //get Returning
            Intent incomingIntent2 = getIntent();
            String date2 = incomingIntent2.getStringExtra("editTextReturning");
            editTextReturning.setText(date2);
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.editTextDeparting:
            case R.id.editTextReturning:

                Calendar cal = Calendar.getInstance();

                DatePickerDialog datePickerDialog = new DatePickerDialog(this, (v, year, monthOfYear, dayOfMonth) -> {
                    String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                    (view.getId() == R.id.editTextDeparting ? editTextDeparting : editTextReturning).setText(selectedDate);
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
                datePickerDialog.show();

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case CALENDAR_ACTIVITY_REQUEST_CODE:
                String dateString = data.getStringExtra("editTextDeparting");
                editTextDeparting.setText(dateString);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
