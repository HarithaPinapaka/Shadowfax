package com.shadowfax.getlocationofdelivery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

    private Button mStartBtn;
    private EditText mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);
       /* ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
        getActionBar().setIcon(
                new ColorDrawable(getResources().getColor(
                        android.R.color.transparent)));
        actionBar.setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle(
                (Html.fromHtml("<font color=\"#FFFFFF\">"
                        + "Customer delivery" + "</font>")));*/
        mId = (EditText) findViewById(R.id.id);
        mStartBtn = (Button) findViewById(R.id.start_btn);
        mStartBtn.setOnClickListener(mStartListtener);

    }

    public View.OnClickListener mStartListtener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String id_str = mId.getText().toString();
            int id = Integer.valueOf(id_str);
            Intent intent = new Intent(MainActivity.this,MyLocation.class);
            intent.putExtra("id",id);
            startService(intent);
            //startActivity(intent);
        }
    };
}
