package com.mobileapp.dressme;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Size;
import android.view.View;
import android.widget.Button;
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

    //@Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_camera);
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

                    capturePhoto();
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
            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/" + "imageSaver");
        } else {
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES) + "/" + "imageSaver");
            String path = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES) + "/" + "imageSaver" + "/" + "img.jpg";

            contentValues.put(MediaStore.Images.Media.DATA, path);

        }
        //define folders
        // String imageSaver = "/storage/emulated/0/Pictures/imageSaver";
        // String folder_main = "/storage/emulated/0/tempHolder";

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