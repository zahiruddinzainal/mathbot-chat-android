package zvhir.dev.mathbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button xSend;
    EditText xInput;
    TextView xText;
    MediaPlayer player;
    String tempText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xInput = findViewById(R.id.textInput);
        xSend = findViewById(R.id.send);
        xText = findViewById(R.id.userChat1);

        // Retrieve data from cache
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        tempText = prefs.getString("Input", tempText);
        xText.setText(tempText);

        xSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempText = xInput.getText().toString();

                if (TextUtils.isEmpty(tempText)){
                    xText.setText("Nothing to answer");
                    xInput.getText().clear();
                    closeKeyboard();
                }
                else {
                    xText.setText(tempText);
                    play();
                    xInput.getText().clear();
                    closeKeyboard();

                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    SharedPreferences.Editor editor = prefs.edit();

                    editor.putString("Input", tempText);
                    editor.apply();
                }
            }
        });
    }


    private void play() {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.send);
        }
        player.start();
    }

    private void stopPlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPlayer();
    }

    private void closeKeyboard() {
        View view = getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}