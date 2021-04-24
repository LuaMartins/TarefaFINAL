package com.example.myapplication.ui.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.dao.PersonagemDAO;
import com.example.myapplication.model.Personagem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.example.myapplication.ui.activities.ConstantesActivities.CHAVE_PERSONAGEM;


public class ListaPersonagemActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Lista de Personagem";
    private final PersonagemDAO dao = new PersonagemDAO();
    private ArrayAdapter<Personagem> adapter;

    @Override /*começo da criação*/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); /*iniciar toda a parte do android*/
        setContentView(R.layout.activity_lista_personagem);
        setTitle(TITULO_APPBAR); //setando o titulo
        configuraFabNovoPersonagem();
        configuraLista();
    }

    // config de botao de novo personagem vinculado com formulario
    private void configuraFabNovoPersonagem() {
        FloatingActionButton botaoNovoPersonagem = findViewById(R.id.fab_add); //pegando o floating action button
        botaoNovoPersonagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreFormulario(); // puxando o metodo

            }
        });
    }
    //metodo de abrir o formulario
    private void abreFormulario() {
        startActivity(new Intent(this, FormularioPersonagemActivity.class));
    }

    // dar o clear para limpar as infos
    @Override
    protected void onResume() {
        super.onResume(); //salvando para não dar back e apagar
        atualizaPersonagem();
    }

    //usar para limpar com o clear e atualizar com o adapter
    private void atualizaPersonagem() {
        adapter.clear(); // limpa tudo
        adapter.addAll(dao.todos()); // adiciona a lista pelo dao.todos
    }

    // chamando o remove para remover
    private void remove(Personagem personagem) {
        dao.remove(personagem);
        adapter.remove(personagem);
    }

    // começando a fazer o esquema de remoção
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.activity_lista_personagem_menu, menu);
    }

    // fazer o remover com o adapter
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return configuraMenu(item);
    }

    //config do menu pegando o Item
    private boolean configuraMenu(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.activity_lista_personagem_menu_remover) {

            new AlertDialog.Builder(this)
                    .setTitle("Removendo Personagem") // fazendo a mensagem de remover o personagem
                    .setMessage("Tem certeza que deseja remover?") // mensagem avisando se o usuario tem certeza da ação
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        // remoção no clique
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                            Personagem personagemEscolhido = adapter.getItem(menuInfo.position);
                            remove(personagemEscolhido);
                        }
                    })
                    .setNegativeButton("Não", null)
                    .show();
        }
        return super.onContextItemSelected(item);
    }

    // configurações da lista
    private void configuraLista() {
        ListView listaDePersonagens = findViewById(R.id.activity_main_lista_personagem);
        listaDePersonagens(listaDePersonagens);
        configuraItemPorClick(listaDePersonagens);
        registerForContextMenu(listaDePersonagens);
    }

    private void configuraItemPorClick(ListView listaDePersonagens) {
        listaDePersonagens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //metodo de selecao personagem
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) { //salvando a posição do nome inserido na lista
                Personagem personagemEscolhido = (Personagem) adapterView.getItemAtPosition(posicao);
                //entrada com os dados
                Log.i("personagem", "" + personagemEscolhido);
                abreFormularioEditar(personagemEscolhido);
            }
        });
    }

    private void abreFormularioEditar(Personagem personagemEscolhido) {
        Intent vaiParaoFormulario = new Intent(ListaPersonagemActivity.this, FormularioPersonagemActivity.class); // criando modo para ver o formulario do personagem
        vaiParaoFormulario.putExtra(CHAVE_PERSONAGEM, personagemEscolhido); // pegar e utilizar os gets
        startActivity(vaiParaoFormulario); // chama o formulario
    }

    private void listaDePersonagens(ListView listaDePersonagens) {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listaDePersonagens.setAdapter(adapter); /*criando um modelo de lista*/
    }
}
