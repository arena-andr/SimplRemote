package com.aarena.simplremote;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {

    private EditText host, port, secret_pass;

    private Button volume_up;
    private Button volume_down;
    private Button mute;

    private Button previous;
    private Button play_pause;
    private Button next;

    private Button bose;
    private Button gaming_speaker;

    private Toast toast_message;


    private void show_message(String message_string) {
        if (toast_message!= null) {
            toast_message.cancel();
        }
        toast_message = Toast.makeText(getApplicationContext(), message_string, Toast.LENGTH_SHORT);
        toast_message.show();
    }


    private boolean isWifiConnected(){
        try {
            ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWifi = connManager.getActiveNetworkInfo();

            if (mWifi!=null && mWifi.getType()== ConnectivityManager.TYPE_WIFI && mWifi.isConnected()) {
                return true;
            }
        }catch (Exception ignored){}
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addListenerOnButton();
    }



public void addListenerOnButton() {
    host = (EditText) findViewById(R.id.host_input);
    port = (EditText) findViewById(R.id.port_input);
    secret_pass = (EditText) findViewById(R.id.secret_pass_input);

    String host_value=host.getText().toString();
    String port_value=port.getText().toString();
    String secret_pass_value=secret_pass.getText().toString();

    final CharSequence address_base = String.format("http://%s:%s/trigger_named/?trigger_name=", host_value, port_value);

    volume_down = (Button) findViewById(R.id.volume_down_butt);

    volume_down.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String full_address = address_base+"volume_down";
            String action = "Volume Down";
            head_request(full_address, action);
        }
    });

    volume_up = (Button) findViewById(R.id.volume_up_butt);

    volume_up.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String full_address = address_base+"volume_up";
            String action = "Volume Up";
            head_request(full_address, action);
        }
    });


    previous = (Button) findViewById(R.id.previous_butt);

    previous.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String full_address = address_base+"previous_mediakey";
            String action = "Previous";
            head_request(full_address, action);
        }
    });

    play_pause = (Button) findViewById(R.id.play_pause_butt);

    play_pause.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String full_address = address_base+"play_pause_mediakey";
            String action = "Play/Pause";
            head_request(full_address, action);
        }
    });

    next = (Button) findViewById(R.id.next_butt);

    next.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String full_address = address_base+"next_mediakey";
            String action = "Next";
            head_request(full_address, action);
        }
    });

    bose = (Button) findViewById(R.id.bose_butt);

    bose.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String full_address = address_base+"bose_soundlink";
            String action = "Bose Soundlink";
            head_request(full_address, action);
        }
    });

    gaming_speaker = (Button) findViewById(R.id.gaming_speaker_butt);

    gaming_speaker.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String full_address = address_base+"gaming_speaker";
            String action = "Gaming Speakers";
            head_request(full_address, action);

        }
    });

    mute = (Button) findViewById(R.id.mute_butt);

    mute.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String full_address = address_base+"mute";
            String action = "Mute";
            head_request(full_address, action);
        }
    });
}

    private void head_request(String endpoint, String action) {

        if (isWifiConnected()) {
            HeadRequest helper = new HeadRequest();
            try {
                show_message(action);
                helper.execute(endpoint).get();
            } catch (InterruptedException | ExecutionException e) {
                show_message("Error");
            }
        } else {
//            Must be on Wi-Fi to send request
            show_message("No WiFi");
        }

    }
}

//      Additional param - prefix to trigger name
//      This does not properly work - but I am personally not using a secret pass so did not fix
//        if (!secret_pass_value.isEmpty()) {
//            secret_pass_value = secret_pass_value.concat("&");
//        }
//        final CharSequence full_address = String.format("http://%s:%s/trigger_named/?%s", host_value, port_value, secret_pass_value);