package com.example.appbanhang.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.appbanhang.Model.MauSanPham;
import com.example.appbanhang.R;

public class ChiTietActivity extends AppCompatActivity {
    TextView tensp, giasp, mota;
    Button btnthem;
    ImageView imghinhanh;
    Spinner spinner;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);
        initView();
        ActionToolBar();
        initData();
    }

    private void initData() {
        MauSanPham mauSanPham = (MauSanPham) getIntent().getSerializableExtra("chitiet");
        tensp.setText(mauSanPham.getTensp());
        mota.setText(mauSanPham.getMota());
        Glide.with(getApplicationContext()).load(mauSanPham.getHinhanh()).into(imghinhanh);
        giasp.setText(mauSanPham.getGiasp());
        Integer[] so = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapterspin = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, so);
        spinner.setAdapter(adapterspin);

    }

    private void initView(){
        tensp = findViewById(R.id.txttensp);
        giasp = findViewById(R.id.txtgiasp);
        mota = findViewById(R.id.txtmotachitiet);
        btnthem = findViewById(R.id.btnthemvaogiohang);
        spinner = findViewById(R.id.spinner);
        imghinhanh = findViewById(R.id.imgchitiet);
        toolbar = findViewById(R.id.toobar);
    }
    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
    }
}