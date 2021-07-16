package com.example.instaapp;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.pdf417.PDF417Reader;
import com.squareup.picasso.Picasso;

import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    View v;
    Button logout;
    Button drawerButton;
    DrawerLayout drawerLayout;
    FirebaseAuth mAuth;
    FirebaseUser user =null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_profile, container, false);

        drawerLayout=v.findViewById(R.id.drawerLayout);
        drawerButton=v.findViewById(R.id.drawButton);

        mAuth=FirebaseAuth.getInstance();


        NavigationView navigationView = v.findViewById(R.id.navprofileView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id == R.id.nav_logout) {

                    FacebookSdk.sdkInitialize(getActivity());
                    FirebaseAuth.getInstance().signOut();
                    LoginManager.getInstance().logOut();

                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
//                } else if(id == R.id.nav_share) {
//                    loadFragment(new Fragment());
//                } else if(id == R.id.nav_send) {
//                    loadFragment(new Fragment());
                }
               // drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


        drawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
        });

        TextView userText=v.findViewById(R.id.userName);
        CircleImageView userImage=v.findViewById(R.id.userImge);
        user = mAuth.getCurrentUser();
        if(user!=null){
            userText.setText(user.getDisplayName());
            if(user.getPhotoUrl()  !=null){

           Uri photoUrl = Profile.getCurrentProfile().getProfilePictureUri(200, 200);
                Picasso.get().load(photoUrl).into(userImage);
            }
        }

        return v;
    }
}


