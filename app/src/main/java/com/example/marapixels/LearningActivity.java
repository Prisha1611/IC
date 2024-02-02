package com.example.marapixels;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class LearningActivity extends AppCompatActivity {
    private ImageView Imageview;
    private EditText editText;
    private TextView textView;
    private String stringURLEndpoint = "";
    private String stringApiKey = "";
    private String stringOutput = "";
    private Bitmap bitmapOutputImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);

        Imageview = findViewById(R.id.imageview);
        editText = findViewById(R.id.editTextText);
        textView = findViewById(R.id.textView);
    }

    public void buttongenerate(View view) {
        String stringInputText = editText.getText().toString();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("prompt", stringInputText);
            jsonObject.put("n", 1);
            jsonObject.put("size", "1024x1024");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                stringURLEndpoint,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("DALL-E Response", response.toString());
                            stringOutput = response
                                    .getJSONArray("data")
                                    .getJSONObject(0)
                                    .getString("url");

                            textView.setText(stringOutput);

                            // Call the method to show the image
                            buttonShow(null);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.setText("Error");
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> mapHeader = new HashMap<>();
                mapHeader.put("Authorization", "Bearer " + stringApiKey);
                mapHeader.put("Content-Type", "application/json");

                return mapHeader;
            }
        };

        int intTimeOutPeriod = 6000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(intTimeOutPeriod,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(retryPolicy);

        Volley.newRequestQueue(getApplicationContext()).add(jsonObjectRequest);
    }

    public void buttonShow(View view) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(stringOutput);

                    try {
                        bitmapOutputImage = BitmapFactory.decodeStream(url.openStream());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        thread.start();

        try {
            // Wait for the thread to finish
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Bitmap bitmapFinalImage = Bitmap.createScaledBitmap(bitmapOutputImage,
                Imageview.getWidth(),
                Imageview.getHeight(),
                true);

        Imageview.setImageBitmap(bitmapFinalImage);
        textView.setText("Image generation SUCCESSFUL!!");
    }
}
