package com.djowda.djowdaTransport;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.splashscreen.SplashScreen;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            LoadMainFragment();
        }

    }

    private void LoadMainFragment() {
        //begin transaction
        getSupportFragmentManager().beginTransaction()

                .replace(R.id.main_host_fragment, new MainFragment()).commit();

    }
}