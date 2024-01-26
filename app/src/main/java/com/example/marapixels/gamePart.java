package com.example.marapixels;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class gamePart extends AppCompatActivity {

    private static final String OPENAI_API_KEY = "YOUR_OPENAI_API_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_part);

        ImageButton learningButton = findViewById(R.id.learningButton);
        ImageButton storiesButton = findViewById(R.id.storiesButton);
        Button nextButton = findViewById(R.id.nextButton);

        learningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click for the Learning Button
                startLearningActivity();
            }
        });

        storiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click for the Stories Button
                startStoriesActivity();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Placeholder: Add logic to determine the selected activity
                String selectedActivity = "game"; // Example: Replace with actual logic

                // Start a new activity based on the selected activity
                if ("learning".equals(selectedActivity)) {
                    startLearningActivity();
                } else if ("stories".equals(selectedActivity)) {
                    startStoriesActivity();
                } else {
                    // Handle other activities if needed
                    Toast.makeText(gamePart.this, "Selected activity not implemented", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Start the Learning Activity
    private void startLearningActivity() {
        Intent intent = new Intent(this, LearningActivity.class); // Replace LearningActivity with the actual class name
        startActivity(intent);
    }

    // Start the Stories Activity
    private void startStoriesActivity() {
        Intent intent = new Intent(this, StoriesActivity.class); // Replace StoriesActivity with the actual class name
        startActivity(intent);
    }
}
