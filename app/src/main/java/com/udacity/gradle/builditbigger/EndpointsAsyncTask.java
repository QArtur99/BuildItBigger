package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ProgressBar;

import com.udacity.gradle.builditbigger.MainActivity;
import com.android.androidjokes.TextDisplay;
import com.android.googlecloudendpoints.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by ART_F on 2017-06-24.
 */

class EndpointsAsyncTask extends AsyncTask<String, String, String> {
    private static MyApi myApiService = null;
    com.udacity.gradle.builditbigger.MainActivity mainActivity;
    private Context context;
    private ProgressBar progressBar;
    private String nameString;

    EndpointsAsyncTask(Context context, String name) {
        this.context = context;
        this.nameString = name;
    }

    @SafeVarargs
    @Override
    protected final String doInBackground(String... params) {
        if (myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://192.168.1.100:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        String name = nameString;

        try {
            return myApiService.sayHi(name).execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPreExecute() {
        ((MainActivity)context).setProgressBarUp();
    }

    @Override
    protected void onPostExecute(String result) {
        ((MainActivity)context).setProgressBarDown();

        Intent myIntent = new Intent(context, TextDisplay.class);
        myIntent.putExtra("joke", result);
        context.startActivity(myIntent);
    }
}
