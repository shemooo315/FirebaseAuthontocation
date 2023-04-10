package com.example.firebaseauthontocation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private static final String ANONYMOUS = " NO NAME";
    private FirebaseAuth firebaseAuth;
  //  private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn=(Button)findViewById(R.id.button) ;
        TextView txt=(TextView)findViewById(R.id.textView);
        ImageView img=(ImageView)findViewById(R.id.imageView);

        firebaseAuth = FirebaseAuth.getInstance();
        String name= getUser();
        String userUrl=getPhotoUrl();

        txt.setText(name);

        btn.setOnClickListener(new View.OnClickListener() {
         @Override
            public void onClick(View view) {
              firebaseAuth.signOut();
              startActivity(new Intent(MainActivity.this,signinactivity.class));
              finish();
           }
        });


    }

    private String getUser() {

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            return user.getDisplayName();
        }

        return ANONYMOUS;

    }

    private String getPhotoUrl() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null && user.getPhotoUrl() != null) {
            return user.getPhotoUrl().toString();
        }

        return null;
    }
}