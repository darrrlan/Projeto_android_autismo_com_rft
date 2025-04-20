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

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.text.BreakIterator;
import java.util.ArrayList;

import android.graphics.Color;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import br.edu.utfpr.darlantcc_v1.model.Pessoa;
import br.edu.utfpr.darlantcc_v1.persistencia.PessoaDatabase;
import br.edu.utfpr.darlantcc_v1.utils.UtilsGUI;

public class ActivityVisualizarPessoas extends AppCompatActivity {

    private ListView listViewPessoas;
    private PessoaAdapter listaAdapter;
    private ArrayList<Pessoa> listaPessoas;
    private PessoaDatabase pessoaDatabase;
    private int posicaoSelecionada = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoas);



        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setTitle(getResources().getString(R.string.app_name));

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            SpannableString spannableTitle = new SpannableString("TEAlink");

            spannableTitle.setSpan(new ForegroundColorSpan(Color.parseColor("#1976D2")), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // T
            spannableTitle.setSpan(new ForegroundColorSpan(Color.parseColor("#D32F2F")), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // E
            spannableTitle.setSpan(new ForegroundColorSpan(Color.parseColor("#FBC02D")), 2, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // A

            // As letras "link" ficam com a cor padrão da ActionBar

            actionBar.setTitle(spannableTitle);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ImageButton btnAdicionar = findViewById(R.id.btnAdicionarPessoa);
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCadastrarPessoa.novoCadastro(ActivityVisualizarPessoas.this);
            }
        });

        listViewPessoas = findViewById(R.id.listViewPessoas);
        registerForContextMenu(listViewPessoas);

        listViewPessoas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                posicaoSelecionada = i;
                alterarPessoa();
            }
        });

        listViewPessoas.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        popularPessoas();
        popularLista();
    }

    private void popularPessoas() {
        pessoaDatabase = PessoaDatabase.getDatabase(this);
        listaPessoas = (ArrayList<Pessoa>) pessoaDatabase.pessoaDAO().queryAll();

        if (listaPessoas.size() < 6) {
            for (int i = 0; i < 6; i++) {
                Pessoa pessoa = new Pessoa();
                pessoa.setNOME("Fulano" + i);
                listaPessoas.add(pessoa);
                pessoaDatabase.pessoaDAO().insert(pessoa);
            }
            listaPessoas = (ArrayList<Pessoa>) pessoaDatabase.pessoaDAO().queryAll();
        }
    }

    private void popularLista() {
        listaPessoas = (ArrayList<Pessoa>) pessoaDatabase.pessoaDAO().queryAll();
        listaAdapter = new PessoaAdapter(this, R.layout.linha_pessoa, listaPessoas);
        listViewPessoas.setAdapter(listaAdapter);
    }

    private void alterarPessoa() {
        Pessoa pessoa = listaPessoas.get(posicaoSelecionada);
        ActivityCadastrarPessoa.alterarCadastro(this, pessoa);
    }

    public static void iniciar(ActivityInicio activityInicio) {
        Intent intent = new Intent(activityInicio, ActivityVisualizarPessoas.class);
        activityInicio.startActivityForResult(intent, 1);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pessoa_opcoes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if (item.getItemId() == R.id.menuItemCadastroExcluir) {
            excluir(info.position);
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.pessoa_menu_contexto, menu);
    }

    private void excluir(int pos) {
        String mensagem = getString(R.string.cadastro_desejaexcluir);

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == DialogInterface.BUTTON_POSITIVE) {
                    Pessoa pessoa = listaPessoas.get(pos);
                    pessoaDatabase.pessoaDAO().delete(pessoa);
                    listaAdapter.clear();
                    popularLista();
                    listaAdapter.notifyDataSetChanged();
                    Toast.makeText(ActivityVisualizarPessoas.this, R.string.cadastro_removido, Toast.LENGTH_SHORT).show();
                }
            }
        };

        UtilsGUI.confirmaAcao(this, mensagem, listener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                String nome = bundle.getString(ActivityCadastrarPessoa.CAMPO_NOME);
                String idade = bundle.getString(ActivityCadastrarPessoa.CAMPO_IDADE);

                if (requestCode == ActivityCadastrarPessoa.ALTERAR) {
                    posicaoSelecionada = -1;
                } else {
                    Pessoa pessoa = new Pessoa();
                    pessoa.setNOME(nome);
                    pessoa.setIDADE(idade);
                    listaPessoas.add(pessoa);
                }

                listaAdapter.clear();
                popularLista();
                listaAdapter.notifyDataSetChanged();
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(this, R.string.cadastro_cancelado, Toast.LENGTH_SHORT).show();
        } else if (resultCode == ActivityCadastrarPessoa.RESULT_DELETED) {
            Toast.makeText(this, R.string.cadastro_removido, Toast.LENGTH_SHORT).show();
            listaAdapter.clear();
            popularLista();
            listaAdapter.notifyDataSetChanged();
        }
    }
}
