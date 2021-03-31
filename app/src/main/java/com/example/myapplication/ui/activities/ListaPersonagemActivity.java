package com.example.myapplication.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.dao.PersonagemDAO;
import com.example.myapplication.model.Personagem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListaPersonagemActivity extends AppCompatActivity {

    private final PersonagemDAO dao = new PersonagemDAO();

    @Override /*começo da criação*/
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState); /*iniciar toda a parte do android*/
        setContentView(R.layout.activity_lista_personagem);
        setTitle("Lista de Personagem"); //setando o titulo
        dao.salva(new Personagem("Ken", "02041979", "1,80")); // inicia para testes
        dao.salva(new Personagem("Ryu", "02041979", "1,80")); // "


       // List<String> personagem = new ArrayList<>(Arrays.asList("Alex", "Ken", "Ryu", "Guile")); /*lista de personagens*/

        FloatingActionButton botaoNovoPersonagem = findViewById(R.id.fab_add); //pegando o floating action button
        botaoNovoPersonagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListaPersonagemActivity.this, FormularioPersonagemActivity.class));

            }
        });


        /*TextView primeiroPersonagem = findViewById(R.id.textView);
        TextView segundoPersonagem = findViewById(R.id.textView2);
        TextView terceiroPersonagem = findViewById(R.id.textView3);
        primeiroPersonagem.setText(personagem.get(0));
        segundoPersonagem.setText(personagem.get(1));
        terceiroPersonagem.setText(personagem.get(2));*/

    }

    @Override
    protected void onResume() {
        super.onResume(); //salvando para não dar back e apagar



        ListView listaDePersonagens = findViewById(R.id.activity_main_lista_personagem);


        List<Personagem> personagems = dao.todos(); //mudando o index para personagens
        listaDePersonagens.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, personagems)); /*criando um modelo de lista*/

        listaDePersonagens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //metodo de selecao personagem
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) { //salvando a posição do nome inserido na lista
                Personagem personagemEscolhido = personagems.get(posicao);
                //entrada com os dados
                Log.i("personagem", "" + personagemEscolhido);
                Intent vaiparaoformulario = new Intent(ListaPersonagemActivity.this, FormularioPersonagemActivity.class); // criando modo para ver o formulario do personagem
                vaiparaoformulario.putExtra("personagem", personagemEscolhido); // pegar e utilizar os gets
                startActivity(vaiparaoformulario); // chama o formulario


            }
        });

    }
}
