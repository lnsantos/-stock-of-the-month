package br.faj.lootmonth;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import br.faj.lootmonth.entidade.Usuario;
import br.faj.lootmonth.firebase.UsuarioDAO;

public class NovoUsuarioActivity extends AppCompatActivity {

    Spinner campus,tipoUsuario;
    EditText usuario,senha;
    Button cadastrar;

    UsuarioDAO uDAO;
    Usuario u;

    List<String> campusLista,tipoUserLista;
    // Recupera a instância do DB do Firebase, dessa forma consigo esta fazendo
    // alterações no banco do google
    // getReference() => voltando para o NÓ Raiz do banco
    // private DatabaseReference fireDB = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_usuario);

        final Snackbar mensagem = Snackbar.make(findViewById(R.id.layoutNovoUsuario),criaID(), Snackbar.LENGTH_SHORT);
        mensagem.show();

        uDAO = new UsuarioDAO();
        u = new Usuario();
        // Ligando XML -> JAVA
            campus = findViewById(R.id.spinner_campus);
            tipoUsuario = findViewById(R.id.spinner_tipoUsuario);
            usuario = findViewById(R.id.edt_novoUsuario);
            senha = findViewById(R.id.edt_senha);
            cadastrar = findViewById(R.id.btn_cadastrar);
        // END XML -> JAVA

        // S P I N N E R S
            // Criando um array com as informações que vão aparecer no spinner
            campusLista = new ArrayList<>();
            campusLista.add("CP1 UniFaj");
            campusLista.add("CP2 UniFaj");
            campusLista.add("CP3 UniFaj");
            campusLista.add("CP4 UniFaj");
            campusLista.add("CP5 UniFaj");

            tipoUserLista = new ArrayList<>();
            tipoUserLista.add("Administrador");
            tipoUserLista.add("Moderador");
            tipoUserLista.add("Visualizador");

            // Pegando os arrays de String e colocando em um Adapter de String para leitura no spinner
            ArrayAdapter<String> adapterCampus = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,campusLista);
            ArrayAdapter<String> adapterTipoUsuario = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,tipoUserLista);

            // Inflando o Adapter de String no Spinner, JAVA -> LAYOUT
            campus.setAdapter(adapterCampus);
            tipoUsuario.setAdapter(adapterTipoUsuario);
        // FINAL S P I N N E R S

        // capturando as informações do usuário da tela, e cadastrando no Database
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verifica se algum campo esta vazio
                if(campus.toString().equals("")||
                   tipoUsuario.toString().equals("")||
                   usuario.getText().toString().equals("")||
                   senha.getText().toString().equals("")){
                    // Mostra mensagem na tela
                    final Snackbar mensagem = Snackbar.make(findViewById(R.id.layoutNovoUsuario),"Preencha todos os campos!", Snackbar.LENGTH_SHORT);
                    mensagem.show();
                }else{
                    /*
                       Pega os elementos da tela, joga dentro de um objeto usuario
                       depois chama método que inseri no Database do Google
                    */
                    final Usuario u = new Usuario();
                    u.setCodigo(criaID());
                    u.setUsuario(usuario.getText().toString());
                    u.setSenha(senha.getText().toString());
                    u.setCampus(spinnerToString(campusLista,campus));
                    u.setTipo_usuario(spinnerToString(tipoUserLista,tipoUsuario));

                    // Mostra mensagem informando que o usuário foi inserido com sucesso!
                    final Snackbar mensagem = Snackbar.make(findViewById(R.id.layoutNovoUsuario), u.getUsuario() + " :: "+ uDAO.novoUsuario(u), Snackbar.LENGTH_SHORT);
                    mensagem.show();
                }
            }
        });
    }

    // esse método recebe o array que ele vai busca o item que você
    // deseja encontrar o nome do spinner
    private String spinnerToString(List<String> array, Spinner item){
         int posicao = item.getSelectedItemPosition();
         return array.get(posicao);
    }

    private String criaID(){
        // Gera um número aleatório
        Random r = new Random();
        // Captura a data e hora atual do sistema
        Date data = new Date();
        // Formata a data
        SimpleDateFormat formataData = new SimpleDateFormat("S");
        String valor_misturado = formataData.format(data);
        /* Significado das letras
            Y = ANO
            M = MÊS DO ANO
            D = DIA DO ANO
            d = DIA DO MÊS
            H = HORA DO DIA
            h = HORA AM/PM
            m = MINUTO DAS HORAS
            s = SEGUNDOS EM MINUTOS
            S = MILESSIMOS DE SEGUNDOS EM NUMEROS
        */
        return "FAJ_ID::"+valor_misturado+"::"+r.nextInt(999);
    }
}
