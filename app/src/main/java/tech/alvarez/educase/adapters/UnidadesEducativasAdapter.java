package tech.alvarez.educase.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tech.alvarez.educase.R;
import tech.alvarez.educase.model.UnidadEducativa;


public class UnidadesEducativasAdapter extends RecyclerView.Adapter<UnidadesEducativasAdapter.DeviceViewHolder> {

    private Context context;
    private List<UnidadEducativa> dataset;
    private OnUnidadEducativaSelectedListener onUnidadEducativaSelectedListener;

    public interface OnUnidadEducativaSelectedListener {
        void onUnidadEducativaSelected(UnidadEducativa departamento);
    }

    public UnidadesEducativasAdapter(Context context, OnUnidadEducativaSelectedListener listener) {
        this.dataset = new ArrayList<>();
        this.context = context;
        this.onUnidadEducativaSelectedListener = listener;
    }

    @Override
    public DeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_unidad_educativa, parent, false);
        return new DeviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DeviceViewHolder holder, int position) {
        UnidadEducativa u = dataset.get(position);
        holder.nombreTextView.setText(u.getNombre());
        holder.direccionTextView.setText(u.getDireccion());
        holder.distritoTextView.setText(u.getDistrito());

        holder.setDeviceSelectedListener(u, onUnidadEducativaSelectedListener);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public static class DeviceViewHolder extends RecyclerView.ViewHolder {

        View cardView;

        TextView nombreTextView;
        TextView direccionTextView;
        TextView distritoTextView;

        public DeviceViewHolder(View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);

            nombreTextView = (TextView) itemView.findViewById(R.id.nombreTextView);
            direccionTextView = (TextView) itemView.findViewById(R.id.direccionTextView);
            distritoTextView = (TextView) itemView.findViewById(R.id.distritoTextView);
        }

        public void setDeviceSelectedListener(final UnidadEducativa departamento, final OnUnidadEducativaSelectedListener onUnidadEducativaSelectedListener) {
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onUnidadEducativaSelectedListener.onUnidadEducativaSelected(departamento);
                }
            });
        }
    }

    public void add(UnidadEducativa unidadEducativa) {
        dataset.add(unidadEducativa);
        notifyDataSetChanged();
    }

    public void setDataset(List<UnidadEducativa> unidadesEducativas) {
        if (unidadesEducativas == null) {
            dataset = new ArrayList<>();
        } else {
            dataset = unidadesEducativas;
        }
        notifyDataSetChanged();
    }

    public void clear() {
        dataset.clear();
        notifyDataSetChanged();
    }

}
