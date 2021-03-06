package br.ufpe.cin.if1001.intents;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText campoTexto = (EditText) findViewById(R.id.campoTexto);
        Button implicit = (Button) findViewById(R.id.botaoImplicit);
        Button explicit = (Button) findViewById(R.id.botaoExplicit);

        implicit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = campoTexto.getText().toString();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(txt));
                startActivity(intent);

            }
        });

        explicit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = campoTexto.getText().toString();
                Intent intent = new Intent(getApplicationContext(), OutraActivity.class);
                intent.putExtra("texto",txt);
                startActivity(intent);
            }
        });

        Button implicitMaps = (Button) findViewById(R.id.botaoImplicitMaps);
        Button implicitSend = (Button) findViewById(R.id.botaoImplicitSend);

        implicitMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = campoTexto.getText().toString();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                //intent.setData(Uri.parse("geo:0,0?q=" + txt));
                intent.setData(Uri.parse("https://www.youtube.com/search?q=" + txt));
                //intent.setData(Uri.parse("https://www.google.com/search?q=" + txt));
                startActivity(intent);

            }
        });

        implicitSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = campoTexto.getText().toString();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_SUBJECT, "algum subject");
                intent.putExtra(Intent.EXTRA_TEXT, txt);
                //se nao marcar isto aqui, pode dar pau
                intent.setType("text/plain");
                startActivity(intent);

            }
        });





    }
}
