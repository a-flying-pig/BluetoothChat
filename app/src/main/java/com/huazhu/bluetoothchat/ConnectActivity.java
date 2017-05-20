package com.huazhu.bluetoothchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ConnectActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView pairedListView;
    private ListView findListView;
    private Button scanButton;
    private Adapter pairedAdapter;
    private Adapter findAdapter;
    private String[] paridItems;
    private String[] finddItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        pairedListView = (ListView) findViewById(R.id.paired_list_view);
        findListView = (ListView) findViewById(R.id.find_list_view);
        scanButton = (Button) findViewById(R.id.scan_button);
        pairedAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                paridItems);
        findAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                finddItems);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scan_button:

                break;
            default:
                break;
        }
    }
}
