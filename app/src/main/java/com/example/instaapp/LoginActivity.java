package com.example.instaapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG ="Facebook" ;
    private CallbackManager mCallbackManager;
    private LoginButton loginButton;
     FirebaseAuth mAuth;
    AccessToken accessToken;

     
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        mAuth=FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();

       ConstraintLayout loginLayout=findViewById(R.id.login);

        // Initialize Facebook Login button
         loginButton = findViewById(R.id.fblogin_button);
         loginButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this,Arrays.asList("email", "public_profile","user_friends"));
                 LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                     @Override
                     public void onSuccess(LoginResult loginResult) {

                         Log.d(TAG, "fb:onSuccess:" + loginResult);
                         handleFacebookAccessToken(loginResult.getAccessToken());
                        loginLayout.setVisibility(View.INVISIBLE);
                         Toast toast = Toast.makeText(getApplicationContext(),"Wait a Second Homepage Loading.....", Toast.LENGTH_SHORT);
                         toast.setGravity(Gravity.CENTER, 0, 0);
                         toast.show();

                     }

                     @Override
                     public void onCancel() {
                         Log.d(TAG, "facebook:onCancel");
                     }

                     @Override
                     public void onError(FacebookException error) {
                         Log.d(TAG, "fb:onError", error);
                     }
                 });
             }
         });

    }
    
    // ...
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken token)
    {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("fb Auth", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            LoginActivity.this.finish();

                        }
                        else
                            {
                            // If sign in fails, display a message to the user.
                            Log.d("fb Auth", "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            }
                    }
                });

    }



//    private void getFBFriendsList()
//    {
//        GraphRequestBatch batch = new GraphRequestBatch(
//                GraphRequest.newMyFriendsRequest(
//                        accessToken,
//                        new GraphRequest.GraphJSONArrayCallback() {
//                            @Override
//                            public void onCompleted(
//                                    JSONArray jsonArray,
//                                    GraphResponse response) {
//                                // Application code for users friends
//                                System.out.println("getFriendsData onCompleted : jsonArray " + jsonArray);
//                                System.out.println("getFriendsData onCompleted : response " + response);
//                                try {
//                                    JSONObject jsonObject = response.getJSONObject();
//                                    System.out.println("getFriendsData onCompleted : jsonObject " + jsonObject);
//                                    JSONObject summary = jsonObject.getJSONObject("summary");
//                                    System.out.println("getFriendsData onCompleted : summary total_count - " + summary.getString("total_count"));
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        })
//
//        );
//        batch.addCallback(new GraphRequestBatch.Callback() {
//            @Override
//            public void onBatchCompleted(GraphRequestBatch graphRequests) {
//                // Application code for when the batch finishes
//            }
//        });
//        batch.executeAsync();
//
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "id,name,link,picture");
//    }



}