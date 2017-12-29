package com.squid.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.squid.sdk.addgas.Squid;
import com.squid.sdk.addgas.api.CallBack;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private final String TAG="MainActivity";
    private Button button1,button2,button3,button4,button5,button6,button7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1=(Button)findViewById(R.id.button1);
        button2=(Button)findViewById(R.id.button1);
        button3=(Button)findViewById(R.id.button1);
        button4=(Button)findViewById(R.id.button1);
        button5=(Button)findViewById(R.id.button1);
        button6=(Button)findViewById(R.id.button1);
        button7=(Button)findViewById(R.id.button1);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        Squid.init(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1:
                Squid.getInstance().getOrderDetail(this, "12381233", new CallBack() {
                    @Override
                    public void Success(String result) {

                    }

                    @Override
                    public void Error(Throwable e) {
                        e.printStackTrace();
                    }
                });
                break;
            case R.id.button2:

                break;
            case R.id.button3:

                break;
            case R.id.button4:

                break;
            case R.id.button5:

                break;
            case R.id.button6:

                break;
            case R.id.button7:

                break;
        }
    }
}
