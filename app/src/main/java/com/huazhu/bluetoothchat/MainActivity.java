package com.huazhu.bluetoothchat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button sendButton;
    private Button connectButton;
    private TextView oppositeName;
    private TextView oppositeText;
    private TextView myText;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVariable();
    }

    private void initVariable() {
        sendButton = (Button) findViewById(R.id.send_button);
        connectButton = (Button) findViewById(R.id.connect_button);
        oppositeName = (TextView) findViewById(R.id.opposite_name);
        oppositeText = (TextView) findViewById(R.id.opposite_text);
        myText = (TextView) findViewById(R.id.my_text);
        editText = (EditText) findViewById(R.id.edit_text);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_button:

                break;
            case R.id.connect_button:

                break;
            default:
                break;
        }
    }

/*    private void setOnclickListener() {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.send_button:
                        break;
                    case R.id.connect_button:
                        break;
                    default:
                        break;
                }
            }
        };
    }*/
}
