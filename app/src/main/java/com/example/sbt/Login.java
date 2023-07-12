package com.example.sbt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    private String selectedOption;
    private boolean isCredentialsValidated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText user = findViewById(R.id.username);
        EditText pass = findViewById(R.id.password);
        Button login = findViewById(R.id.button1);
        Spinner select = findViewById(R.id.spinner);
        String[] options = {"Select User","Driver", "Parent"};



        // Create an ArrayAdapter using a string array and a default spinner layout
       ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_options, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

       // Set the adapter for the Spinner
        select.setAdapter(adapter);
        select.getOnItemSelectedListener();
        select.setEnabled(false);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = user.getText().toString();
                String password = pass.getText().toString();

                select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selectedItem = parent.getItemAtPosition(position).toString();

                        // Handle different options using if statements
                        if(selectedItem.equals("Select User")){
                            Toast.makeText(Login.this, "Select User", Toast.LENGTH_SHORT).show();
                        }
                        if (selectedItem.equals("Driver")) {
                            // Action for Option
                            if (username.equals("admin") && password.equals("password")) {
                                select.setEnabled(true);
                                Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
                                // Redirect to main activity or perform other actions
                                // Create a new Intent to start a specific activity
                                Intent map = new Intent(Login.this, drivermap.class);
                                startActivity(map);
                                user.setText("");
                                pass.setText("");


                            } else {
                                Toast.makeText(Login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                            }

                            Intent dmap = new Intent(Login.this, drivermap.class);
                            startActivity(dmap);
                            // performActionForOption1();
                        } else if (selectedItem.equals("Parent")) {
                            // Action for Option 2
                            if (username.equals("admin") && password.equals("password")) {
                                select.setEnabled(true);
                                Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
                                // Redirect to main activity or perform other actions
                                // Create a new Intent to start a specific activity
                                Intent map = new Intent(Login.this, parentmap.class);
                                startActivity(map);
                                user.setText("");
                                pass.setText("");


                            } else {
                                Toast.makeText(Login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                            }
                            Intent pmap = new Intent(Login.this, parentmap.class);
                           startActivity(pmap);
                            //performActionForOption2();
                        }
                        //dialog box showing the selection
                        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                        builder.setTitle("Confirm Selection");
                        builder.setMessage("You have selected: " + selectedItem);
                        builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Perform navigation to the next activity
                                // navigateToNextActivity();
                            }
                        });

                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Handle the cancellation or stay on the current activity
                            }
                        });
                        builder.show();
                    }


                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // Handle the case where no item is selected (if needed)

                    }
                });

                // Check if username and password are correct
                if (username.equals("admin") && password.equals("password")) {
                    select.setEnabled(true);
                    Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
                    // Redirect to main activity or perform other actions
                    // Create a new Intent to start a specific activity
                    Intent map = new Intent(Login.this, parentmap.class);
                    startActivity(map);
                    user.setText("");
                    pass.setText("");


                } else {
                    Toast.makeText(Login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView textView = findViewById(R.id.textView7);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        String linkText = "New user?Click here.";
        SpannableString spannableString = new SpannableString(linkText);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(Login.this, Newuser.class);
                startActivity(intent);
            }
        };

        spannableString.setSpan(clickableSpan, 0, linkText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);

    }
    private void selecter() {
        // Check if the selected option is Driver or Parent
        if (selectedOption.equals("Driver")) {
            Intent intent1 = new Intent(Login.this, drivermap.class);
            startActivity(intent1);
        } else if (selectedOption.equals("Parent")) {
            Intent intent2 = new Intent(Login.this, parentmap.class);
            startActivity(intent2);
        } else {
            Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show();
        }
    }
}