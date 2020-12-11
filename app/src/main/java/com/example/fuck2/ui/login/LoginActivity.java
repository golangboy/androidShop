package com.example.fuck2.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;
import com.example.fuck2.MainActivity;
import com.example.fuck2.R;
import com.example.fuck2.config.Config;
import com.example.fuck2.result.Result;
import com.example.fuck2.utils.ApiThread;
import com.example.fuck2.utils.HttpRequest;
import com.example.fuck2.utils.Utils;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {


    private MHandler mHandler = new MHandler();

    private class MHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            HttpRequest.Response response = (HttpRequest.Response) msg.obj;
            if (0 == msg.what) {
                String setCookie = Utils.EmptyString;
                if (response.getHeaders() != null && response.getHeaders().get("Set-Cookie") != null && response.getHeaders().get("Set-Cookie").size() > 0) {
                    setCookie = response.getHeaders().get("Set-Cookie").get(0);
                }
                Config.setCookie(setCookie);
                int code = JSONObject.parseObject(response.getBody()).getInteger("code");
                if (code == Result.ErrCode.Ok.ordinal()) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, Result.getMsg(code), Toast.LENGTH_SHORT).show();
                }

            }
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        final EditText phoneEditText = findViewById(R.id.phone);
        final EditText passWordEditText = findViewById(R.id.password);
        Button loginRegister = findViewById(R.id.login);
        loginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("phone", phoneEditText.getText().toString());
                hashMap.put("pass_word", passWordEditText.getText().toString());
                new ApiThread(0, mHandler, "post2", Config.getServerAddress() + "/v1/login", Utils.MapToHttpParam(hashMap)).start();
            }
        });

    }

}
