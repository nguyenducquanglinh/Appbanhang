package com.example.appbanhang.retrofit;

import com.example.appbanhang.Model.LoaiSpModel;
import com.example.appbanhang.Model.MauSanPhamModel;
import com.example.appbanhang.Model.UserModel;

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
    @POST("dangky.php")
    @FormUrlEncoded
    Observable<UserModel> dangky(
            @Field("email") String email,
            @Field("pass") String pass,
            @Field("username") String username,
            @Field("sodienthoai") String sodienthoai
    );

    @POST("dangnhap.php")
    @FormUrlEncoded
    Observable<UserModel> dangNhap(
            @Field("email") String email,
            @Field("pass") String pass
    );

    @POST("reset.php")
    @FormUrlEncoded
    Observable<UserModel> resetPass(
            @Field("email") String email
    );

}
