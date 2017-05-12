package com.letsallchef.letsallchef;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class IPInputActivity extends AppCompatActivity {
    Button goButton;
    EditText inputIP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipinput);

        goButton = (Button)findViewById(R.id.goButton);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                inputIP  = (EditText)findViewById(R.id.inputIP);
                String inIP = inputIP.getText().toString();
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                i.putExtra("inIP", inIP);
                startActivity(i);
            }
        });
    }
}
