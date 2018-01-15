package com.xyl.life.Interface;

import com.xyl.life.entity.movie.Top250;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * -------------------------
 * Author：doraemon
 * Created by xyl on 2018/1/12.
 * ---------------------------
 * This class is used for:
 */

public interface Top250Movie {
    @GET("movie/top250")
    Observable<Top250> response(
            @Query("start") int start,
            @Query("count") int count
    );
}
