package br.faj.lootmonth;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash);

        // Esconde a ActionBar
        getSupportActionBar().hide();
        // Exibe em FullScreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Depois de passa 2Segundos, ele irá abrir a MainActivity
                startActivity(new Intent(getBaseContext(), MainActivity.class));
                // Destroi a Activity atual
                finish();
            }
        }, 2000);
    }
}
