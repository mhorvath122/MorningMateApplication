package com.example.jokegenerator;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JokeMainActivity extends AppCompatActivity {
    private TextView jokeTextBox;
    private Button jokeButton;
    private String joke;
    private ArrayList<String> jokesList;

    private TextToSpeech mTTs;
    private SeekBar mSeekBarPitch;
    private SeekBar getmSeekBarSpeed;
    private Button mButtonSpeak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joke_activity_main);

        jokeButton = findViewById(R.id.getJokeButton);
        jokesList = new ArrayList<String>();

        mButtonSpeak = findViewById(R.id.getJokeButton2);

        mTTs = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTs.setLanguage(Locale.ENGLISH);

                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {
                        mButtonSpeak.setEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initialization failed!");
                }
            }
        });

            mSeekBarPitch = findViewById(R.id.SB1);
            getmSeekBarSpeed = findViewById(R.id.SB2);

            mButtonSpeak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    speak();
                }
            });
    }

    private void speak() {
        String text = jokeTextBox.getText().toString();
        float pitch = (float) mSeekBarPitch.getProgress() / 50;
        if (pitch < 0.1) pitch = 0.1f;
        float speed = (float) getmSeekBarSpeed.getProgress() / 50;
        if (speed < 0.1) speed = 0.1f;
        mTTs.setPitch(pitch);
        mTTs.setSpeechRate(speed);

        mTTs.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onDestroy() {
        if(mTTs != null) {
            mTTs.stop();
            mTTs.shutdown();
        }

        super.onDestroy();
    }

    public void retrieveJoke(View view) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Api api = retrofit.create(Api.class);

        Call<Joke> call = api.getJoke();

        call.enqueue(new Callback<Joke>() {
            @Override
            public void onResponse(Call<Joke> call, Response<Joke> response) {
                joke = response.body().getJoke();

                jokeTextBox = findViewById(R.id.jokeTextBox);
                jokeTextBox.setMovementMethod(new ScrollingMovementMethod());
                jokeTextBox.setText(joke);

                jokesList.add(joke);
            }

            @Override
            public void onFailure(Call<Joke> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Internet connection required to generate joke", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void goToJokeHistory(View view) {
        Intent intent = new Intent(JokeMainActivity.this, SearchHistoryActivity.class);
        intent.putStringArrayListExtra("jokesList", jokesList);

        startActivity(intent);
    }

    public List<String> getJokesList() {
        return jokesList;
    }
}
