package android.example.vaccinesurvey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SurveyActivity extends AppCompatActivity {

    Spinner vaccine, gender;
    EditText name, city, sideEffect, day, month, year;
    Button submit;
    RelativeLayout progressBar;
    String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        String[] arrayGenders = new String[]{ "male", "female", "other", "prefer not to mention"};
        String[] arrayVaccines = new String[]{ "Pfizer-BioNTech", "Moderna", "Oxford-AstraZeneca", "Sputnik V","Johnson & Johnson", "Convidicea",
                                               "BBIBP-CorV", "CoronaVac", "Covaxin", "CoviVav", "EpiVacCorona", "RBD-Dimer"};

        vaccine = findViewById(R.id.vaccine);
        gender = findViewById(R.id.gender);
        name = findViewById(R.id.name);
        city = findViewById(R.id.city);
        sideEffect = findViewById(R.id.sideEffect);
        submit = findViewById(R.id.submitBtn);
        progressBar = findViewById(R.id.surveyPB);
        day = findViewById(R.id.day);
        month = findViewById(R.id.month);
        year = findViewById(R.id.year);


        ArrayAdapter<String> adapterVac = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayVaccines);
        ArrayAdapter<String> adapterGen = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.dropdownOption, arrayGenders);

        adapterGen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterVac.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        vaccine.setAdapter(adapterVac);
        gender.setAdapter(adapterGen);





        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!name.getText().toString().equals("") &&
                        !city.getText().toString().equals("") &&
                        !sideEffect.getText().toString().equals("") &&
                        !day.getText().toString().equals("") &&
                        !month.getText().toString().equals("") &&
                        !year.getText().toString().equals("")
                ) submit.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        name.addTextChangedListener(textWatcher);
        city.addTextChangedListener(textWatcher);
        sideEffect.addTextChangedListener(textWatcher);
        day.addTextChangedListener(textWatcher);
        month.addTextChangedListener(textWatcher);
        year.addTextChangedListener(textWatcher);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameStr, cityStr, sideEffectStr, dayStr, monthStr, yearStr;

                dayStr = day.getText().toString();
                monthStr = month.getText().toString();
                yearStr = year.getText().toString();

                date = dayStr+"/"+monthStr+"/"+yearStr;
                nameStr = name.getText().toString();
                cityStr = city.getText().toString();
                sideEffectStr = sideEffect.getText().toString();

                if(checkName(nameStr) && checkCity(cityStr) && checkSideEffects(sideEffectStr) && checkDate(dayStr, monthStr, yearStr) ) {

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    Map<String, Object> entry = new HashMap<>();

                    entry.put("name", nameStr);
                    entry.put("bDate", date);
                    entry.put("city", cityStr);
                    entry.put("gender", gender.getSelectedItem().toString());
                    entry.put("vaccine", vaccine.getSelectedItem().toString());
                    entry.put("sideEffect", sideEffectStr);

                    Random rnd = new Random();
                    int ID = rnd.nextInt(999999);
                    final String id = String.format("%06d", ID);

                    progressBar.setVisibility(View.VISIBLE);
                    db.collection("Entries").document(id)
                            .set(entry)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    progressBar.setVisibility(View.GONE);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), "Connection problem", Toast.LENGTH_LONG).show();
                                }
                            });

                }//end of if
            }
        });
    }

    private boolean checkName(String name){

        for( int i = 0; i < name.length(); i++){
            if(name.charAt(i) == '1' || name.charAt(i) == '2' || name.charAt(i) == '3' ||
                    name.charAt(i) == '4' || name.charAt(i) == '5' || name.charAt(i) == '6' ||
                    name.charAt(i) == '7' || name.charAt(i) == '8' || name.charAt(i) == '9' ||
                    name.charAt(i) == '0'){
                Toast.makeText(getApplicationContext(), "Name cannot contain numbers", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        return  true;
    }

    private boolean checkCity(String city){

        for( int i = 0; i < city.length(); i++){
            if(city.charAt(i) == '1' || city.charAt(i) == '2' || city.charAt(i) == '3' ||
                    city.charAt(i) == '4' || city.charAt(i) == '5' || city.charAt(i) == '6' ||
                    city.charAt(i) == '7' || city.charAt(i) == '8' || city.charAt(i) == '9' ||
                    city.charAt(i) == '0'){
                Toast.makeText(getApplicationContext(), "City cannot contain numbers", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return  true;
    }

    private boolean checkSideEffects(String sideEffect){

        if(sideEffect.length() < 3){
            Toast.makeText(getApplicationContext(), "Side effect description cannot have less than 3 characters", Toast.LENGTH_LONG).show();
            return false;
        }
        if(sideEffect.length() > 50){
            Toast.makeText(getApplicationContext(), "Side effect description cannot have more than 50 characters", Toast.LENGTH_LONG).show();
            return false;
        }
        return  true;
    }

    private boolean checkDate(String day,String month, String year){

        for( int i = 0; i < day.length(); i++){
            if(     day.charAt(i) != '1' && day.charAt(i) != '2' && day.charAt(i) != '3' &&
                    day.charAt(i) != '4' && day.charAt(i) != '5' && day.charAt(i) != '6' &&
                    day.charAt(i) != '7' && day.charAt(i) != '8' && day.charAt(i) != '9' &&
                    day.charAt(i) != '0'){
                Toast.makeText(getApplicationContext(), "Day cannot contain letters", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        for( int i = 0; i < month.length(); i++){
            if(     month.charAt(i) != '1' && month.charAt(i) != '2' && month.charAt(i) != '3' &&
                    month.charAt(i) != '4' && month.charAt(i) != '5' && month.charAt(i) != '6' &&
                    month.charAt(i) != '7' && month.charAt(i) != '8' && month.charAt(i) != '9' &&
                    month.charAt(i) != '0'){
                Toast.makeText(getApplicationContext(), "Month cannot contain letters", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        for( int i = 0; i < year.length(); i++){
            if(     year.charAt(i) != '1' && year.charAt(i) != '2' && year.charAt(i) != '3' &&
                    year.charAt(i) != '4' && year.charAt(i) != '5' && year.charAt(i) != '6' &&
                    year.charAt(i) != '7' && year.charAt(i) != '8' && year.charAt(i) != '9' &&
                    year.charAt(i) != '0'){
                Toast.makeText(getApplicationContext(), "Year cannot contain letters", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        int dayInt, monthInt, yearInt;

        dayInt = Integer.parseInt(day);
        monthInt = Integer.parseInt(month);
        yearInt = Integer.parseInt(year);

        if(dayInt < 1 || dayInt > 31 ){
            Toast.makeText(getApplicationContext(), "Day is not valid", Toast.LENGTH_LONG).show();
            return false;
        }
        if(monthInt < 1 || monthInt > 12 ){
            Toast.makeText(getApplicationContext(), "Month is not valid", Toast.LENGTH_LONG).show();
            return false;
        }
        if(yearInt < 1900 || yearInt > 2021 ){
            Toast.makeText(getApplicationContext(), "Year is not valid", Toast.LENGTH_LONG).show();
            return false;
        }

        if(yearInt > 2006){
            Toast.makeText(getApplicationContext(), "The person below age 15 cannot take the survey", Toast.LENGTH_LONG).show();
            return false;
        }

        return  true;
    }


}
