package com.example.fxcalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    Button digitButton, operatorButton;
    TextView primaryDisplay, secondaryDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        primaryDisplay = findViewById(R.id.primaryTextView);
        secondaryDisplay = findViewById(R.id.secondaryTextView);
    }

    public void digitFunction(View view) {
        digitButton = (Button) view;
        String digit = digitButton.getText().toString();
        if (primaryDisplay.getText().toString().equals("0")) {
            primaryDisplay.setText(digit);
        } else {
            primaryDisplay.append(digit);
        }
    }

    public void clearFunction(View view) {
        primaryDisplay.setText("0");
        secondaryDisplay.setText("");
    }

    public void deleteFunction(View view) {
        String currentText = primaryDisplay.getText().toString();
        if (currentText.length() > 1) {
            String newText = currentText.substring(0, currentText.length() - 1);
            primaryDisplay.setText(newText);
        } else {
            primaryDisplay.setText("0");
        }
    }

    public void operationFunction(View view) {
        operatorButton = (Button) view;
        String operator = operatorButton.getText().toString();
        String currentText = primaryDisplay.getText().toString();
        secondaryDisplay.setText(currentText + operator);
        primaryDisplay.setText("0");
    }

    public void calculateFunction(View view) {
        String expression = secondaryDisplay.getText().toString() + primaryDisplay.getText().toString();
        try {
            Expression exp = new ExpressionBuilder(expression).build();
            double result = exp.evaluate();
            DecimalFormat format = new DecimalFormat("0.##");
            primaryDisplay.setText(format.format(result));
            secondaryDisplay.setText(expression);
        } catch (Exception e) {
            primaryDisplay.setText("Error");
        }
    }
}