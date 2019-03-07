package br.faj.lootmonth;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.faj.lootmonth.entidade.Usuario;
import br.faj.lootmonth.firebase.UsuarioDAO;
import br.faj.lootmonth.listeners.FicaNoApp;
import br.faj.lootmonth.listeners.SairDoApp;

public class MainActivity extends AppCompatActivity {

    Button acessar, atalho;
    AlertDialog.Builder codigoAtalho;
    Snackbar mensagem;

    // Recupera a instância do DB do Firebase, dessa forma consigo esta fazendo
    // alterações no banco do google
    // getReference() => voltando para o NÓ Raiz do banco
    private DatabaseReference fireDB = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference colaboradoresDB = fireDB.child("colaboradores");

    List<Usuario> colaboradores = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ligando XML -> JAVA
            acessar = findViewById(R.id.btn_acessar);
            atalho = findViewById(R.id.btn_atalho);
        // END XML -> JAVA

            acessar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verificaLogin("lnsantos","01");
                }
            });

            atalho.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Iniciando a criação do dialog
                    codigoAtalho = new AlertDialog.Builder(MainActivity.this);
                    // Titulo do Dialog
                    codigoAtalho.setTitle("Codigo de Atalho");
                    // EditText que irá recebe o código
                    final EditText campoCodigoAtalho = new EditText(getApplicationContext());
                    // colocar nosso EditText no Dialog
                    codigoAtalho.setView(campoCodigoAtalho);
                    // Botão para verificar codigo
                    codigoAtalho.setPositiveButton("Verificar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // verifica código pré-definido
                            String codigo = campoCodigoAtalho.getText().toString();
                            if(codigo.equals("cad001")){
                                // Vai abrir a tela para cadastrar usuários no sistema
                                startActivity(new Intent(MainActivity.this,NovoUsuarioActivity.class));
                                // finaliza a activity atual
                                // finish();
                            }else {
                                // aviso para o usuário
                                /*
                                    Lembrando que a referencia do ID, tem que ser o ID do elemento layout inteiro
                                    ( Coloque um ID no ConstraintLayout ou no Layout que você esteja usando)

                                    Depois de colocar o ID, precisamos informar a mensagem que irá para-se sobre
                                    o layout que colocamos o ID

                                    Após isso, precisamos informar a velocidade que ele irá, sair da tela
                                */
                                mensagem = Snackbar.make(findViewById(R.id.layoutMain),"Código inválido", Snackbar.LENGTH_SHORT);
                                mensagem.show();
                            }
                        }
                    });
                    codigoAtalho.show();
                }
            });
    }
    /*
        onBack é o botão de retorno do celular
        então temos um método para ele
    */
    @Override
    public void onBackPressed() {
        // Iniciando a criação do dialog
        codigoAtalho = new AlertDialog.Builder(MainActivity.this);
        // Titulo do Dialog
        codigoAtalho.setTitle("Deseja realmente sair do aplicativo");
        // Botões caso usuario deseja sair do aplicativo
        codigoAtalho.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // finalizara tela atual
                finish();
            }
        });
        // Botões caso usuario não deseja sair do aplicativo
        codigoAtalho.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Mensagem para usuari
                mensagem = Snackbar.make(findViewById(R.id.layoutMain),"Aplicativo não finalizado!", Snackbar.LENGTH_SHORT);
                mensagem.show();
            }
        });
        codigoAtalho.show();
    }

    public void verificaLogin(final String usuario, final String senha){
        // Recuperando todos os colaboradores cadastrado no database
        DatabaseReference colaboradoresDB = fireDB.child("colaboradores").child(usuario);
        colaboradoresDB.addValueEventListener(new ValueEventListener()  {
            // Ele fica escutando o database, para retornar as informações/alterações
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                colaboradores.clear();
                for (DataSnapshot dSnapshot : dataSnapshot.getKey(usu)) {
                    // colaboradores.add(dSnapshot.getValue(Usuario.class));
                    String usuarioFB = dSnapshot.getValue();
                }
                for (Usuario u : colaboradores ){
                    if(u.getUsuario().equals(usuario) && u.getSenha().equals(senha)){
                        startActivity(new Intent(getApplicationContext(), SplashActivity.class));
                    }else{
                        Toast.makeText(MainActivity.this, "Usuário inválido", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            // Caso ocorra algum problema no processo de recuperar informaçõe
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

}
