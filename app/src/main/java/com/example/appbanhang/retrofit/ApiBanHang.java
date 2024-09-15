package com.example.appbanhang.retrofit;

import com.example.appbanhang.Model.LoaiSpModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface ApiBanHang {
    @GET("loaisanpham")
    Observable<LoaiSpModel> getLoaiSp();
}
