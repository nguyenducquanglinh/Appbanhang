package com.example.appbanhang.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.appbanhang.Model.GioHang;
import com.example.appbanhang.Model.MauSanPham;
import com.example.appbanhang.R;
import com.example.appbanhang.utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;

public class ChiTietActivity extends AppCompatActivity {
    TextView tensp, giasp, mota;
    Button btnthem;
    ImageView imghinhanh;
    Spinner spinner;
    Toolbar toolbar;
    MauSanPham mauSanPham;
    NotificationBadge badge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);
        initView();
        ActionToolBar();
        initData();
        initControl();
    }

    private void initControl() {
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themGioHang();
            }
        });
    }

    private void themGioHang() {
        if (mauSanPham == null) {
            Log.e("ChiTietActivity", "Không thể thêm vào giỏ hàng");
            return; // Dừng phương thức nếu mauSanPham là null
        }

        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());

        // Loại bỏ dấu phẩy và dấu chấm, sau đó chuyển đổi giá thành long
        String giaString = mauSanPham.getGiasp().replace(".", "").replace(",", "").replace("₫", "")
                .trim(); // Loại bỏ ký tự trắng;
        long gia = Long.parseLong(giaString) * soluong;

        boolean existsInCart = false;
        for (GioHang gioHang : Utils.manggiohang) {
            if (gioHang.getIdsp() == mauSanPham.getId()) {
                gioHang.setSoluong(gioHang.getSoluong() + soluong);
                gioHang.setGiasp(gia);
                existsInCart = true;
                break;
            }
        }

        if (!existsInCart) {
            GioHang gioHang = new GioHang();
            gioHang.setGiasp(gia);
            gioHang.setSoluong(soluong);
            gioHang.setIdsp(mauSanPham.getId());
            gioHang.setTensp(mauSanPham.getTensp());
            gioHang.setHinhsp(mauSanPham.getHinhanh());
            Utils.manggiohang.add(gioHang);
        }

        updateBadgeCount();
    }

    private void updateBadgeCount() {
        int totalItem = 0;
        for (GioHang item : Utils.manggiohang) {
            totalItem += item.getSoluong();
        }
        badge.setText(String.valueOf(totalItem));
    }

    private void initData() {
        mauSanPham = (MauSanPham) getIntent().getSerializableExtra("chitiet");
        if (mauSanPham != null) {
            tensp.setText(mauSanPham.getTensp());
            mota.setText(mauSanPham.getMota());
            Glide.with(getApplicationContext()).load(mauSanPham.getHinhanh()).into(imghinhanh);
            giasp.setText(mauSanPham.getGiasp());

            Integer[] so = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
            ArrayAdapter<Integer> adapterspin = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, so);
            spinner.setAdapter(adapterspin);
        } else {
            Log.e("ChiTietActivity", "MauSanPham is null");
            finish(); // Hoặc hiển thị thông báo
        }
    }

    private void initView() {
        tensp = findViewById(R.id.txttensp);
        giasp = findViewById(R.id.txtgiasp);
        mota = findViewById(R.id.txtmotachitiet);
        btnthem = findViewById(R.id.btnthemvaogiohang);
        spinner = findViewById(R.id.spinner);
        imghinhanh = findViewById(R.id.imgchitiet);
        toolbar = findViewById(R.id.toobar);
        badge = findViewById(R.id.menu_sl);
        FrameLayout frameLayoutgiohang = findViewById(R.id.framegiohang);
        frameLayoutgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giohang = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(giohang);
            }
        });
        updateBadgeCount();
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
