package com.langhet.osterialanghet;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

public class MainActivity extends AppCompatActivity {
    private static int[] IMAGES_ID = { R.drawable.langhet, R.drawable.riso_osteria_langet, R.drawable.piscina_osteria_langhet, R.drawable.bergolo };
    private static final int IMAGES_DELAY = 4000;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        makeContentAppearBehindStatusBar();
        setUpToolbar();
        setUpImageSwitcher();
    }

    private void setUpImageSwitcher() {
        final ImageSwitcher imageSwitcher = (ImageSwitcher) findViewById(R.id.place_image);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setAdjustViewBounds(true);
                return imageView;
            }
        });
        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,android.R.anim.slide_in_left));
        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,android.R.anim.slide_out_right));
        imageSwitcher.setImageResource(IMAGES_ID[0]);
        imageSwitcher.postDelayed(new Runnable() {
            int index = 1;
            @Override
            public void run() {
                imageSwitcher.setImageResource(IMAGES_ID[index]);
                index++;
                if (index >= IMAGES_ID.length) {
                    index = 0;
                }
                imageSwitcher.postDelayed(this, IMAGES_DELAY);
            }
        }, IMAGES_DELAY);
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
        Uri uri = Uri.parse("sms:" + getResources().getString(R.string.booking_phone_number));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.putExtra("sms_body", "Gradirei prenotare per il giorno");
        startActivity(intent);
    }

    public void onClickSendEmail(View view) {
        String[] addresses = new String[] {getResources().getString(R.string.email)};

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Prenotazione");
        intent.putExtra(Intent.EXTRA_TEXT, "Buongiorno,\nVorrei prenotare un tavolo per il giorno");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    public void onClickMenu(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(getResources().getString(R.string.menu_site)));
        startActivity(browserIntent);
    }

    public void onClickGetDirections(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q=44.546449,8.1813415"));
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }
}
