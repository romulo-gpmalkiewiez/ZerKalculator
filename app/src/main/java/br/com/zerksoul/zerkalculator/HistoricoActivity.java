package br.com.zerksoul.zerkalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HistoricoActivity extends AppCompatActivity {

    List<Conta> contas;
    RecyclerView recyclerView;
    ContaAdapter contaAdapter;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);
        getSupportActionBar().setTitle("Histórico");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF4E71AE));
        recyclerView = findViewById(R.id.recyclerView_contas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        contas = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("historico").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dn:snapshot.getChildren()){
                    Conta con = dn.getValue(Conta.class);
                    con.resultado = "=" + con.resultado;
                    contas.add(con);
                    contaAdapter = new ContaAdapter(contas);
                    recyclerView.setAdapter(contaAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}