package com.example.facerecognition;/*package whatever do not write package name here*/

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionPoint;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button cameraButton;
    InputImage img;
    int valid=0;

    // whenever we request for our customized permission, we
    // need to declare an integer and initialize it to some
    // value .
    private final static int REQUEST_IMAGE_CAPTURE = 124;
    FirebaseVisionImage image;
    FaceDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializing our firebase in main activity
        FirebaseApp.initializeApp(this);

        // finding the elements by their id's alloted.
        cameraButton = findViewById(R.id.camera);

        // setting an onclick listener to the button so as
        // to request image capture using camera
        cameraButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {

                        // makin a new intent for opening camera
                        Intent intent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        if (intent.resolveActivity(
                                getPackageManager())
                                != null) {
                            startActivityForResult(
                                    intent, REQUEST_IMAGE_CAPTURE);
                        }
                        else {
                            // if the image is not captured, set
                            // a toast to display an error image.
                            Toast
                                    .makeText(
                                            MainActivity.this,
                                            "Something went wrong",
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    @Nullable Intent data)
    {
        // after the image is captured, ML Kit provides an
        // easy way to detect faces from variety of image
        // types like Bitmap

        super.onActivityResult(requestCode, resultCode,
                data);
        if (requestCode == REQUEST_IMAGE_CAPTURE
                && resultCode == RESULT_OK) {
            Bundle extra = data.getExtras();
            Bitmap bitmap = (Bitmap)extra.get("data");
            img = InputImage.fromBitmap(bitmap, 0);
            detectFace(bitmap);
        }
    }

    // If you want to configure your face detection model
    // according to your needs, you can do that with a
    // FirebaseVisionFaceDetectorOptions object.
    private void detectFace(Bitmap bitmap)
    {
        FaceDetectorOptions options =
                new FaceDetectorOptions.Builder()
                        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
                        .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                        .build();

        try {
            image = FirebaseVisionImage.fromBitmap(bitmap);
            detector = FaceDetection.getClient(options);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        Task<List<Face>> result =
                detector.process(img)
                        .addOnSuccessListener(
                                new OnSuccessListener<List<Face>>() {
                                    @Override
                                    public void onSuccess(List<Face> faces) {
                                        // Task completed successfully
                                        // ...
                                        String resultText = "";
                                        int i = 1;
                                        for ( Face face : faces) {
                                            valid=1;
                                            resultText
                                                    = resultText
                                                    .concat("\n\nFACE NUMBER. "
                                                            + i + ": ")
                                                    .concat(
                                                            "\nSmile : "
                                                                    + String.format("%.3f",face.getSmilingProbability()* 100)
                                                                    + "%")
                                                    .concat(
                                                            "\nLeft eye open : "
                                                                    + String.format("%.3f",face.getLeftEyeOpenProbability()
                                                                    * 100)
                                                                    + "%")
                                                    .concat(
                                                            "\nRight eye open "
                                                                    + String.format("%.3f",face.getRightEyeOpenProbability()
                                                                    * 100)
                                                                    + "%");
                                            i++;
                                            Log.e("INFO","smile="+face.getSmilingProbability().toString());
                                        }
                                        if(valid==0)
                                        {
                                            Toast
                                                    .makeText(MainActivity.this,
                                                            "NO FACE DETECT",
                                                            Toast.LENGTH_SHORT)
                                                    .show();
                                        }
                                        else
                                        {
                                            Bundle bundle = new Bundle();
                                            Log.e("INFO","in else");
                                            bundle.putString(
                                                    LCOFaceDetection.RESULT_TEXT,
                                                    resultText);
                                            DialogFragment resultDialog
                                                    = new ResultDialog();
                                            resultDialog.setArguments(bundle);
                                            resultDialog.setCancelable(true);
                                            resultDialog.show(
                                                    getSupportFragmentManager(),
                                                    LCOFaceDetection.RESULT_DIALOG);
                                        }
                                        }
                                })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast
                                                .makeText(
                                                        MainActivity.this,
                                                        "Oops, Something went wrong",
                                                        Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                });

    }
}
