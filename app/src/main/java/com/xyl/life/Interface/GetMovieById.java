package com.xyl.life.Interface;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetMovieById {
    @GET("movie/subject/{id}")
    Observable<ResponseBody> response(@Path("id") String id);
}
