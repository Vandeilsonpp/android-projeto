package com.example.ultimavez.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ultimavez.R;
import com.example.ultimavez.listener.CupomClickListener;
import com.example.ultimavez.model.domain.Cupom;

import java.util.List;

public class CupomAdapter extends RecyclerView.Adapter<CupomAdapter.CupomViewHolder>{

    private Context context;
    private List<Cupom> cupomList;
    private CupomClickListener cupomClickListener;

    public CupomAdapter(List<Cupom> cupomList, Context context, CupomClickListener listener) {
        this.context = context;
        this.cupomList = cupomList;
        this.cupomClickListener = listener;
    }

    @NonNull
    @Override
    public CupomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cupom, parent, false);
        return new CupomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CupomViewHolder holder, int position) {
        Cupom cupom = cupomList.get(position);
        holder.cupomCodigo.setText("Código: " + cupom.getCodigo());
        holder.cupomValor.setText("Valor do desconto: " + String.valueOf(cupom.getValorDoDesconto()));
        String estaAtivado = cupom.eValido() ? "ATIVADO" : "DESATIVADO";
        holder.cupomAtivo.setText("Status: " + estaAtivado);

        holder.itemView.setOnClickListener(v -> cupomClickListener.onCupomClick(cupom));
    }

    @Override
    public int getItemCount() {
        return cupomList.size();
    }

    public static class CupomViewHolder extends RecyclerView.ViewHolder {
        TextView cupomCodigo, cupomValor, cupomAtivo;

        public CupomViewHolder(View itemView) {
            super(itemView);
            cupomCodigo = itemView.findViewById(R.id.codigoDoCupom);
            cupomValor = itemView.findViewById(R.id.valorDoCupom);
            cupomAtivo = itemView.findViewById(R.id.cupomAtivado);
        }
    }
}
