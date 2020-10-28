package com.aarena.simplremote;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import android.content.Context;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URISyntaxException;
import java.net.SocketTimeoutException;
import java.net.URL;

public class HeadRequest extends AsyncTask<String, String, String> {

    private HttpURLConnection urlConnection;

    @Override
    protected String doInBackground(String... strings) {
        String full_address = strings[0];

        try {
            URL url = new URL(strings[0]);
            Proxy proxy = ProxySelector.getDefault().select(url.toURI()).get(0);
            urlConnection = (HttpURLConnection) url.openConnection(proxy);
            urlConnection.setRequestMethod("HEAD");
            urlConnection.getInputStream().close();
            urlConnection.setConnectTimeout(1000);
            urlConnection.setReadTimeout(1000);
            throw new SocketTimeoutException();
        } catch (SocketTimeoutException e) {
//            ToastClass.MakeToast("Timeout Error");
            ;
        } catch (IOException | URISyntaxException  e) {
//            ToastClass.MakeToast("IO error");
            ;
        } finally {
            try {
                urlConnection.disconnect();
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        return null;
    }
}