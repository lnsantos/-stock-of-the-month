package br.faj.lootmonth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class NovoUsuarioActivity extends AppCompatActivity {

    Spinner campus,tipoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_usuario);

        // Ligando XML -> JAVA
            campus = findViewById(R.id.spinner_campus);
            tipoUsuario = findViewById(R.id.spinner_tipoUsuario);
        // END XML -> JAVA

        // S P I N N E R S
            // Criando um array com as informações que vão aparecer no spinner
            String [] campusArray = {"CP1 UniFaj", "CP2 UniFaj", "CP3 UniFaj", "CP4 UniFaj", "CP5 UniFaj"};
            String [] tipoUsuarioArray = {"Administrador","Moderador","Visualizador"};

            // Pegando os arrays de String e colocando em um Adapter de String para leitura no spinner
            ArrayAdapter<String> adapterCampus = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,campusArray);
            ArrayAdapter<String> adapterTipoUsuario = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,tipoUsuarioArray);

            // Inflando o Adapter de String no Spinner, JAVA -> LAYOUT
            campus.setAdapter(adapterCampus);
            tipoUsuario.setAdapter(adapterTipoUsuario);
        // FINAL S P I N N E R S
    }
}
