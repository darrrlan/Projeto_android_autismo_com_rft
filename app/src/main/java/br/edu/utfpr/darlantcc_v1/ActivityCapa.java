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

import androidx.appcompat.app.AppCompatActivity;
import br.edu.utfpr.darlantcc_v1.ActivityInicio;
import br.edu.utfpr.darlantcc_v1.R;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityCapa extends AppCompatActivity {

    private Button botaoLogin;
    private EditText login_email;
    private EditText login_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capa);
        TextView textView = findViewById(R.id.textoBemVindo);

        String texto = "Bem-vindo\nao TEAlink";
        SpannableString spannable = new SpannableString(texto);

        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#007AFF")), texto.indexOf('T'), texto.indexOf('T') + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // Azul
        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#FFD60A")), texto.indexOf('E'), texto.indexOf('E') + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // Amarelo
        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#FF3B30")), texto.indexOf('A'), texto.indexOf('A') + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // Vermelho

        textView.setText(spannable);


        botaoLogin = (Button) findViewById(R.id.botaoLogin);
        login_email = (EditText) findViewById(R.id.login_email);
        login_password = (EditText) findViewById(R.id.login_password);

        botaoLogin.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                //Given from ExampleUnitTest
                String hashed = "$2a$12$u71koTbtxNHxKMovkfh9vuU.nyqrAfdsW0lGZ7g4vI2/65wBuJyDq";
                String password = hashed;
               // String hashed = org.mindrot.jbcrypt.BCrypt.hashpw(email,
               //         org.mindrot.jbcrypt.BCrypt.gensalt(12));

                // Check that an unencrypted password matches one that has
                // previously been hashed
                if(BCrypt.checkpw(login_email.getText().toString(), hashed) &&
                        BCrypt.checkpw(login_password.getText().toString(), password)) {
                    Toast.makeText(ActivityCapa.this, "USUARIO/SENHA OK", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(ActivityCapa.this,"Passei por aqui", Toast.LENGTH_SHORT).show();
                    ActivityInicio.iniciar(ActivityCapa.this);
                }
                else {
                    Toast.makeText(ActivityCapa.this, "USUARIO/SENHA INVÁLIDOS.", Toast.LENGTH_SHORT).show();
                    login_email.setText("");
                    login_password.setText("");
                    login_email.setFocusable(true);
                }
            }
        });


    }
}