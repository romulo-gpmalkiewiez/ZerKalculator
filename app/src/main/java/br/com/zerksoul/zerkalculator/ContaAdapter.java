package br.com.zerksoul.zerkalculator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class ContaAdapter extends RecyclerView.Adapter {

    List<Conta> contas;

    public ContaAdapter(List<Conta> contas) {
        this.contas = contas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_historico,parent,false);
        ViewHolderClass vhClass = new ViewHolderClass(view);
        return vhClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ViewHolderClass vhClass = (ViewHolderClass)  holder;
            Conta conta = contas.get(position);
            vhClass.text_valor1.setText(conta.getValor1());
            vhClass.text_valor2.setText(conta.getValor2());
            vhClass.text_operation.setText(conta.getOperation());
            vhClass.text_resultado.setText(conta.getResultado());
    }

    @Override
    public int getItemCount() {
        return contas.size();
    }
    public class ViewHolderClass extends RecyclerView.ViewHolder{
        TextView text_valor1;
        TextView text_valor2;
        TextView text_operation;
        TextView text_resultado;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            text_valor1 = itemView.findViewById(R.id.text_valor1);
            text_valor2 = itemView.findViewById(R.id.text_valor2);
            text_operation = itemView.findViewById(R.id.text_operation);
            text_resultado = itemView.findViewById(R.id.text_resultado);
        }
    }
}
