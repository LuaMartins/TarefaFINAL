package com.example.myapplication.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.dao.PersonagemDAO;
import com.example.myapplication.model.Personagem;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.io.Serializable;

import static com.example.myapplication.ui.activities.ConstantesActivities.CHAVE_PERSONAGEM;

public class FormularioPersonagemActivity extends AppCompatActivity {

    // criação de todos os nomes, incluindo strings e editTexts
    public static final String TITULO_APPBAR_EDITA_PERSONAGEM = "Editar Personagem";
    public static final String TITULO_APPBAR_NOVO_PERSONAGEM = "Novo Personagem";
    private EditText campoNome;
    private EditText campoAltura;
    private EditText campoNascimento;
    private final PersonagemDAO dao = new PersonagemDAO();
    private Personagem personagem;


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_formulario_personagem_menu_salvar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int itemId = item.getItemId();
        if(itemId == R.id.activity_formulario_personagem_menu_salvar){
            finalizarFormulario();
        }
        return super.onOptionsItemSelected(item);
    }
    // usando o onCreate e tambem puxando os metodos citados a baixo
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_personagem);
        inicializacaoCampos();
        carregaPersonagem();
        configuraBotaoSalvar();
    }

    private void carregaPersonagem() {
        Intent dados = getIntent(); // instanciei meus dados
        if(dados.hasExtra(CHAVE_PERSONAGEM)) {
            setTitle(TITULO_APPBAR_EDITA_PERSONAGEM); //settar o titulo
            personagem = (Personagem) dados.getSerializableExtra(CHAVE_PERSONAGEM);
            //buscando as 3 informações
            preencheCampos();
        } else{
            setTitle(TITULO_APPBAR_NOVO_PERSONAGEM);
            personagem = new Personagem();
        }
    }
    // metodo de preencher campos, puxando e levando informações
    private void preencheCampos() {
        campoNome.setText(personagem.getNome());
        campoAltura.setText(personagem.getAltura());
        campoNascimento.setText(personagem.getNascimento());
    }

    private void configuraBotaoSalvar() {
        Button botaosalvar = findViewById(R.id.button_salvar); // pegando botão para colocar ações
        botaosalvar.setOnClickListener(new View.OnClickListener() { //salvando a ação

            @Override
            public void onClick(View view) {
                finalizarFormulario();
            } //puxando as infos o metodo finalizar formulario
        });
    }

    private void finalizarFormulario() { //metodo criado para finalizar o formulario
        preencherPersonagem();

        if(personagem.IdValido()){
            dao.editar(personagem); // edição de personagem ao clicar no personagem
            finish(); // finalizar a ação
        } else {
            dao.salva(personagem); // se não, salva o personagem
        }
        finish();
    }

    private void inicializacaoCampos() {
        // pegando os id's
        campoNome = findViewById(R.id.editText_nome);
        campoNascimento = findViewById(R.id.editText_nascimento);
        campoAltura = findViewById(R.id.editText_altura);

        //atribuindo os valores para atribuir na agenda os valores de altura e nascimento
        SimpleMaskFormatter smfAltura = new SimpleMaskFormatter("N,NN");
        MaskTextWatcher mtwAltura = new MaskTextWatcher(campoAltura, smfAltura); //crição de mascara para identificar o campo
        campoAltura.addTextChangedListener(mtwAltura); // local para adicionar o texto

        SimpleMaskFormatter smfNascimento = new SimpleMaskFormatter("NN/NN/NNNN"); // mesma coisa que dia, mes e ano
        MaskTextWatcher mtwNascimento = new MaskTextWatcher(campoNascimento, smfNascimento);
        campoNascimento.addTextChangedListener(mtwNascimento);

    }

    private void preencherPersonagem(){ //metodo para preencher os peronagens na lista

        String nome = campoNome.getText().toString();
        String nascimento = campoNascimento.getText().toString();
        String altura = campoAltura.getText().toString();

        // puxando os 3 set's de nome, altura e nascimento
        personagem.setNome(nome);
        personagem.setAltura(altura);
        personagem.setNascimento(nascimento);

    }


}