package tech.alvarez.educase;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import tech.alvarez.educase.model.DatosResponse;

public interface DatosGobBoService {

    @GET("action/datastore_search")
    Call<DatosResponse> establecimientosEducativos(@Query("resource_id") String resourceId, @Query("limit") int limit);

    @GET("action/datastore_search")
    Call<DatosResponse> establecimientosEducativos(@Query("resource_id") String resourceId, @Query("q") String query);

}
