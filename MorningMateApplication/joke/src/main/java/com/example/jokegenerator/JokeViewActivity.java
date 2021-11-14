package com.example.jokegenerator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeViewActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joke_view);

        String text = getIntent().getStringExtra("joke");

        textView = (TextView)findViewById(R.id.jokeTextView);
        textView.setText(text);
    }
}