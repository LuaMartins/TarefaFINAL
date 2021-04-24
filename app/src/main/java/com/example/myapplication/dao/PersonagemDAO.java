package com.example.myapplication.dao;

import com.example.myapplication.model.Personagem;

import java.util.ArrayList;
import java.util.List;

public class PersonagemDAO {
    private final static List<Personagem> personagens = new ArrayList<>(); // declaração
    private static int contadorDeId = 1; // atribuir contador para 1



    public void salva(Personagem personagemSalvo) { // salvar item
        personagemSalvo.setId(contadorDeId); // cada personagem salvo add um id

        personagens.add(personagemSalvo); // adicionar personagens
        atualizarID();

    }

    private void atualizarID() {
        contadorDeId++; // adicionar 1
    }

    public void editar( Personagem personagem){
        Personagem personagemEscolhido = buscaPersonagemID(personagem); //buscar o personagem escolhido

        if(personagemEscolhido != null) {
            int posicaoDoPersonagem = personagens.indexOf(personagemEscolhido);//posicionamento ideal
            personagens.set(posicaoDoPersonagem, personagem);
        }


    }

    private Personagem buscaPersonagemID(Personagem personagem) {
        for (Personagem p:
             personagens) { // for each para passar pela listagem
            if(p.getId() == personagem.getId()){
                return p; //guardar e armazenar informações
            }

        }
        return null;
    }

    public List<Personagem> todos(){
       return new ArrayList<>(personagens);
    }

    // usado para remover item da classe de persistencia
    public void remove(Personagem personagem){
        Personagem personagemDevolvido = buscaPersonagemID(personagem);
        if(personagemDevolvido != null){
            personagens.remove(personagemDevolvido);
        }
    }
}
