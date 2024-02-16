/*
Mobile App Development I -- COMP.4631 Honor Statement
The practice of good ethical behavior is essential for maintaining good order in the classroom, providing an enriching learning experience for students, and training as a practicing computing professional upon graduation. This practice is manifested in the University's Academic Integrity policy. Students are expected to strictly avoid academic dishonesty and adhere to the Academic Integrity policy as outlined in the course catalog. Violations will be dealt with as outlined therein. All programming assignments in this class are to be done by the student alone unless otherwise specified. No outside help is permitted except the instructor and approved tutors.
I certify that the work submitted with this assignment is mine and was generated in a manner consistent with this document, the course academic policy on the course website on Blackboard, and the UMass Lowell academic code.
Date: 4/29/23
Names: Krestina Beshara and Aria Siriouthay
*/

package com.mobileapp.dressme;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    //camera permissions
    private static final String[] CAMERA_PERMISSION = new String[]{android.Manifest.permission.CAMERA};
    private static final int CAMERA_REQUEST_CODE = 10;

    //boolean for music
    private boolean isPlaying = false;

    MaterialToolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //adding music
        ImageButton playPauseButton = findViewById(R.id.soundOn);
        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    // Start playing the audio file
                    stopService(new Intent(MainActivity.this, BackgroundMusicService.class));
                    isPlaying = false;
                    playPauseButton.setImageResource(android.R.drawable.ic_lock_silent_mode);
                } else {
                   startService(new Intent(MainActivity.this, BackgroundMusicService.class));
                   isPlaying = true;
                   playPauseButton.setImageResource(android.R.drawable.ic_lock_silent_mode_off);
                }
            }
        });


        //setting up navigation toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();


        //up button
        AppBarConfiguration.Builder builder = new AppBarConfiguration.Builder(navController.getGraph());
        AppBarConfiguration appBarConfiguration = builder.build();
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

        //bottom nav
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.homeScreen:
                        navController.navigate(R.id.homeScreen);
                        return true;

                    case R.id.closet:
                        navController.navigate(R.id.closet);
                        return true;

                    case R.id.scrapbook:
                        navController.navigate(R.id.scrapbook);
                        return true;

                    case R.id.donate:
                        navController.navigate(R.id.donate);
                        return true;

                    default:
                        return false;
                }
            }
           });

        //checking if camera has permission first before opening
        Button enableCamera = findViewById(R.id.button2);
        enableCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasCameraPermission()) {
                    enableCamera();
                } else {
                    requestPermission();
                }
            }
        });
    }

    //function for toolbar options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //function to get toolbar permissions
    private boolean hasCameraPermission() {
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    //function to request permissions
    private void requestPermission() {
        ActivityCompat.requestPermissions(
                this,
                CAMERA_PERMISSION,
                CAMERA_REQUEST_CODE
        );
    }

    //function to enable camera
    private void enableCamera() {
        Intent intent = new Intent(this, CameraActivity.class);
        System.out.println("about to start camera activity");
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);

    }

}