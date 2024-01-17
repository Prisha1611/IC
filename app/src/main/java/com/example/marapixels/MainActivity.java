package com.example.marapixels;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final int VIDEO_CAPTURE = 101;
    Button Resultsbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Resultsbutton = findViewById(R.id.Resultsbutton);

        Resultsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ResultOfDiagnosis.class);
                startActivity(intent);
            }
        });
    }
    public void launchVideoRecorder(View view) {

        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, VIDEO_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        // Handle video capture intent result
        super.onActivityResult(requestCode, resultCode, data);
    }
}