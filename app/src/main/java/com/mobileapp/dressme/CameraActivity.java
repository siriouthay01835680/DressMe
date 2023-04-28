package com.mobileapp.dressme;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.ContentObserver;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Size;
import android.view.Gravity;
import android.view.LayoutInflater;
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

import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class CameraActivity extends AppCompatActivity {
    PreviewView previewView;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ImageCapture imageCapture;
    Button captureButton;
    View popUpView;
    String resultFile = "";
    View view1;
    int itemGroupID;
    int seasonGroupID;

    //    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_camera);
        LayoutInflater layoutInflater = getLayoutInflater();
        popUpView = layoutInflater.inflate(R.layout.camerapopup, null);
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

        preview.setSurfaceProvider(previewView.createSurfaceProvider());

        imageCapture = new ImageCapture.Builder().setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY).build();

        cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, imageCapture,
                imageAnalysis, preview);

        captureButton.setOnClickListener(new View.OnClickListener() {
            //set on click listener
            public void onClick(View view) {
                if (view.getId() == R.id.button_capture) {

                    //capturePhoto();
                    int width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    int height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    boolean focusable = true;
                    Button doneBtn = popUpView.findViewById(R.id.cameraDone);
                    doneBtn.setEnabled(true);

                    PopupWindow popupWindow = new PopupWindow(popUpView, width, height, focusable);
                    previewView.post(new Runnable() {
                        @Override
                        public void run() {
                            popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
                        }
                    });
                    RadioGroup radioItems = popUpView.findViewById(R.id.radioItem);
                    RadioGroup radioSeason = popUpView.findViewById(R.id.radioSeason);

                    doneBtn.setOnClickListener(new View.OnClickListener() {


                        public void onClick(View v) {
                            resultFile = "";
                            itemGroupID = radioItems.getCheckedRadioButtonId();
                            seasonGroupID = radioSeason.getCheckedRadioButtonId();
                            if((itemGroupID == -1) || (seasonGroupID == -1))
                            {
                                Toast.makeText(CameraActivity.this, "Please select a clothing item and season", Toast.LENGTH_SHORT).show();

                        }
//
//                    radioSeason.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(RadioGroup group, int checkedId) {
////                            System.out.println("season");//r.id.checkedid
//                            switch(checkedId) {
//                                case R.id.cameraSpring:
//                                    resultFile += "Spring";
//                                    isSeasonChecked = true;
//                                    break;
//                                case R.id.cameraSummer:
//                                    resultFile += "Summer";
//                                    isSeasonChecked = true;
//                                    break;
//                                case R.id.cameraFall:
//                                    resultFile += "Fall";
//                                    isSeasonChecked = true;
//                                    break;
//                                case R.id.cameraWinter:
//                                    resultFile += "Winter";
//                                    isSeasonChecked = true;
//                                    break;
//                                default:
//                                    resultFile = "";
//                                    isSeasonChecked = false;
//                                    break;
//                            }
                            else
                            {
                                RadioButton item = popUpView.findViewById(itemGroupID);
                                RadioButton season = popUpView.findViewById(seasonGroupID);
                                resultFile += item.getText().toString();
                                resultFile += season.getText().toString();
                                capturePhoto();
                                doneBtn.setEnabled(false);
                            }

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
            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/" + resultFile);
        }
        else {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + resultFile);
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + resultFile;

            contentValues.put(MediaStore.Images.Media.DATA, path);

    }


        ContentResolver resolver = getContentResolver();


        getContentResolver().registerContentObserver(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, true,
                new ContentObserver(new Handler()) {
                    @Override
                    public void onChange(boolean selfChange) {
//                        Log.d("ScratchService","External Media has been added");
//                        System.out.println("changes made to media");
                        super.onChange(selfChange);

                    }
                }
        );

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
                        //create pop ups to ask for season and item, based on answer...work w appropriate folder

                        //add the saved image to the folder using contentValues

                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Toast.makeText(CameraActivity.this, "Error saving photo: " + exception.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }

        );


    }


}


