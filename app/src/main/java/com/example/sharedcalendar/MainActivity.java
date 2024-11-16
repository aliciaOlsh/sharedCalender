package com.example.sharedcalendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private EditText username, password;
    private Button loginButton;
    private TextView errorMessage;
    private JSONArray usersArray;

    private Button registerButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        errorMessage = findViewById(R.id.errorMessage);
        registerButton = findViewById(R.id.bt_registered);

        loadJSONFromAsset();

        // Inside your MainActivity.java
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorMessage.setVisibility(View.GONE); // Hide error message
                String user = username.getText().toString();
                String pass = password.getText().toString();

                try {
                    if (validateCredentials(user, pass)) {
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish(); // Optionally finish MainActivity so the user can't go back to the login screen
                    } else {
                        showErrorMessage("Invalid username or password. Try again.");
                    }
                } catch (Exception e) {
                    showErrorMessage("An error occurred. Please try again.");
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //need to continue !!!!!!!!!!!!!
            }
        });

    }

    private void loadJSONFromAsset() {
        try {
            InputStream is = getAssets().open("users.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String json = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(json);
            usersArray = jsonObject.getJSONArray("users");

        } catch (Exception e) {
            showErrorMessage("Failed to load user data. Please try again.");
        }
    }

    private boolean validateCredentials(String user, String pass) {
        try {
            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject userObject = usersArray.getJSONObject(i);
                if (userObject.getString("username").equals(user) && userObject.getString("password").equals(pass)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void showErrorMessage(String message) {
        errorMessage.setText(message);
        errorMessage.setVisibility(View.VISIBLE);
    }
}


