package com.ouledahmed.simplynote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawer;
    private SharedNotesReadViewModel sharedNotesReadViewModel;
    private NavigationView navigationView;
    private Toolbar toolbar;
    SharedPref sharedpref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedpref = new SharedPref(this);
        if (sharedpref.loadNightModeState() == true){
            setTheme(R.style.darkTheme);

        } else {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedNotesReadViewModel = new ViewModelProvider(this).get(SharedNotesReadViewModel.class);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);


        navigationView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this,navController,drawer);
        NavigationUI.setupWithNavController(navigationView,navController);
        navigationView.setNavigationItemSelectedListener(this);
        toolbar.setTitle("all notes");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.read_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.searchItemMenu){
            Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.searchFragment2);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(Navigation.findNavController(this,R.id.nav_host_fragment),drawer);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public  void setToolbarTitle(String title){
        toolbar.setTitle(title);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_all:
                sharedNotesReadViewModel.setRequested(0);
                Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.notesFragment);
                toolbar.setTitle("all notes");
                break;
            case R.id.nav_important:
                sharedNotesReadViewModel.setRequested(1);
                Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.notesFragment);
                toolbar.setTitle("important");
                break;
            case R.id.nav_urgent:
                sharedNotesReadViewModel.setRequested(2);
                Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.notesFragment);
                toolbar.setTitle("urgent");
                break;
            case R.id.nav_mode:
                if (sharedpref.loadNightModeState() == true){
                    sharedpref.setNightModeState(false);
                } else {
                    sharedpref.setNightModeState(true);
                }
                restartApp();
                break;
            case R.id.nav_about:
                Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.aboutFragment);
                toolbar.setTitle("about us");
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void restartApp(){
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        finish();
    }

}