package com.example.exoplayer;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    EditText trainno;
    TextToSpeech textToSpeech;
    Button go;
    TextView textView;
    String string="";
    Button button;
    String[] traindetails={"1","TrainA","12","30","5"};
    String[] traindetails1={"2","TrainB","12","30","4"};
    String[] traindetails2={"3","TrainC","12","30","3"};
    String[] traindetails3={"4","TrainD","12","30","2"};
    String[] traindetails4={"5","TrainE","12","30","1"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        trainno=(EditText)findViewById(R.id.editText);
        textView=(TextView)findViewById(R.id.textView);
        button=(Button)findViewById(R.id.button);
        Button button1=(Button)findViewById(R.id.button2);
        textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status!=TextToSpeech.ERROR);{
                    textToSpeech.setLanguage(Locale.US);
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                string=trainno.getText().toString();

                if(string.equals("1")) {  //textView.setText(""+string);

                    textView.setText("Train no " + string + ", " + traindetails[1] + ", is departed at" + traindetails[2] + ":" + traindetails[3] + "from platform no" + traindetails[4]);
                    textToSpeech.speak(textView.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                }
                if(string.equals("2")) {    //textView.setText(""+string);
                    textView.setText("Train no" + string + ", " + traindetails1[1] + ", is departed at" + traindetails1[2] + ":" + traindetails1[3] + "from platform no" + traindetails1[4]);
                    textToSpeech.speak(textView.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                }
                if(string.equals("3")) {   //textView.setText(""+string);
                    textView.setText("Train no " + string + ", " + traindetails2[1] + ",is departed at" + traindetails2[2] + ":" + traindetails2[3] + "from platform no" + traindetails2[4]);
                    textToSpeech.speak(textView.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);
                }
                if(string.equals("4")) {
                    //textView.setText(""+string);
                    textView.setText("Train no" + string + ", " + traindetails3[1] + ", is departed at" + traindetails3[2] + ":" + traindetails3[3] + "from platform no" + traindetails3[4]);
                    textToSpeech.speak(textView.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);
                }
                if(string.equals("5")) {
                    //textView.setText(""+string);
                    textView.setText("Train no" + string + ", " + traindetails4[1] + ", is departed at" + traindetails4[2] + ":" + traindetails4[3] + "from platform no" + traindetails4[4]);
                    textToSpeech.speak(textView.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);
                }

            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                string=trainno.getText().toString();
                if(string.equals("1")){
                    Intent intent=new Intent(MainActivity.this,Streaming1.class);
                    startActivity(intent);

                }
                else  if(string.equals("2")){
                    // playaudio1(Anounce.this);
                    Intent intent=new Intent(MainActivity.this,Streaming2.class);
                    startActivity(intent);
                }
                else if(string.equals("3")){
                    //playaudio2(Anounce.this);
                    Intent intent=new Intent(MainActivity.this,Streaming3.class);
                    startActivity(intent);
                }
                else if(string.equals("4")){
                    // playaudio3(Anounce.this);
                    Intent intent=new Intent(MainActivity.this,Streaming4.class);
                    startActivity(intent);
                }
                else if(string.equals("5")){
                    //playaudio4(Anounce.this);
                    Intent intent=new Intent(MainActivity.this,Streaming1.class);
                    startActivity(intent);
                }
            }
        });

            }


    }




