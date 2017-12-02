package com.example.gdunellari.newsaggregator;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by juneh on 12/1/2017.
 */

public class ApiConnectionTask extends AsyncTask<String, Float, JSONObject> {
    @Override
    protected JSONObject doInBackground(String... args) {
        String jsonString = "";
        JSONObject jsonDoc = null;
        HttpURLConnection connection = null;

        try {
            URL url = new URL(args[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.addRequestProperty("Accept","application/json");
            connection.addRequestProperty("Content-Type","application/json");

            InputStream in = connection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                jsonString = scanner.next();
            }
            jsonDoc = new JSONObject(jsonString);

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }

        return jsonDoc;
    }
}
