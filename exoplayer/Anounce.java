package com.example.exoplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Anounce extends AppCompatActivity {

    Button ok;
    EditText editText;
    TextView textView;
    String string="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anounce);
        textView=(TextView)findViewById(R.id.textView);
        editText=(EditText)findViewById(R.id.editText);
        ok=(Button)findViewById(R.id.button2);
       ok.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
            if(editText.getText().toString().equals("1")){
                Intent intent=new Intent(Anounce.this,Streaming1.class);
                startActivity(intent);

            }
             else  if(editText.getText().toString().equals("2")){
                   Intent intent=new Intent(Anounce.this,Streaming2.class);
                   startActivity(intent);
               }
              else if(editText.getText().toString().equals("3")){
                   Intent intent=new Intent(Anounce.this,Streaming3.class);
                   startActivity(intent);
               }
              else if(editText.getText().toString().equals("4")){
                   Intent intent=new Intent(Anounce.this,Streaming4.class);
                   startActivity(intent);
               }
              else if(editText.getText().toString().equals("5")){
                   Intent intent=new Intent(Anounce.this,Streaming1.class);
                   startActivity(intent);
               }
           }
       });



    }



}
