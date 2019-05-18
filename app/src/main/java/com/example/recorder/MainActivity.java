package com.example.recorder;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaMuxer;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int RECORD_AUDIO = 0;

    private MediaRecorder myAudioRecorder;

    private String output = null;

    private Button start, stop;

    private boolean permissionToRecordAccepted = false;

    private boolean permissionToWriteAccepted = false;

    private String [] permissions = {"android.permission.RECORD_AUDIO", "android.permission.WRITE_EXTERNAL_STORAGE"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int requestCode = 200;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            requestPermissions(permissions, requestCode);

        }

        start = (Button)findViewById(R.id.button1);

        stop = (Button) findViewById(R.id.button2);

        //play = (Button)findViewById(R.id.button3);

        start.setOnClickListener(this);

        stop.setOnClickListener(this);

        stop.setEnabled(false);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.button1:

                start(v);

                break;

            case R.id.button2:

                stop(v);

                break;

            default:

                break;

        }

    }

    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,

                                                     @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){

            case 200:

                permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                permissionToWriteAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                break;

        }

        if (!permissionToRecordAccepted ) MainActivity.super.finish();

        if (!permissionToWriteAccepted ) MainActivity.super.finish();

    }

    public void start(View view){

        startRecording();

    }

    public void stop(View view){

        myAudioRecorder.stop();

        myAudioRecorder.release();

        myAudioRecorder = null;

        stop.setEnabled(false);

        start.setEnabled(true);

        Toast.makeText(getApplicationContext(), "Audio recorded successfully", Toast.LENGTH_SHORT).show();

    }

    private void startRecording(){

        myAudioRecorder = new MediaRecorder();

        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);

        File root = android.os.Environment.getExternalStorageDirectory();
        File file = new File(root.getAbsolutePath() + "/myrecordings/Audios");
        if (!file.exists()) {
            file.mkdirs();
        }

        output =  root.getAbsolutePath() + "/myrecordings/Audios/" +
                String.valueOf(System.currentTimeMillis() + ".3gp");
        Log.d("filename",output);


        myAudioRecorder.setOutputFile(output);

        try{

            myAudioRecorder.prepare();

            myAudioRecorder.start();

        }

        catch (IllegalStateException e){

            e.printStackTrace();

        }

        catch (IOException e){

            e.printStackTrace();

        }

        start.setEnabled(false);

        stop.setEnabled(true);

        Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_SHORT).show();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.item_list:
                Intent intent = new Intent(this, RecordingFiles.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }
}
