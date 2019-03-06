package br.faj.lootmonth.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.faj.lootmonth.entidade.Usuario;

public class UsuarioDAO {

    // Recupera a instância do DB do Firebase, dessa forma consigo esta fazendo
    // alterações no banco do google
    // getReference() => voltando para o NÓ Raiz do banco
    private DatabaseReference fireDB = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference colaboradoresDB = fireDB.child("colaboradores");

    // Método que cria um novo usuario no NÓ do fireBase
    public String novoUsuario(Usuario u){
        // Coloca o indentificador do usuario
        colaboradoresDB.child(u.getCodigo()).child("usuario").setValue(u.getUsuario());
        colaboradoresDB.child(u.getCodigo()).child("senha").setValue(u.getSenha());
        colaboradoresDB.child(u.getCodigo()).child("campus").setValue(u.getCampus());
        colaboradoresDB.child(u.getCodigo()).child("tipo_usuario").setValue(u.getTipo_usuario());

        return "Inserido com sucesso!";
    }

}
