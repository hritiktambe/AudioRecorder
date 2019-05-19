package com.example.recorder.fragments;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.recorder.MainActivity;
import com.example.recorder.R;

import java.io.File;
import java.io.IOException;

public class Tab1Fragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "Tab1Fragment";
    public static final int RECORD_AUDIO = 0;

    private MediaRecorder myAudioRecorder;

    private String output = null;

    private Button start, stop;

    private boolean permissionToRecordAccepted = false;

    private boolean permissionToWriteAccepted = false;

    private LiveData<Tab2Fragment> f;

    private String [] permissions = {"android.permission.RECORD_AUDIO", "android.permission.WRITE_EXTERNAL_STORAGE"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(TAG,"Fragment1 started");
        View view = inflater.inflate(R.layout.fragment1_layout,container,false);
       // btnTest = view.findViewById(R.id.btnTEST);
        int requestCode = 200;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            requestPermissions(permissions, requestCode);

        }

        start = (Button)view.findViewById(R.id.button1);

        stop = (Button) view.findViewById(R.id.button2);

        start.setOnClickListener(this);

        stop.setOnClickListener(this);

        stop.setEnabled(false);



        return view;
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

        if (!permissionToRecordAccepted ) getActivity().getParent().finish();

        if (!permissionToWriteAccepted ) getActivity().getParent().finish();

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

        Toast.makeText(getActivity(), "Audio recorded successfully", Toast.LENGTH_SHORT).show();

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

        Toast.makeText(getActivity(), "Recording started", Toast.LENGTH_SHORT).show();

    }


}
