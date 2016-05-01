package miltoncasanova.proyectomovil2;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private AudioRecording audioRecording = new AudioRecording();
    private TakePicture takePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        takePicture = new TakePicture(this);

        final MyMapFragment mapFragment = (MyMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        final FloatingActionButton buttonRecord = (FloatingActionButton) findViewById(R.id.action_record);
        assert buttonRecord != null;
        buttonRecord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    // startRecording();
                    Vibrator v = (Vibrator) MainActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(100);
                    Handler h = new Handler();
                    h.postDelayed(new Runnable(){@Override public void run(){}}, 100);
                    buttonRecord.setTitle("Grabando...");
                    buttonRecord.setIcon(R.drawable.ic_my_mic_rec);
                    audioRecording.starRecording();
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP
                        || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                    Toast.makeText(MainActivity.this, "Grabado finalizado", Toast.LENGTH_SHORT).show();
                    buttonRecord.setTitle("Manten presionado para grabar");
                    buttonRecord.setIcon(R.drawable.ic_my_mic);
                    //stop
                    audioRecording.stopRecording();
                    mapFragment.setRecorderaMarket();
                }
                return true;
            }
        });
        final FloatingActionButton buttonCamera = (FloatingActionButton) findViewById(R.id.action_open_camera);
        assert buttonCamera != null;
        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture.take();
            }
        });



    }

}
