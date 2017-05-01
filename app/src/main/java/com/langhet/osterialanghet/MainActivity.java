package com.langhet.osterialanghet;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        makeContentAppearBehindStatusBar();
        setUpToolbar();
    }

    private void makeContentAppearBehindStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    private void setUpToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Osteria Langhet");
    }

    public void onClickBook(View view) {
        Uri uri = Uri.parse("smsto:" + getResources().getString(R.string.booking_phone_number));
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", "Vorrei prenotare per il giorno");
        String previousPackage = intent.getPackage();
        intent.setPackage("com.whatsapp");
        if (intent.resolveActivity(getPackageManager()) == null) {
            // Can't start WhatsApp
            intent.setPackage(previousPackage);
        }
        startActivity(intent);
    }

    public void onClickMenu(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(getResources().getString(R.string.menu_site)));
        startActivity(browserIntent);
    }
}
