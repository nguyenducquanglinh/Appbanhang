package com.example.appbanhang.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhang.R;
import com.example.appbanhang.retrofit.ApiBanHang;
import com.example.appbanhang.retrofit.RetrofitClient;
import com.example.appbanhang.utils.Utils;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangNhapActivity extends AppCompatActivity {
    private TextView txtdangky, txtresetpass;
    private EditText email, pass;
    private AppCompatButton btndangnhap;
    private ApiBanHang apiBanHang;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        initView();
        initControl();
    }

    /**
     * Initialize the view components and read saved data if available.
     */
    private void initView() {
        // Initialize Paper for data persistence
        Paper.init(this);

        // Initialize Retrofit API client
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        // Bind UI components
        txtdangky = findViewById(R.id.txtdangky);
        txtresetpass = findViewById(R.id.txtresetpass);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        btndangnhap = findViewById(R.id.btndangnhap);

        // Read saved email and password
        String savedEmail = Paper.book().read("email");
        String savedPass = Paper.book().read("pass");

        if (savedEmail != null && savedPass != null) {
            email.setText(savedEmail);
            pass.setText(savedPass);
        }
    }

    /**
     * Set up control listeners for UI components.
     */
    private void initControl() {
        // Navigate to DangKyActivity (Register Activity)
        txtdangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DangNhapActivity.this, DangKyActivity.class);
                startActivity(intent);
            }
        });

        // Navigate to ResetPassActivity (Password Reset Activity)
        txtresetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DangNhapActivity.this, ResetPassActivity.class);
                startActivity(intent);
            }
        });

        // Handle Login Button Click
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleLogin();
            }
        });
    }

    /**
     * Handle the login process.
     */
    private void handleLogin() {
        String strEmail = email.getText().toString().trim();
        String strPass = pass.getText().toString().trim();

        // Input Validation
        if (TextUtils.isEmpty(strEmail)) {
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập Email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(strPass)) {
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập Password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save email and password using Paper
        Paper.book().write("email", strEmail);
        Paper.book().write("pass", strPass);

        // Make network request to login
        compositeDisposable.add(apiBanHang.dangNhap(strEmail, strPass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            if (userModel.isSuccess()) {
                                // Assign the current user
                                Utils.user_current = userModel.getResult().get(0);

                                // Navigate to MainActivity
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // Handle login failure
                                Toast.makeText(getApplicationContext(), userModel.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> {
                            // Handle network or other errors
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Update UI with current user information if available
        if (Utils.user_current.getEmail() != null && Utils.user_current.getPass() != null) {
            email.setText(Utils.user_current.getEmail());
            pass.setText(Utils.user_current.getPass());
        }
    }

    @Override
    protected void onDestroy() {
        // Clear all disposables to prevent memory leaks
        compositeDisposable.clear();
        super.onDestroy();
    }
}
