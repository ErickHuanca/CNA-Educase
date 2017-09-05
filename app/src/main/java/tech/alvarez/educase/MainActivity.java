package tech.alvarez.educase;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.alvarez.educase.adapters.UnidadesEducativasAdapter;
import tech.alvarez.educase.model.DatosResponse;
import tech.alvarez.educase.model.UnidadEducativa;

public class MainActivity extends AppCompatActivity implements UnidadesEducativasAdapter.OnUnidadEducativaSelectedListener {

    private RecyclerView unidadesEducativasRecyclerView;
    private UnidadesEducativasAdapter unidadesEducativasAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unidadesEducativasRecyclerView = (RecyclerView) findViewById(R.id.unidadesEducativasRecyclerView);
        unidadesEducativasRecyclerView.setHasFixedSize(true);
        unidadesEducativasRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        unidadesEducativasAdapter = new UnidadesEducativasAdapter(this, this);

        unidadesEducativasRecyclerView.setAdapter(unidadesEducativasAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cargarDatos();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        swipeRefreshLayout.setRefreshing(true);
        cargarDatos();
    }

    private void cargarDatos() {
        DatosGobBoService service = ServiceGenerator.createService(DatosGobBoService.class);
        Call<DatosResponse> call = service.establecimientosEducativos("b5da4242-1c90-47d9-b98f-bde1f35a0764", "miraflores");

        call.enqueue(new Callback<DatosResponse>() {
            @Override
            public void onResponse(Call<DatosResponse> call, Response<DatosResponse> response) {
                swipeRefreshLayout.setRefreshing(false);

                if (response.isSuccessful()) {
                    unidadesEducativasAdapter.setDataset(response.body().getResult().getRecords());
                } else {
                    Log.e("MIAPP ", "No se puede obtener los establecimientos");
                }
            }

            @Override
            public void onFailure(Call<DatosResponse> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Log.e("MIAPP", "Error obteniendo establecimientos: " + t.getMessage());
            }
        });
    }


    /**
     * Este método se ejecuta cuando se presiona una Unidad Educativa
     */
    @Override
    public void onUnidadEducativaSelected(UnidadEducativa unidadEducativa) {
        String latitud = unidadEducativa.getLatitud().replace(",", ".");
        String longitud = unidadEducativa.getLongitud().replace(",", ".");

        String uriString = "geo:" + latitud + "," + longitud + "?z=16&q=" + latitud + "," + longitud + "(" + unidadEducativa.getNombre() + ")";

        Uri location = Uri.parse(uriString);
        Intent intent = new Intent(Intent.ACTION_VIEW, location);
        startActivity(intent);
    }
}
