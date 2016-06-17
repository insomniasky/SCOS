package es.source.code.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.StrictMode;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import es.source.code.model.User;

public class LoginOrRegister extends Activity implements View.OnClickListener {
    private static EditText login_username;
    private static EditText login_password;
    private static Button user_login_button;
    private static Button user_return_button;
    private static Button user_register_button;
    private  static User loginUser = new User();
    private  static Intent intent = new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_login_or_register);
        initWidget();
    }

    private void initWidget() {
        login_username = (EditText) findViewById(R.id.username);
        login_password = (EditText) findViewById(R.id.login_password);
        user_login_button = (Button) findViewById(R.id.login_button);
        user_return_button = (Button) findViewById(R.id.return_button);
        user_register_button = (Button)findViewById(R.id.register_button);
        user_login_button.setOnClickListener(this);
        user_return_button.setOnClickListener(this);
        user_register_button.setOnClickListener(this);
        login_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String username = login_username.getText().toString().trim();
                    if (username.replaceAll("[a-z]*[A-Z]*\\d*", "").length() != 0) {
                        login_username.setError("输入内容不合规则！");
                    }
                }
            }
        });
        login_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String password = login_password.getText().toString().trim();
                    if (password.replaceAll("[a-z]*[A-Z]*\\d*", "").length() != 0)
                        login_password.setError("输入内容不合规则！");
                }
            }
        });
    }

    //确认登陆还是返回
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                if (checkEdit()&&IsLegal()) {
                    loginUser.setUserName(login_username.getText().toString().trim());
                    loginUser.setPassword(login_password.getText().toString().trim());
                    loginUser.setOldUser(true);
                    intent.putExtra("from", "LoginSuccess");
                    intent.setClass(LoginOrRegister.this, MainScreen.class);
                    //传递login对象
                    intent.putExtra("object",loginUser);
                    startActivity(intent);
                    LoginOrRegister.this.finish();
                    Toast.makeText(this, "系统主页面", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.return_button:

                intent.putExtra("from", "Return");
                intent.setClass(LoginOrRegister.this, MainScreen.class);
                startActivity(intent);
                LoginOrRegister.this.finish();
                Toast.makeText(this, "系统主页面", Toast.LENGTH_SHORT).show();
                break;
            case R.id.register_button:
                if (checkEdit()&&IsLegal()) {
                    loginUser.setUserName(login_username.getText().toString().trim());
                    loginUser.setPassword(login_password.getText().toString().trim());
                    loginUser.setOldUser(false);
                    intent.putExtra("from", "RegisterSuccess");
                    intent.setClass(LoginOrRegister.this, MainScreen.class);
                    //传递login对象
                    intent.putExtra("object", loginUser);
                    startActivity(intent);
                    LoginOrRegister.this.finish();
                    Toast.makeText(this, "系统主页面", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                Toast.makeText(this, "登录失败！", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //检查登录名和密码是否为空
    private boolean checkEdit() {
        if (login_username.getText().toString().trim().equals("")) {
            Toast.makeText(LoginOrRegister.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
        } else if (login_password.getText().toString().trim().equals("")) {
            Toast.makeText(LoginOrRegister.this, "密码不能为空", Toast.LENGTH_SHORT).show();
        } else {
            return true;
        }
        return false;
    }

    private boolean IsLegal() {
        if (login_username.getText().toString().trim().replaceAll("[a-z]*[A-Z]*\\d*", "").length() != 0) {
            Toast.makeText(LoginOrRegister.this, "用户名不合法", Toast.LENGTH_SHORT).show();
        } else if (login_password.getText().toString().trim().replaceAll("[a-z]*[A-Z]*\\d*", "").length() != 0) {
            Toast.makeText(LoginOrRegister.this, "密码不合法", Toast.LENGTH_SHORT).show();
        } else {
            return true;
        }
        return false;
    }
}
