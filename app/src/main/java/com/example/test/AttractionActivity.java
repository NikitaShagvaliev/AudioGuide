package com.example.test;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class AttractionActivity extends AppCompatActivity {

    ImageView mainImageView, galleryImage1View, galleryImage2View, galleryImage3View;
    TextView title, address, description;

    String data1, data2, data3;
    int myImage, gallery_image1, gallery_image2, gallery_image3;
    int audioguide;

    private Button buttonPlayStop;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;

    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mainImageView = findViewById(R.id.mainImageView);
        title = findViewById(R.id.title);
        address = findViewById(R.id.address);
        description = findViewById(R.id.attraction_description);
        galleryImage1View = findViewById(R.id.image_gallery1);
        galleryImage2View = findViewById(R.id.image_gallery2);
        galleryImage3View = findViewById(R.id.image_gallery3);

        getData();
        setData();
        initViews(audioguide);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        else return super.onOptionsItemSelected(item);
    }

    private void getData() {
        if (getIntent().hasExtra("myImage") && getIntent().hasExtra("data1")
                && getIntent().hasExtra("data2"))
        {
            data1 = getIntent().getStringExtra("data1");
            data2 = getIntent().getStringExtra("data2");
            data3 = getIntent().getStringExtra("data3");
            myImage = getIntent().getIntExtra("myImage", 1);
            gallery_image1 = getIntent().getIntExtra("gallery_image1", 1);
            gallery_image2 = getIntent().getIntExtra("gallery_image2", 1);
            gallery_image3 = getIntent().getIntExtra("gallery_image3", 1);
            audioguide = getIntent().getIntExtra("audioguide", 1);

        }
        else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData() {
        title.setText(data1);
        address.setText(data2);
        mainImageView.setImageResource(myImage);
        galleryImage1View.setImageResource(gallery_image1);
        galleryImage2View.setImageResource(gallery_image2);
        galleryImage3View.setImageResource(gallery_image3);
        description.setText(data3);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initViews(int audio_id) {
        buttonPlayStop = (Button) findViewById(R.id.ButtonPlayStop);
        mediaPlayer = MediaPlayer.create(this, audio_id);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                seekChange(v);
                return false;
            }
        });
    }

    private void seekChange(View v){
        if(mediaPlayer.isPlaying()){
            SeekBar sb = (SeekBar)v;
            mediaPlayer.seekTo(sb.getProgress());
        }
    }

    public void playAndStop(View v){
        if (buttonPlayStop.getText() == getString(R.string.play_str)) {
            buttonPlayStop.setText(getString(R.string.pause_str));
            try{
                mediaPlayer.start();
                startPlayProgressUpdater();
            }catch (IllegalStateException e) {
                mediaPlayer.pause();
            }
        }else {
            buttonPlayStop.setText(getString(R.string.play_str));
            mediaPlayer.pause();
        }
    }

    public void startPlayProgressUpdater() {
        seekBar.setProgress(mediaPlayer.getCurrentPosition());

        if (mediaPlayer.isPlaying()) {
            Runnable notification = new Runnable() {
                public void run() {
                    startPlayProgressUpdater();
                }
            };
            handler.postDelayed(notification, 1000);
        } else {
            mediaPlayer.pause();
            buttonPlayStop.setText(getString(R.string.play_str));
            seekBar.setProgress(0);
        }
    }
}
