package com.example.lab5_m1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private Button stopButton;
    private TextView progress;
    private static final String TAG = "MainActivity";
    private volatile boolean stopThread = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button2);
        stopButton = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startDownload(v);
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                stopDownload(v);
            }
        });
    }

    public void mockFileDownloader(){
        progress = findViewById(R.id.textView);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                button.setText("Downloading...");

            }
        });

        for(int downloadProgress = 0; downloadProgress <= 100; downloadProgress=downloadProgress+10){
            int finalDownloadProgress = downloadProgress;
            if(stopThread){

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.setText("");
                        button.setText("Start");
                    }
                });
                return;
            }

            int finalDownloadProgress1 = downloadProgress;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progress.setText("Downloading Progress: " + finalDownloadProgress1 + "%");

                }
            });

            Log.d(TAG, "Download Progress: " + downloadProgress + "%");
            try{
                Thread.sleep(1000);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progress.setText("");
                button.setText("Start");
            }
        });
    }

    public void startDownload(View view){
        stopThread = false;
        ExampleRunnable runnable = new ExampleRunnable();
        new Thread(runnable).start();
    }

    public void stopDownload(View view){
        stopThread = true;
    }

    class ExampleRunnable implements Runnable{
        @Override
        public void run(){
            mockFileDownloader();
        }

    }



}
