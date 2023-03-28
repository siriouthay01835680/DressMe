package com.mobileapp.dressme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
//import androidx.camera.core.CameraSelector;
//import androidx.camera.core.ImageCapture;
//import androidx.camera.core.Preview;
//import androidx.camera.lifecycle.ProcessCameraProvider;
//import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

//import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

public class Upload extends Fragment {

    private UploadViewModel mViewModel;

    public static Upload newInstance() {
        return new Upload();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_upload, container, false);
//        PreviewView viewFinder = view.findViewById(R.id.viewFinder);
//        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext());
//
//        cameraProviderFuture.addListener(() -> {
//            // Camera provider is now ready to be used
//            try {
//                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
//
//                // Bind the Preview to the lifecycle
//                Preview preview = new Preview.Builder().build();
//                preview.setSurfaceProvider(viewFinder.getSurfaceProvider());
//
//                // Select back camera as a default
//                CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;
//
//                ImageCapture imageCapture =
//                        new ImageCapture.Builder()
//                                .setTargetRotation(view.getDisplay().getRotation())
//                                .build();
//
//                cameraProvider.bindToLifecycle(getViewLifecycleOwner(), cameraSelector, imageCapture, preview);
//

                //  androidx.camera.core.ImageCapture imageCapture = new androidx.camera.core.ImageCapture.Builder().build();
                // Create an image capture use case and set defaults
                //ImageCapture imageCapture = new ImageCapture.Builder().build();
                //ImageCaptureConfig.Builder builder = new ImageCaptureConfig.Builder();
                //builder.setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY);

                // Build the image capture use case and set the default values
                //ImageCaptureConfig imageCaptureConfig = builder.build();
                //imageCapture.setConfig(imageCaptureConfig);

                // Attach the use cases to the camera with the same lifecycle owner
                // Camera camera = cameraProvider.bindToLifecycle(getViewLifecycleOwner(), cameraSelector, preview, imageCapture);
//            } catch (ExecutionException | InterruptedException e) {
//                e.printStackTrace();
//            }
//        }, ContextCompat.getMainExecutor(requireContext()));


       // return inflater.inflate(R.layout.fragment_upload, container, false);

/*
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(UploadViewModel.class);
        // TODO: Use the ViewModel
    } */
// return view;
        return inflater.inflate(R.layout.fragment_upload, container, false);
}}