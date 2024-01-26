package com.example.marapixels;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StoriesActivity extends AppCompatActivity {

    private static final String OPENAI_API_KEY = "YOUR_OPENAI_API_KEY";
    private static final String OPENAI_URL = "https://api.openai.com/v1/engines/davinci/completions";

    private Button generateStoryButton;
    private TextView generatedStoryTextView;
    private ImageView generatedImageView;
    private RecyclerView storiesRecyclerView;
    private ProgressBar loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);

        // Initialize UI elements
        generateStoryButton = findViewById(R.id.generateStoryButton);
        generatedStoryTextView = findViewById(R.id.generatedStoryTextView);
        generatedImageView = findViewById(R.id.generatedImageView);
        storiesRecyclerView = findViewById(R.id.storiesRecyclerView);
        loadingIndicator = findViewById(R.id.loadingIndicator);

        // Set up RecyclerView
        List<StoryModel> initialStories = new ArrayList<>();
        StoriesAdapter storiesAdapter = new StoriesAdapter(initialStories);
        storiesRecyclerView.setAdapter(storiesAdapter);
        storiesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up click listener for the "Generate Story" button
        generateStoryButton.setOnClickListener(view -> fetchStoryFromOpenAI());
    }

    private void fetchStoryFromOpenAI() {
        // Show loading indicator
        loadingIndicator.setVisibility(View.VISIBLE);

        // Example prompt for story generation
        String prompt = "Generate a short story about";

        // Construct the OpenAI API request
        String requestBodyJson = "{\"prompt\":\"" + prompt + "\",\"max_tokens\":150}";
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        // Create an OkHttpClient instance
        OkHttpClient client = new OkHttpClient();

        // Create a Request object
        Request request = new Request.Builder()
                .url(OPENAI_URL)
                .post(RequestBody.create(requestBodyJson, JSON))
                .addHeader("Authorization", "Bearer " + OPENAI_API_KEY)
                .build();

        // Make an asynchronous call to OpenAI API
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Handle failure (e.g., network error)
                e.printStackTrace();
                // Hide loading indicator on failure
                loadingIndicator.setVisibility(View.GONE);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // Hide loading indicator on response
                loadingIndicator.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    // Parse the response and extract the generated story
                    String responseBody = response.body().string();
                    String generatedStory = parseOpenAiResponse(responseBody);

                    // Update the UI with the generated story
                    runOnUiThread(() -> generatedStoryTextView.setText(generatedStory));
                } else {
                    // Handle unsuccessful response (e.g., API error)
                    // Log the error or perform appropriate error handling
                }
            }
        });
    }

    private String parseOpenAiResponse(String responseBody) {
        String generatedStory;
        try {
            // Parse the JSON response
            JSONObject jsonResponse = new JSONObject(responseBody);

            // Extract the generated story from the JSON
            generatedStory = jsonResponse.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");

        } catch (JSONException e) {
            e.printStackTrace();
            return "Error parsing response";
        }
        return generatedStory;
    }

    // Optionally, implement a method to handle images associated with the generated story
    private void updateGeneratedImageView(String generatedStory) {
        List<String> imageUrls = extractImageUrls(generatedStory);
        ImageView generatedImage = findViewById(R.id.generatedImageView);

        // Load the first image using Picasso
        if (!imageUrls.isEmpty()) {
            String firstImageUrl = imageUrls.get(0);
            Picasso.get().load(firstImageUrl).into(generatedImage);
        }
    }

    private List<String> extractImageUrls(String generatedStory) {
        try {
            JSONObject storyJson = new JSONObject(generatedStory);

            // Example: Extract image URLs from a "images" array in the JSON
            JSONArray imagesArray = storyJson.getJSONArray("images");
            List<String> imageUrls = new ArrayList<>();

            for (int i = 0; i < imagesArray.length(); i++) {
                String imageUrl = imagesArray.getString(i);
                imageUrls.add(imageUrl);
            }
            return imageUrls;
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
