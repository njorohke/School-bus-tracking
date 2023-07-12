package com.example.sbt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Newuser extends AppCompatActivity {
    private EditText parentNameEditText, studentNameEditText, emailEditText, phoneNumberEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newuser);

        parentNameEditText = findViewById(R.id.parent);
        studentNameEditText = findViewById(R.id.sname);
        emailEditText = findViewById(R.id.email);
        phoneNumberEditText = findViewById(R.id.phone);
        passwordEditText = findViewById(R.id.newpass);


        Button create = findViewById(R.id.btnCreateUser);

        // Set click listener for create user button
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String parentName = parentNameEditText.getText().toString().trim();
                String studentName = studentNameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String phoneNumber = phoneNumberEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Validate user input
                if (parentName.isEmpty() || studentName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Newuser.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Call the AsyncTask to send data to the database
                    new RegisterUserTask().execute(parentName, studentName, email, phoneNumber, password);
                }
            }

        });

    }

    private class RegisterUserTask extends AsyncTask<String, Void, String> {

        private static final String PHP_SCRIPT_URL = "http://localhost/school/DB.php";

        @Override
        protected String doInBackground(String... params) {
            String parentName = params[0];
            String studentName = params[1];
            String email = params[2];
            String phoneNumber = params[3];
            String password = params[4];
            Connection connection = database.getConnection();


            try {
                URL url = new URL(PHP_SCRIPT_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("parentName", parentName)
                        .appendQueryParameter("studentName", studentName)
                        .appendQueryParameter("email", email)
                        .appendQueryParameter("phoneNumber", phoneNumber)
                        .appendQueryParameter("password", password);
                String query = builder.build().getEncodedQuery();
                OutputStream os = conn.getOutputStream();
                os.write(query.getBytes(StandardCharsets.UTF_8));
                os.flush();
                os.close();

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String response = in.readLine();
                    in.close();

                    return response;
                } else {
                    return "Error: " + responseCode;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "Error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(Newuser.this, result, Toast.LENGTH_SHORT).show();
        }
    }


}


