package br.ufpe.cin.if1001.dadosmemoriaexterna;

import android.app.Activity;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends Activity {
    ImageView imagem;
    private final String arquivo = "melhorDoNordeste.png";
    private String TAG = "DadosMemoriaExterna";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button copiar = (Button) findViewById(R.id.botaoCopiar);
        Button ler = (Button) findViewById(R.id.botaoLer);
        Button limpar = (Button) findViewById(R.id.botaoLimpar);
        Button apagar = (Button) findViewById(R.id.botaoApagar);
        imagem = (ImageView) findViewById(R.id.imagem);

        copiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExternalStorageWritable()) {
                    File f = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),arquivo);
                    if (!f.exists()) {
                        copiarImagemNaMemoria(f);
                        Toast.makeText(getApplicationContext(),"Copiando...",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Já existe!",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Memória externa nao disponivel!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        limpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limparConteudo();
            }
        });

        ler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExternalStorageReadable()) {
                    File f = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),arquivo);
                    if (f.exists()) {
                        imagem.setImageURI(Uri.parse("file://" + f.getAbsolutePath()));
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Arquivo não existe!",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Memória externa nao disponivel!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        apagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExternalStorageWritable()) {
                    File f = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),arquivo);
                    if (f.exists()) {
                        f.delete();
                        Toast.makeText(getApplicationContext(),"Apagando...",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Arquivo inexistente!",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Memória externa nao disponivel!",Toast.LENGTH_SHORT).show();
                }
                limparConteudo();
            }
        });

    }

    /* Checa se memoria externa esta disponivel para leitura e escrita */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checa se memoria externa esta disponivel pelo menos para leitura */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    private void limparConteudo() {
        imagem.setImageResource(android.R.color.transparent);
        //imagem.setImageResource(0);
        //imagem.setImageDrawable(null);
    }

    private void copiarImagemNaMemoria(File f) {
        try {
            BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(f));
            BufferedInputStream is = new BufferedInputStream(getResources().openRawResource(R.raw.sport));
            copiar(is, os);
        } catch (FileNotFoundException e) {
            Log.e(TAG, "FileNotFoundException " + e.getMessage());
        }
    }

    private void copiar(InputStream is, OutputStream os) {
        final byte[] buf = new byte[1024];
        int numBytes;
        try {
            while (-1 != (numBytes = is.read(buf))) {
                os.write(buf, 0, numBytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                os.close();
            } catch (IOException e) {
                Log.e(TAG, "IOException "+e.getMessage());

            }
        }
    }
}
