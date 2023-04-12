package com.mobileapp.dressme;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.os.Bundle;
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

import java.util.concurrent.ExecutionException;

public class CameraActivity extends AppCompatActivity {
    PreviewView previewView;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ImageCapture imageCapture;
    Button captureButton;

    //@Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //View view = inflater.inflate(R.layout.activity_camera,
        //      container, false);

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

       /* captureButton.setOnClickListener(new View.OnClickListener() {
            //set on click listener
            public void onClick(View view) {
                if (view.getId() == R.id.button_capture) {
                    capturePhoto();
                }
            }

        }); */
    //

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
                    capturePhoto();
                }
            }

        });

    }

    private void capturePhoto() {
        //ImageCapture.OutputFileOptions outputFileOptions =
          //      new ImageCapture.OutputFileOptions.Builder(new File("/Users/krestinabeshara/DressMe/app/src/main/assets/tempHolder")).build();
       // File photoDir = new File("/Users/krestinabeshara/DressMe/app/src/main/assets/tempHolder");
       // if(!photoDir.exists())
        //{
          //  photoDir.mkdir();
        //}

       /* Date date = new Date();
        String timestamp = String.valueOf(date.getTime());
        String photoFilePath = photoDir.getAbsolutePath() + "/" + timestamp + ".jpeg";

        File photoFile = new File(photoFilePath);
        */

        long timestamp = System.currentTimeMillis();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, timestamp);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
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
                        saveImage();
                        Toast.makeText(CameraActivity.this, "Photo saved successfully", Toast.LENGTH_SHORT).show();

                    }
                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Toast.makeText(CameraActivity.this, "Error saving photo: " + exception.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }
        );

    }
    private void saveImage() {
        
    }
}


