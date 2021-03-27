package com.example.myapplication.ui.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListaPersonagemActivity extends AppCompatActivity {

    @Override /*começo da criação*/
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState); /*iniciar toda a parte do android*/
        setContentView(R.layout.activity_lista_personagem);
        List<String> personagem = new ArrayList<>(Arrays.asList("Alex", "Ken", "Ryu", "Guile")); /*lista de personagens*/

        ListView listaDePersonagens = findViewById(R.id.activity_main_lista_personagem);
        listaDePersonagens.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, personagem)); /*criando um modelo de lista*/

        /*TextView primeiroPersonagem = findViewById(R.id.textView);
        TextView segundoPersonagem = findViewById(R.id.textView2);
        TextView terceiroPersonagem = findViewById(R.id.textView3);
        primeiroPersonagem.setText(personagem.get(0));
        segundoPersonagem.setText(personagem.get(1));
        terceiroPersonagem.setText(personagem.get(2));*/

    };


}
