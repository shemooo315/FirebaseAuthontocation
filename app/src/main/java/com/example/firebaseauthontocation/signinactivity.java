package com.example.firebaseauthontocation;

import static com.google.android.gms.auth.api.signin.GoogleSignInOptions.DEFAULT_SIGN_IN;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class signinactivity extends AppCompatActivity {

   private GoogleSignInClient googleSignInClient;
   private FirebaseAuth firebaseAuth;
    private static final int RC_SIGN_IN=100;
    private static final String TAG="GOOGLE_SIGN_IN_TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signinactivity);
        SignInButton btn=(SignInButton)findViewById(R.id.sign_in_button);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        googleSignInClient= GoogleSignIn.getClient(this,googleSignInOptions);
        firebaseAuth= FirebaseAuth.getInstance();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }
    private void signIn(){

        Intent signInIntent=googleSignInClient.getSignInIntent();
      startActivityForResult(signInIntent,RC_SIGN_IN);

    }
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {


        super.onActivityResult(requestCode, resultCode, data);

        if( requestCode==RC_SIGN_IN){

            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
                 try {
                     GoogleSignInAccount account=task.getResult(ApiException.class);
                     firebaseAuthWithGoogle(account);
                 }catch (ApiException e){
                     Log.w(TAG,"GOOGLE SIGN IN FAILED",e);
                 }


        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential= GoogleAuthProvider.getCredential(account.getIdToken(),null);
        firebaseAuth.signInWithCredential(credential).addOnSuccessListener(this, AuthResult->{

            startActivity(new Intent(signinactivity.this,MainActivity.class));
            finish();
        }).addOnFailureListener(this,e-> Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show());


    }
}