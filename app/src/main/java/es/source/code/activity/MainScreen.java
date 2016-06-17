package es.source.code.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import es.source.code.model.User;

public class MainScreen extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Intent intent = getIntent();
        //从Intent当中根据key取得value
        RadioButton button_1 = (RadioButton) findViewById(R.id.radio_button1);//点菜
        RadioButton button_2 = (RadioButton) findViewById(R.id.radio_button2);//查看菜单
        RadioButton LoginButton = (RadioButton) findViewById(R.id.radio_button3);
        User user = new User();
        String fromKey = intent.getStringExtra("from");
        switch (fromKey) {
            case "FromEntry":
                if (button_1.getVisibility() != View.VISIBLE) {
                    button_1.setVisibility(View.VISIBLE);
                }
                if (button_2.getVisibility() != View.VISIBLE) {
                    button_2.setVisibility(View.VISIBLE);
                }
                //接收User对象，为传入的loginUser
                user = (User) intent.getSerializableExtra("object");
                break;
            case "LoginSuccess":
                if (button_1.getVisibility() != View.VISIBLE) {
                    button_1.setVisibility(View.VISIBLE);
                }
                if (button_2.getVisibility() != View.VISIBLE) {
                    button_2.setVisibility(View.VISIBLE);
                }

                user = null;
                break;
            case "RegisterSuccess":
                Toast.makeText(this, "欢迎您成为SCOS新用户", Toast.LENGTH_SHORT).show();
                if (button_1.getVisibility() != View.VISIBLE) {
                    button_1.setVisibility(View.VISIBLE);
                }
                if (button_2.getVisibility() != View.VISIBLE) {
                    button_2.setVisibility(View.VISIBLE);
                }

                user = (User) intent.getSerializableExtra("object");
                break;
            case "Return":
                if (button_1.getVisibility() != View.GONE) {
                    button_1.setVisibility(View.GONE);
                }
                if (button_2.getVisibility() != View.GONE) {
                    button_2.setVisibility(View.GONE);
                }

                user = null;
                break;
            default:
                user = null;
                break;
        }

        button_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Intent intent = new Intent();
                    intent.setClass(MainScreen.this, FoodView.class);
                    startActivity(intent);
                }
            }
        });
        LoginButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Intent intentlog = new Intent();
                    intentlog.setClass(MainScreen.this, LoginOrRegister.class);
                    startActivity(intentlog);
                }
            }
        });
    }

}
