package android.example.vaccinesurvey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity {

    ArrayList<String> mName = new ArrayList<>();
    ArrayList<String> mBDate = new ArrayList<>();
    ArrayList<String> mCity = new ArrayList<>();
    ArrayList<String> mGender = new ArrayList<>();
    ArrayList<String> mVaccine = new ArrayList<>();
    ArrayList<String> mSideEffects = new ArrayList<>();
    RelativeLayout progressBar;
    TextView text0,text1,text2,text3,text4,text5,text6,text7,text8,text9,text10,text11;

     int[] vaccineCounts = {0,0,0,0,0,0,0,0,0,0,0,0};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);


        progressBar = findViewById(R.id.resultsPB);
        getEntries();
        /*
                                if(documentSnapshot.getData().get("vaccine").toString().equals("Pfizer-BioNTech"))vaccineCounts[0] = vaccineCounts[0]+1;
                                if(documentSnapshot.getData().get("vaccine").toString().equals("Moderna"))vaccineCounts[1] = vaccineCounts[1]+1;
                                if(documentSnapshot.getData().get("vaccine").toString().equals("Oxford-AstraZeneca"))vaccineCounts[2] = vaccineCounts[2]+1;
                                if(documentSnapshot.getData().get("vaccine").toString().equals("Sputnik V"))vaccineCounts[3] = vaccineCounts[3]+1;
                                if(documentSnapshot.getData().get("vaccine").toString().equals("Johnson & Johnson"))vaccineCounts[4] = vaccineCounts[4]+1;
                                if(documentSnapshot.getData().get("vaccine").toString().equals("Convidicea"))vaccineCounts[5] = vaccineCounts[5]+1;
                                if(documentSnapshot.getData().get("vaccine").toString().equals("BBIBP-CorV"))vaccineCounts[6] = vaccineCounts[6]+1;
                                if(documentSnapshot.getData().get("vaccine").toString().equals("CoronaVac"))vaccineCounts[7] = vaccineCounts[7]+1;
                                if(documentSnapshot.getData().get("vaccine").toString().equals("Covaxin"))vaccineCounts[8] = vaccineCounts[8]+1;
                                if(documentSnapshot.getData().get("vaccine").toString().equals("CoviVav"))vaccineCounts[9] = vaccineCounts[9]+1;
                                if(documentSnapshot.getData().get("vaccine").toString().equals("EpiVacCorona"))vaccineCounts[10] = vaccineCounts[10]+1;
                                if(documentSnapshot.getData().get("vaccine").toString().equals("RBD-Dimer"))vaccineCounts[11] = vaccineCounts[11]+1;

         */

        text0 = findViewById(R.id.text0);
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        text4 = findViewById(R.id.text4);
        text5 = findViewById(R.id.text5);
        text6 = findViewById(R.id.text6);
        text7 = findViewById(R.id.text7);
        text8 = findViewById(R.id.text8);
        text9 = findViewById(R.id.text9);
        text10 = findViewById(R.id.text10);
        text11 = findViewById(R.id.text11);





    }

    private void getEntries(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        progressBar.setVisibility(View.VISIBLE);
        db.collection("Entries")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        progressBar.setVisibility(View.GONE);

                        if(task.isSuccessful()){

                            for (QueryDocumentSnapshot documentSnapshot: task.getResult()){


                                mName.add(documentSnapshot.getData().get("name").toString());
                                mBDate.add(documentSnapshot.getData().get("bDate").toString());
                                mCity.add(documentSnapshot.getData().get("city").toString());
                                mGender.add(documentSnapshot.getData().get("gender").toString());
                                mVaccine.add(documentSnapshot.getData().get("vaccine").toString());
                                mSideEffects.add(documentSnapshot.getData().get("sideEffect").toString());


                                if(documentSnapshot.getData().get("vaccine").toString().equals("Pfizer-BioNTech"))vaccineCounts[0] = vaccineCounts[0]+1;
                                if(documentSnapshot.getData().get("vaccine").toString().equals("Moderna"))vaccineCounts[1] = vaccineCounts[1]+1;
                                if(documentSnapshot.getData().get("vaccine").toString().equals("Oxford-AstraZeneca"))vaccineCounts[2] = vaccineCounts[2]+1;
                                if(documentSnapshot.getData().get("vaccine").toString().equals("Sputnik V"))vaccineCounts[3] = vaccineCounts[3]+1;
                                if(documentSnapshot.getData().get("vaccine").toString().equals("Johnson & Johnson"))vaccineCounts[4] = vaccineCounts[4]+1;
                                if(documentSnapshot.getData().get("vaccine").toString().equals("Convidicea"))vaccineCounts[5] = vaccineCounts[5]+1;
                                if(documentSnapshot.getData().get("vaccine").toString().equals("BBIBP-CorV"))vaccineCounts[6] = vaccineCounts[6]+1;
                                if(documentSnapshot.getData().get("vaccine").toString().equals("CoronaVac"))vaccineCounts[7] = vaccineCounts[7]+1;
                                if(documentSnapshot.getData().get("vaccine").toString().equals("Covaxin"))vaccineCounts[8] = vaccineCounts[8]+1;
                                if(documentSnapshot.getData().get("vaccine").toString().equals("CoviVav"))vaccineCounts[9] = vaccineCounts[9]+1;
                                if(documentSnapshot.getData().get("vaccine").toString().equals("EpiVacCorona"))vaccineCounts[10] = vaccineCounts[10]+1;
                                if(documentSnapshot.getData().get("vaccine").toString().equals("RBD-Dimer"))vaccineCounts[11] = vaccineCounts[11]+1;



                            }


                            text0.setText("Pfizer-BioNTech ==> " + vaccineCounts[0]);
                            text1.setText("Moderna ==> "+ vaccineCounts[1]);
                            text2.setText("Oxford-AstraZeneca ==> "+ vaccineCounts[2]);
                            text3.setText("Sputnik  ==> "+ vaccineCounts[3]);
                            text4.setText("Johnson & Johnson ==> "+ vaccineCounts[4]);
                            text5.setText("Convidicea ==> "+ vaccineCounts[5]);
                            text6.setText("BBIBP-CorV ==> "+ vaccineCounts[6]);
                            text7.setText("CoronaVac ==> "+ vaccineCounts[7]);
                            text8.setText("Covaxin ==> "+ vaccineCounts[8]);
                            text9.setText("CoviVav ==> "+ vaccineCounts[9]);
                            text10.setText("EpiVacCorona ==> "+ vaccineCounts[10]);
                            text11.setText("RBD-Dimer ==> "+ vaccineCounts[11]);


                        }

                    }
                });

    }

}