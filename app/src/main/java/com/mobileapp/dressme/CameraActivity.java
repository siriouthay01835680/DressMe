package com.mobileapp.dressme;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Layout;
import android.util.Size;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import android.view.LayoutInflater;


import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class CameraActivity extends AppCompatActivity {
    PreviewView previewView;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ImageCapture imageCapture;
    Button captureButton;
    Button springBtn;
    Button summerBtn;
    Button fallBtn;
    Button winterBtn;
    Button topBtn;
    Button bottomBtn;
    View popUpView;
    String resultFile = "";
    Boolean isItemChecked = false;
    Boolean isSeasonChecked = false;
    View view1;

//    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        view1 = inflater.inflate(R.layout.activity_camera,
//                container, false);
//        popUpView = inflater.inflate(R.layout.camerapopup,null);

        setContentView(R.layout.activity_camera);
        LayoutInflater layoutInflater = getLayoutInflater();
        popUpView = layoutInflater.inflate(R.layout.camerapopup, null);
//        setContentView(R.layout.camerapopup);
        captureButton = findViewById(R.id.button_capture);
        previewView = findViewById(R.id.previewView);

        cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                    startCameraX(cameraProvider);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, ContextCompat.getMainExecutor(this));//getExecutor());
    }


    private void startCameraX(ProcessCameraProvider cameraProvider) {
        //cameraProvider.unbindAll();
        ImageAnalysis imageAnalysis =
                new ImageAnalysis.Builder().setTargetResolution(new Size(1280, 720))
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).build();
        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), new ImageAnalysis.Analyzer() {
            @Override
            public void analyze(@NonNull ImageProxy image) {
                image.close();
            }
        });

        Preview preview = new Preview.Builder().build();


        CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build();


        //preview.setSurfaceProvider(previewView.getSurfaceProvider());
        //Preview preview = new Preview.Builder().build();

       /* Camera camera = cameraProvider.bindToLifecycle(
                ((LifecycleOwner) this),
                cameraSelector,
                preview,
                imageCapture);*/

        preview.setSurfaceProvider(previewView.createSurfaceProvider());

        imageCapture = new ImageCapture.Builder().setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY).build();

        cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector,imageCapture,
                imageAnalysis, preview);

        captureButton.setOnClickListener(new View.OnClickListener() {
            //set on click listener
            public void onClick(View view) {
                if (view.getId() == R.id.button_capture) {
                    int width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    int height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    boolean focusable = true;

                    PopupWindow popupWindow = new PopupWindow(popUpView, width, height, focusable);
                    previewView.post(new Runnable() {
                        @Override
                        public void run() {
                            popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
                        }
                    });
//                    topBtn = popUpView.findViewById(R.id.cameraTop);
//                    bottomBtn = popUpView.findViewById(R.id.cameraBottom);
//                    springBtn = popUpView.findViewById(R.id.cameraSpring);
//                    summerBtn = popUpView.findViewById(R.id.cameraSummer);
//                    fallBtn = popUpView.findViewById(R.id.cameraFall);
//                    winterBtn = popUpView.findViewById(R.id.cameraWinter);
                    RadioGroup radioItems = popUpView.findViewById(R.id.radioItem);
                    RadioGroup radioSeason = popUpView.findViewById(R.id.radioSeason);
                    Button doneBtn = popUpView.findViewById(R.id.cameraDone);
                    doneBtn.setEnabled(false);

                    radioItems.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
//                            System.out.println("items");
                            switch(checkedId) {
                                case R.id.cameraTop:
                                    resultFile = "top";
                                    isItemChecked = true;
                                    break;
                                case R.id.cameraBottom:
                                    resultFile = "bottom";
                                    isItemChecked = true;
                                    break;
                                default:
                                    resultFile = "";
                                    isItemChecked = false;
                                    break;
                            }
//                            System.out.println(resultFile);
                            if(isItemChecked && isSeasonChecked){
                                doneBtn.setEnabled(true);
                            }

                        }
                    });
                    radioSeason.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
//                            System.out.println("season");
                            switch(checkedId) {
                                case R.id.cameraSpring:
                                    resultFile += "Spring";
                                    isSeasonChecked = true;
                                    break;
                                case R.id.cameraSummer:
                                    resultFile += "Summer";
                                    isSeasonChecked = true;
                                    break;
                                case R.id.cameraFall:
                                    resultFile += "Fall";
                                    isSeasonChecked = true;
                                    break;
                                case R.id.cameraWinter:
                                    resultFile += "Winter";
                                    isSeasonChecked = true;
                                    break;
                                default:
                                    resultFile = "";
                                    isSeasonChecked = false;
                                    break;
                            }
                            if(isItemChecked && isSeasonChecked){
                                doneBtn.setEnabled(true);
                            }
                        }
                    });
                    doneBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            capturePhoto();
                        }
                    });

                }
            }

        });

    }

    private void capturePhoto() {

        long timestamp = System.currentTimeMillis();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, timestamp);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
        if (Build.VERSION.SDK_INT >= 29) {
            System.out.println("In sdk int 29");

//            /storage/emulated/0/Pictures/imageSaver
            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/" + resultFile);
        }

        else {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + resultFile);
            if(!file.exists()){
                System.out.println("File doesn't exist");
            }
//            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + "imageSaver";
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + resultFile;


            contentValues.put(MediaStore.Images.Media.DATA, path);

        }
//        String folder_main = "/storage/emulated/0/tempHolder/";
//        File f = new File(folder_main);
//        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + "imageSaver" + "img";
//        contentValues.put(MediaStore.Images.Media.DATA,)
//        if (f.exists()) {
//                System.out.println("Successful");
//        }
//        else{
//            System.out.println("not successful");
//        }

        ContentResolver resolver = getContentResolver();
        imageCapture.takePicture(
                new ImageCapture.OutputFileOptions.Builder(
                        getContentResolver(),
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        contentValues
                ).build(),
                ContextCompat.getMainExecutor(this),
                //outputFileOptions,ContextCompat.getMainExecutor(this),
                //new ImageCapture.OutputFileOptions.Builder(photoFile).build(),
                //ContextCompat.getMainExecutor(this),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        Toast.makeText(CameraActivity.this, "Photo saved successfully", Toast.LENGTH_SHORT).show();

                    }
                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Toast.makeText(CameraActivity.this, "Error saving photo: " + exception.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }

        );


    }
}


