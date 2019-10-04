package com.btechviral.quickdrawapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nex3z.fingerpaintview.FingerPaintView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.fpv_paint) FingerPaintView mFpvPaint;
    @BindView(R.id.tv_prediction) TextView mTvPrediction;

    private Classifier mClassifier;
    private String[] drawing = {"drums", "flower", "laptop", "anvil", "baseball_bat",
            "ladder", "eyeglasses", "grapes", "book", "dumbbell", "traffic_light", "wristwatch",
            "wheel", "shovel", "bread", "table", "tennis_racquet", "cloud", "chair", "candle", "face", "eye", "airplane", "donut", "lollipop", "power_outlet", "pants", "mushroom", "star", "sword", "clock", "hot_dog", "syringe", "stop_sign", "mountain", "smiley_face", "apple", "bed", "shorts", "broom", "diving_board", "umbrella", "spider", "cell_phone", "car", "camera", "tree", "square", "moon", "radio", "hat", "pizza", "axe", "door", "tent", "envelope", "line", "cup", "fan", "triangle", "basketball", "butterfly", "scissors", "t-shirt", "tooth", "sun", "paper_clip", "spoon", "microphone", "headphones", "pencil", "alarm_clock", "saw", "frying_pan", "screwdriver", "helmet", "bridge", "light_bulb", "ceiling_fan", "key", "snake", "bird", "circle", "beard", "coffee_cup", "pillow", "bench", "rifle", "cat", "sock", "ice_cream", "moustache", "suitcase", "hammer", "rainbow", "knife", "cookie", "baseball", "lightning", "bicycle"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    public void onDetectClick(View view) {
        if (mClassifier == null) {
            Log.e(LOG_TAG, "onDetectClick(): Classifier is not initialized");
            return;
        } else if (mFpvPaint.isEmpty()) {
            Toast.makeText(this, "Draw something", Toast.LENGTH_SHORT).show();
            return;
        }

        Bitmap image = mFpvPaint.exportToBitmap(
                Classifier.IMG_WIDTH, Classifier.IMG_HEIGHT);
        Result result = mClassifier.classify(image);
        renderResult(result);
    }

    public void onClearClick(View view) {
        mFpvPaint.clear();
        mTvPrediction.setText("");
    }


    private void init() {
        try {
            mClassifier = new Classifier(this);
        } catch (IOException e) {
            Toast.makeText(this, "Failed to create classifier", Toast.LENGTH_LONG).show();
            Log.e(LOG_TAG, "init(): Failed to create Classifier", e);
        }
    }

    public void onClearAllClick(View view){
        mFpvPaint.clear();
        mTvPrediction.setText("");
    }


    private void renderResult(Result result) {
        mTvPrediction.setText(drawing[result.getPrediction()]);
    }

}