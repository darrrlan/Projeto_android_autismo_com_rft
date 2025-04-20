/*
    Copyright (C) 2024  Lucio A. Rocha

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

package br.edu.utfpr.darlantcc_v1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import br.edu.utfpr.darlantcc_v1.model.Pessoa;
import br.edu.utfpr.darlantcc_v1.persistencia.PessoaDatabase;
import br.edu.utfpr.darlantcc_v1.utils.UtilsGUI;

public class ActivityCadastrarPessoa extends AppCompatActivity {

    public static final String CAMPO_NOME = "CAMPO_NOME";
    public static final String CAMPO_IDADE = "CAMPO_IDADE";
    public static final String MODO = "MODO";
    public static final int NOVO = 1;
    public static final int ALTERAR = 2;

    private TextView cadastro_ID;
    private EditText cadastro_etNome;
    private Spinner cadastro_spinnerIdade;

    private int modo;

    public static final int RESULT_DELETED = 1;

    private ArrayList<Pessoa> listaPessoas;
    private PessoaDatabase pessoaDatabase;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cadastrar_pessoa);

        setTitle(getResources().getString(R.string.cadastro_titulo));

        // Pega o nome do app da string
        String appName = getResources().getString(R.string.app_name);

        // Cria um SpannableString para estilizar as letras
        SpannableString spannableTitle = new SpannableString(appName);

        // Define a cor das letras individualmente
        spannableTitle.setSpan(new ForegroundColorSpan(Color.parseColor("#1976D2")), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // Primeira letra (T)
        spannableTitle.setSpan(new ForegroundColorSpan(Color.parseColor("#D32F2F")), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // Segunda letra (E)
        spannableTitle.setSpan(new ForegroundColorSpan(Color.parseColor("#FBC02D")), 2, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // Terceira letra (A)

        // Para o resto da palavra, não aplicamos cores, então elas ficam com a cor padrão da ActionBar

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(spannableTitle);
        }


        pessoaDatabase = PessoaDatabase.getDatabase(this);
        listaPessoas = (ArrayList<Pessoa>) pessoaDatabase.pessoaDAO().queryAll();

        cadastro_ID = findViewById(R.id.cadastro_id); // <-- CORREÇÃO AQUI
        cadastro_etNome = findViewById(R.id.cadastro_et_nome);
        cadastro_spinnerIdade = findViewById(R.id.spinnerIdade);

        String[] lista = getResources().getStringArray(R.array.array_idade);
        String[] lista_campo_nulo = new String[lista.length + 1];
        lista_campo_nulo[0] = "";
        for (int i = 1; i < lista_campo_nulo.length; i++) {
            lista_campo_nulo[i] = lista[i - 1];
        }

        ArrayAdapter adapterIdade =
                new ArrayAdapter(ActivityCadastrarPessoa.this, R.layout.spinner_item, lista_campo_nulo);
        cadastro_spinnerIdade.setAdapter(adapterIdade);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            modo = bundle.getInt(MODO);
            if (modo == ALTERAR) {
                cadastro_ID.setText(bundle.getString("CAMPO_ID"));
                cadastro_etNome.setText(bundle.getString("CAMPO_NOME"));

                String campoIdade = bundle.getString("CAMPO_IDADE");
                String[] listaIdade = getResources().getStringArray(R.array.array_idade);

                int posicao = 0;
                for (int i = 0; i < listaIdade.length; i++) {
                    if (listaIdade[i].equals(campoIdade))
                        posicao = i;
                }
                cadastro_spinnerIdade.setSelection(posicao + 1);
            }
        }
    }

    public static void novoCadastro(AppCompatActivity activityPessoaes) {
        Intent intent = new Intent(activityPessoaes, ActivityCadastrarPessoa.class);
        intent.putExtra(MODO, NOVO);
        activityPessoaes.startActivityForResult(intent, NOVO);
    }

    public static void alterarCadastro(AppCompatActivity activityPessoas, Pessoa pessoa) {
        Intent intent = new Intent(activityPessoas, ActivityCadastrarPessoa.class);
        intent.putExtra(MODO, ALTERAR);
        intent.putExtra("CAMPO_ID", String.valueOf(pessoa.getID()));
        intent.putExtra("CAMPO_NOME", pessoa.getNOME());
        intent.putExtra("CAMPO_IDADE", pessoa.getIDADE());
        activityPessoas.startActivityForResult(intent, ALTERAR);
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

    public void salvar(AppCompatActivity activityCadastro) {
        Pessoa pessoa;
        if (modo == NOVO)
            pessoa = new Pessoa();
        else
            pessoa = pessoaDatabase.pessoaDAO().queryForId(Integer.parseInt(cadastro_ID.getText().toString()));

        Intent intent = new Intent();
        String mensagem = "";

        String campoNome = UtilsGUI.validaCampoTexto(this,
                cadastro_etNome,
                getString(R.string.cadastro_nomevazio));
        if (campoNome == null) {
            return;
        }

        mensagem += getString(R.string.cadastro_nome) + campoNome + "\n";
        intent.putExtra(CAMPO_NOME, campoNome);
        pessoa.setNOME(campoNome);

        mensagem += getString(R.string.cadastro_idade);
        String campoIdade = (String) cadastro_spinnerIdade.getSelectedItem();
        if (campoIdade.equals("")) {
            UtilsGUI.avisoErro(this,
                    getString(R.string.cadastro_idade_naoSelecionada));
            return;
        }

        mensagem += campoIdade + "\n";
        intent.putExtra(CAMPO_IDADE, campoIdade);
        pessoa.setIDADE(campoIdade);

        if (modo == NOVO) {
            pessoaDatabase.pessoaDAO().insert(pessoa);
        } else {
            pessoa.setID(Integer.parseInt(cadastro_ID.getText().toString()));
            pessoaDatabase.pessoaDAO().update(pessoa);
        }

        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
        Log.d("LOG:", mensagem);

        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void excluir(AppCompatActivity activityCadastro) {
        Intent intent = new Intent();
        if (!cadastro_ID.getText().toString().equals("-1")) {
            int idPessoa = Integer.parseInt(cadastro_ID.getText().toString());
            Pessoa pessoa = pessoaDatabase.pessoaDAO().queryForId(idPessoa);

            String mensagem = getString(R.string.cadastro_apagar) + "\n\n" +
                    getString(R.string.cadastro_nome) + pessoa.getNOME() + "\n\n";

            DialogInterface.OnClickListener listener = (dialogInterface, i) -> {
                if (i == DialogInterface.BUTTON_POSITIVE) {
                    pessoaDatabase.pessoaDAO().delete(pessoa);
                    Toast.makeText(ActivityCadastrarPessoa.this, R.string.cadastro_removido, Toast.LENGTH_SHORT).show();
                    Log.d("LOG:", getString(R.string.cadastro_removido));
                    setResult(RESULT_DELETED, intent);
                    finish();
                }
            };

            UtilsGUI.confirmaAcao(ActivityCadastrarPessoa.this, mensagem, listener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cadastro_opcoes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuItemSalvar) {
            salvar(this);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    // Teste simples para validação unitária
    public static int teste_inserirCadastro() {
        return 1;
    }
}
