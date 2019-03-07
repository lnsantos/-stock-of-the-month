package br.faj.lootmonth.firebase;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.faj.lootmonth.MainActivity;
import br.faj.lootmonth.entidade.Usuario;
import br.faj.lootmonth.interfaces.DatabaseValue;

public class UsuarioDAO implements DatabaseValue {

    // Recupera a instância do DB do Firebase, dessa forma consigo esta fazendo
    // alterações no banco do google
    // getReference() => voltando para o NÓ Raiz do banco
    private DatabaseReference fireDB = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference colaboradoresDB = fireDB.child("colaboradores");

    List<Usuario> colaboradores = new ArrayList<>();

    // Método que cria um novo usuario no NÓ do fireBase
    public String novoUsuario(Usuario u){
        // Coloca o indentificador do usuario
        colaboradoresDB.child(u.getUsuario());
        colaboradoresDB.child(u.getUsuario()).child("senha").setValue(u.getSenha());
        colaboradoresDB.child(u.getUsuario()).child("campus").setValue(u.getCampus());
        colaboradoresDB.child(u.getUsuario()).child("tipo_usuario").setValue(u.getTipo_usuario());

        return "Inserido com sucesso!";
    }

    public void verificaLogin(final String usuario, final String senha){
        // Recuperando todos os colaboradores cadastrado no database
        DatabaseReference colaboradoresDB = fireDB.child("colaboradores").child(usuario);
        colaboradoresDB.addValueEventListener(new ValueEventListener()  {
            // Ele fica escutando o database, para retornar as informações/alterações
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dSnapshot : dataSnapshot.getChildren()) {
                    colaboradores.add(dSnapshot.getValue(Usuario.class));
                }
                for (Usuario u : colaboradores ){
                    if(u.getUsuario().equals(usuario) && u.getSenha().equals(senha)){

                    }
                }
            }
            // Caso ocorra algum problema no processo de recuperar informaçõe
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    @Override
    public String valorDB(String valor) {
        return valor;
    }
}
