package miltoncasanova.proyectomovil2;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


import com.firebase.client.Firebase;
import com.getbase.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private AudioRecording audioRecording = new AudioRecording();
    private TakePicture takePicture;
    private String firebaseUrl = "https://proyecto-movil.firebaseio.com/";
    private FireBaseMethods fireBaseMethods;
    MyMapFragment myMapFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        fireBaseMethods = new FireBaseMethods(firebaseUrl);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        takePicture = new TakePicture(this);

        myMapFragment = (MyMapFragment) getFragmentManager().findFragmentById(R.id.map);

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
        final FloatingActionButton buttonText = (FloatingActionButton) findViewById(R.id.action_text);
        assert buttonText != null;
        buttonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("asd",myMapFragment.toString());
                myMapFragment.setTextaMarket();
                DialogFragment dialog = new TextDialog();
                dialog.show(getSupportFragmentManager(), "dialog");
            }
        });
        final FloatingActionButton buttonUpload = (FloatingActionButton) findViewById(R.id.action_upload);
        assert buttonUpload != null;
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isConnectedViaWifi()) {
                    // Your code here
                    Toast.makeText(MainActivity.this, "Enviando...", Toast.LENGTH_SHORT).show();

                }else{
                    DialogFragment dialog = new MyAlertDialog();
                    dialog.show(getSupportFragmentManager(), "dialog");
                }
            }
        });





    }
    private boolean isConnectedViaWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return mWifi.isConnected();
    }

}
