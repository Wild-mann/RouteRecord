package cz.wild.routerecord;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Skryje toolbar ,protože chceme animaci přes celou obrazovku
        //getSupportActionBar().hide();

        // Inicializace imageView s naším obrázkem
        ImageView imgvwSpsoa = findViewById(R.id.imageViewWildLogoText);
        // Inicializace animace
        Animation animSpsoaLogo = AnimationUtils.loadAnimation(this, R.anim.welcome_sc);
        // Spuštění animace
        imgvwSpsoa.startAnimation(animSpsoaLogo); // spuštění samotné animace

        // Vlákno, které na 3,5 sekundy uspíme a pak zavoláme hlavní aktivitu
        Thread thrdWlcmscrnDelay = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }
        };

        // Spuštění vlákna
        thrdWlcmscrnDelay.start();
    }
}