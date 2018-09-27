package com.example.ireribrian.compscience;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Home extends AppCompatActivity {

    private CardView mondayCard;
    private CardView tuesdayCard;
    private CardView wednesdayCard;
    private CardView thursdayCard;
    private CardView fridayCard;
    private FloatingActionButton chatBtn;
    private FloatingActionButton callBtn;
    private EditText usernamepop;
    private EditText emailpop;
    private EditText passwordText;
    private Dialog mDialog;
    private Button registerpop;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mDialog = new Dialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("comrades");

        mondayCard = (CardView) findViewById(R.id.mondayCard);
        tuesdayCard = (CardView) findViewById(R.id.tuesdayCard);
        wednesdayCard = (CardView) findViewById(R.id.wednesdayCard);
        thursdayCard = (CardView) findViewById(R.id.thursdayCard);
        fridayCard = (CardView) findViewById(R.id.fridayCard);
        chatBtn = (FloatingActionButton) findViewById(R.id.chat);
        callBtn = (FloatingActionButton) findViewById(R.id.call);

        progressDialog = new ProgressDialog(this);
        mondayCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent mainIntent = new Intent(Home.this, Monday.class);
                Home.this.startActivity(mainIntent);
            }
        });

        tuesdayCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent mainIntent = new Intent(Home.this, Tuesday.class);
                Home.this.startActivity(mainIntent);
            }
        });

        wednesdayCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent mainIntent = new Intent(Home.this, Wednesday.class);
                Home.this.startActivity(mainIntent);
            }
        });

        thursdayCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent mainIntent = new Intent(Home.this, Wednesday.class);
                Home.this.startActivity(mainIntent);
            }
        });

        fridayCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent mainIntent = new Intent(Home.this, Wednesday.class);
                Home.this.startActivity(mainIntent);
            }
        });



        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:0791205989"));
                if (ActivityCompat.checkSelfPermission(Home.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(Home.this,
                            Manifest.permission.CALL_PHONE)) {
                        ActivityCompat.requestPermissions(Home.this,
                                new String[]{Manifest.permission.CALL_PHONE}, 1);
                    } else {
                        ActivityCompat.requestPermissions(Home.this,
                                new String[]{Manifest.permission.CALL_PHONE}, 1);
                    }
                    return;
                }
                startActivity(callIntent);
            }

        });

    }

    public void showPopup(View v){

        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplication(), ClassGroup.class));
        } else {



        TextView txtClose;
        mDialog.setContentView(R.layout.registerpop);
        txtClose = (TextView) mDialog.findViewById(R.id.closedialog);

        usernamepop = (EditText)mDialog.findViewById(R.id.usernameText);
        emailpop = (EditText)mDialog.findViewById(R.id.emailText);
        passwordText = (EditText) mDialog.findViewById(R.id.passwordText);

        registerpop = (Button) mDialog.findViewById(R.id.registerButton);
        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });

        registerpop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = usernamepop.getText().toString().trim();
                final String email = emailpop.getText().toString().trim();
                String password = passwordText.getText().toString().trim();

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(Home.this, "Please enter username", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Home.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Home.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.setMessage("Signing Up....");
                progressDialog.show();

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Home.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    String user_id = firebaseAuth.getCurrentUser().getUid();
                                    DatabaseReference current_user_db = mDatabase.child(user_id);
                                    current_user_db.child("username").setValue(username);

                                    Toast.makeText(Home.this, "Welcome" + " " + username , Toast.LENGTH_SHORT).show();
                                    final Intent mainIntent = new Intent(Home.this, ClassGroup.class);
                                    Home.this.startActivity(mainIntent);
                                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                } else {
                                    Toast.makeText(Home.this, "Error in Signing Up. Please try again later....", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }

                            }
                        });
            }
        });

        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();

    }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1: {
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(Home.this,
                            Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "No permission granted", Toast.LENGTH_SHORT).show();
                }
            }
    }


    }

}
