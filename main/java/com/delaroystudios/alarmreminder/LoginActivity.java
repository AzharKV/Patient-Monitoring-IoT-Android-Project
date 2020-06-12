package com.delaroystudios.alarmreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.delaroystudios.alarmreminder.data.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button logbut;
    EditText Uname,Pass;
    FirebaseFirestore firebaseFirestore;
    ShowProgress showProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseFirestore=FirebaseFirestore.getInstance();



        logbut=findViewById(R.id.button);
        Uname=findViewById(R.id.editText);
        Pass=findViewById(R.id.editText2);
        logbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Uname.getText().toString().equals("")) {



                    if (!Pass.getText().toString().equals("")) {



                        showProgress=new ShowProgress();

                        showProgress.show(getSupportFragmentManager(),"gh");


                        firebaseFirestore.collection("Users").get().addOnCompleteListener(LoginActivity.this, new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                showProgress.dismiss();

                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    // Log.e(TAG, document.getId() + " => " + document.getData());

                                    Map<String, Object> logindata = document.getData();

                                    if (logindata != null) {

                                        if (logindata.containsKey("name")) {

                                            if (logindata.containsKey("Password")) {

                                                if(logindata.get("name").toString().equals(Uname.getText().toString()))
                                                {
                                                    if(logindata.get("Password").toString().equals(Pass.getText().toString()))
                                                    {

                                                        new HealthPreference(LoginActivity.this).createLoginSession(Constants.user_key,document.getId());
                                                        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                        startActivity(i);


                                                    }

                                                    else {
                                                        Toast.makeText(LoginActivity.this,"Failure",Toast.LENGTH_SHORT).show();

                                                    }


                                                }

                                                else {
                                                    Toast.makeText(LoginActivity.this,"Failure",Toast.LENGTH_SHORT).show();

                                                }







                                            }


                                        }


                                    }
                                }






                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this,"Failure",Toast.LENGTH_SHORT).show();

                                showProgress.dismiss();

                            }
                        });






                    } else {


                        Toast.makeText(LoginActivity.this, "Enter password", Toast.LENGTH_SHORT).show();

                    }


                } else {


                    Toast.makeText(LoginActivity.this, "Enter UserName", Toast.LENGTH_SHORT).show();

                }

            }

        });


    }
}
