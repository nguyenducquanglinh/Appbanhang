package com.example.appbanhang.retrofit;

import com.example.appbanhang.Model.LoaiSpModel;
import com.example.appbanhang.Model.MauSanPhamModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiBanHang {
    @GET("getloaisp.php")
    Observable<LoaiSpModel> getLoaiSp();

    @GET("getmausp.php")
    Observable<MauSanPhamModel> getMauSp();

    @POST("chitiet.php")
    @FormUrlEncoded
    Observable<MauSanPhamModel> getSanPham(
            @Field("page") int page,
            @Field("loai") int loai
    );
}
