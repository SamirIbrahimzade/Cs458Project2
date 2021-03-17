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
    TextView bDate;
    EditText name, city, sideEffect;
    Button submit;
    RelativeLayout progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        String[] arrayGenders = new String[]{ "male", "female", "other", "prefer not to mention"};
        String[] arrayVaccines = new String[]{ "Pfizer-BioNTech", "Moderna", "Oxford-AstraZeneca", "Sputnik V","Johnson & Johnson", "Convidicea",
                                               "BBIBP-CorV", "CoronaVac", "Covaxin", "CoviVav", "EpiVacCorona", "RBD-Dimer"};

        vaccine = findViewById(R.id.vaccine);
        gender = findViewById(R.id.gender);
        bDate = findViewById(R.id.bDate);
        name = findViewById(R.id.name);
        city = findViewById(R.id.city);
        sideEffect = findViewById(R.id.sideEffect);
        submit = findViewById(R.id.submitBtn);
        progressBar = findViewById(R.id.surveyPB);


        ArrayAdapter<String> adapterVac = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayVaccines);
        ArrayAdapter<String> adapterGen = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayGenders);

        adapterGen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterVac.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        vaccine.setAdapter(adapterVac);
        gender.setAdapter(adapterGen);


        bDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dateDialog = new DatePickerDialog(SurveyActivity.this, datePickerListener, mYear, mMonth, mDay);
                dateDialog.getDatePicker().setMaxDate(new Date().getTime());
                dateDialog.show();
            }
        });


        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!name.getText().toString().equals("") &&
                        !city.getText().toString().equals("") &&
                        !sideEffect.getText().toString().equals("")) submit.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        name.addTextChangedListener(textWatcher);
        city.addTextChangedListener(textWatcher);
        sideEffect.addTextChangedListener(textWatcher);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> entry = new HashMap<>();

                entry.put("name",name.getText().toString());
                entry.put("bDate",bDate.getText().toString());
                entry.put("city",city.getText().toString());
                entry.put("gender",gender.getSelectedItem().toString());
                entry.put("vaccine",vaccine.getSelectedItem().toString());
                entry.put("sideEffect",sideEffect.getText().toString());

                Random rnd = new Random();
                int ID = rnd.nextInt(999999);
                final String id = String.format("%06d",ID);

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
                                Toast.makeText(getApplicationContext(),"Connection problem",Toast.LENGTH_LONG).show();
                            }
                        });


            }
        });


    }
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, day);
            String format = new SimpleDateFormat("dd MMM YYYY").format(c.getTime());
            bDate.setText(format);
            //tvAge.setText(Integer.toString(calculateAge(c.getTimeInMillis())));
        }
    };
}