package com.example.appbanhang.retrofit;

import com.example.appbanhang.Model.LoaiSpModel;
import com.example.appbanhang.Model.MauSanPhamModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface ApiBanHang {
    @GET("getloaisp.php")
    Observable<LoaiSpModel> getLoaiSp();

    @GET("getmausp.php")
    Observable<MauSanPhamModel> getMauSp();
}
