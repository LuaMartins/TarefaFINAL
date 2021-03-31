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
        contadorDeId++; // adicionar 1

    }

    public void editar( Personagem personagem){
        Personagem personagemEscolhido = null; // começar com condicionais
        for (Personagem p:
             personagens) { // for each para passar pela listagem
            if(p.getId() == personagem.getId()){
                personagemEscolhido = p; //guardar e armazenar informações
            }

        }
        if(personagemEscolhido != null) {
            int posicaoDoPersonagem = personagens.indexOf(personagemEscolhido);//posicionamento ideal
            personagens.set(posicaoDoPersonagem, personagem);
        }


    }

    public List<Personagem> todos(){
       return new ArrayList<>(personagens);
    }
}
