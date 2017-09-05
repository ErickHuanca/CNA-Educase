package tech.alvarez.educase;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//import okhttp3.logging.HttpLoggingInterceptor;

public class ServiceGenerator {

    private static final String BASE_URL = "https://datos.gob.bo/api/";

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

//    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
//            .setLevel(HttpLoggingInterceptor.Level.BODY);


    public static <S> S createService(Class<S> serviceClass) {
//        if (!httpClient.interceptors().contains(logging)) {
//            httpClient.addInterceptor(logging);
//            builder.client(httpClient.build());
//            retrofit = builder.build();
//        }
        return retrofit.create(serviceClass);
    }

}
