package com.example.bubbletravel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private TextView textViewUserEmail;
    private Button buttonLogout;
    private  Button buttonInvitation;

    private ImageView imageViewProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()== null)
        {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewUserEmail = (TextView)findViewById(R.id.textViewUserEmail);
        imageViewProfile = (ImageView)findViewById(R.id.imageViewProfile);

        int imageResource = getResources().getIdentifier("@drawable/image_profile",null,this.getPackageName());
        imageViewProfile.setImageResource(imageResource);

        textViewUserEmail.setText(user.getEmail());

        buttonLogout = (Button)findViewById(R.id.buttonLogout);
        buttonInvitation = (Button)findViewById(R.id.buttonInvitation);

        buttonLogout.setOnClickListener(this);
        buttonInvitation.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(view == buttonLogout)
        {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));

        }
        if(view == buttonInvitation)
        {
            finish();
            startActivity(new Intent(this, InvitationActivity.class));
        }
    }
}
