package br.edu.utfpr.darlantcc_v1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.util.ArrayList;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import br.edu.utfpr.darlantcc_v1.model.Pessoa;
import br.edu.utfpr.darlantcc_v1.persistencia.PessoaDatabase;
import br.edu.utfpr.darlantcc_v1.utils.UtilsGUI;

public class ActivityInicio extends AppCompatActivity {

    private LinearLayout botaoCadastro;
    private LinearLayout botaoQuestionario;
    private LinearLayout botaoRespostasSalvas;

    public PessoaDatabase pessoaDatabase;  //Todos os jogadores na BD Room

    public static Context contextToString; //Para internacionalizar a exibicao do toString da 'Cidade'

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setTheme(R.style.@style/Theme.darlantcc_v1);
        super.onCreate(savedInstanceState);
        //Seta fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_inicio);
        contextToString = this;  //Para exibir conteudo do string.xml

        //Botao de retorno
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); //Cria uma nova instancia da Activity parent
        }

        pessoaDatabase = PessoaDatabase.getDatabase(ActivityInicio.this);

        // Atualizado: LinearLayout como botão de questionário
        botaoQuestionario = findViewById(R.id.botaoQuestionario);
        botaoQuestionario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Pessoa> lista = (ArrayList<Pessoa>) pessoaDatabase.pessoaDAO().queryAll();
                if(lista.size() < 1) {
                    UtilsGUI.avisoErro(ActivityInicio.this, getResources().getString(R.string.inicio_sem_cadastro));
                    return;
                }
                ActivityQuestionario.iniciar(ActivityInicio.this);
            }
        });

        // Atualizado: LinearLayout como botão de cadastro
        botaoCadastro = findViewById(R.id.botaoCadastro);
        botaoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityVisualizarPessoas.iniciar(ActivityInicio.this);
            }
        });

        // Atualizado: LinearLayout como botão de respostas salvas
        botaoRespostasSalvas = findViewById(R.id.botaoRespostasSalvas);
        botaoRespostasSalvas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityVisualizarRespostas.iniciar(ActivityInicio.this);
            }
        });
    }

    public static void iniciar(ActivityCapa activityCapa){
        Intent intent = new Intent(activityCapa, ActivityInicio.class);
        activityCapa.startActivityForResult(intent,1); //Inicia esta ActivityInicio com requestcode=1
    }

    public void finalizar() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        finalizar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.inicio_opcoes_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.menuItemSobre){
            ActivitySobre.exibir(this);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
