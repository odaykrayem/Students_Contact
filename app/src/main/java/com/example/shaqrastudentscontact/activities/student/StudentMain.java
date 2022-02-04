package com.example.shaqrastudentscontact.activities.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.shaqrastudentscontact.R;
import com.example.shaqrastudentscontact.utils.SharedPrefManager;
import com.google.android.material.navigation.NavigationView;

public class StudentMain extends AppCompatActivity implements DrawerLayout.DrawerListener, NavigationView.OnNavigationItemSelectedListener{

    SharedPrefManager prefManager;
    int deptId, studentId, studentType;

    public Toolbar toolbar;

    public DrawerLayout drawerLayout;

    public NavController navController;

    public NavigationView navigationView;


    int destination = R.id.answerQuestionsFragment;

    AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);

        prefManager = SharedPrefManager.getInstance(this);
        deptId = prefManager.getSelectedDept();
        studentId = prefManager.getUserId();
        studentType = prefManager.getStudentType();

        setupNavigation();


    }

    private void setupNavigation() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerLayout = findViewById(R.id.my_drawer_layout);

        navigationView = findViewById(R.id.nav_view);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);


        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.answerQuestionsFragment, R.id.scheduleFreeTime, R.id.profile).setOpenableLayout(drawerLayout).build();

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout.addDrawerListener(this);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        menuItem.setChecked(true);

        int id = menuItem.getItemId();

        switch (id) {

            case R.id.community:
                destination = R.id.communityFragment;
                break;

            case R.id.professors:
                destination = R.id.professors;
                break;

            case R.id.honor_students:
                destination = R.id.honorStudents;
                break;

            case R.id.books:
                destination = R.id.book_library;
                break;

            case R.id.student_profile:
                destination = R.id.profile;
                break;
        }

        drawerLayout.closeDrawers();

        return true;
    }


    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {
        navController.navigate(destination);
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }
}