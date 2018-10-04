package com.example.eko8757.Myskripsi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.eko8757.Myskripsi.Informasi.Activity_About;
import com.example.eko8757.Myskripsi.Informasi.MenuContact;
import com.example.eko8757.Myskripsi.Rating.GiveFinalRating;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private TextView tv1;
    EditText edit1;
    ToggleButton button_check;
    private AlertDialog.Builder alertKeluar;
    String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);

        alertKeluar = new AlertDialog.Builder(this);
        tv1 = (TextView) findViewById(R.id.tv1);
        edit1 = (EditText) findViewById(R.id.edit1);
        button_check = (ToggleButton) findViewById(R.id.button_check);

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Intent intent = getIntent();
        Uri data = intent.getData();

        //pengkondisian pada editText
        if (data == null) {
            edit1.setText("");
        } else {
            edit1.setText(String.valueOf(data));
        }

        //button cek
        button_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                link = edit1.getText().toString();
                String message;

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(link);
                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    HttpEntity httpEntity = httpResponse.getEntity();
                    InputStream inputStream = httpEntity.getContent();
                    StringBuffer sb = new StringBuffer();
                    String newLine;
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    while ((newLine = bufferedReader.readLine()) != null) {
                        sb.append(newLine);
                        sb.append("\n");
                    }
//                    tv1.setText(sb.toString());
//                    final double x = new GiveFinalRating(link, sb.toString()).run();
//                    ans += x;
//                    SaveFile(fileName, sb.toString());
                    inputStream.close();

                    GiveFinalRating r = new GiveFinalRating(link, sb.toString());

                    int a = r.run();
                    if (a <= 2) {
                        message = "Bukan situs phishing";
                    } else {
                        message = "Warning!!! Ini adalah situs Phishing";
                    }

                    alertDialog(message, link);

                    // aksi pada button
                    if (button_check.isChecked()) {
                        Toast.makeText(getApplicationContext(), "Check", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Uncheck", Toast.LENGTH_SHORT).show();
                    }
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private String alertDialog(String message, final String link) {

        AlertDialog.Builder report = new AlertDialog.Builder(this);
        report.setTitle("REPORT");
        report.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("NEXT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Uri uri = Uri.parse(link);
                        Log.e(TAG, "uri" + uri);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        edit1.setText("");
                        tv1.setText("");
                        dialog.cancel();
                    }
                }).create().show();
        return message;
    }

//    public void SaveFile(String file, String text) {
//        try {
//            FileOutputStream fos = openFileOutput(file, Context.MODE_PRIVATE);
//            fos.write(text.getBytes());
//            fos.close();
//            Toast.makeText(MainActivity.this, "Go!", Toast.LENGTH_SHORT).show();
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public String readFile(String file) {
//        String text = "";
//        try {
//            FileInputStream fis = openFileInput(file);
//            int size = fis.available();
//            byte[] buffer = new byte[size];
//            fis.read(buffer);
//            fis.close();
//            text = new String(buffer);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return text;
//    }

    // menutup aplikasi
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Toast.makeText(MainActivity.this, "Anda menekan back", Toast.LENGTH_SHORT).show();
        //set Title
        alertKeluar.setTitle("Tutup Aplikasi");
        //set message
        alertKeluar
                .setMessage("Apakah anda yakin ingin keluar?")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //YA
                        finish();
                    }
                })
                .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //TIDAK
                        dialog.cancel();
                        Toast.makeText(MainActivity.this, "Anda menekan TIDAK", Toast.LENGTH_SHORT).show();
                    }
                }).create().show();
    }

    // Menu pilihan
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // menampilkan menu about
        if (id == R.id.menu_about) {
            Intent i = new Intent(this, Activity_About.class);
            startActivity(i);
        }
        // menampilkan menu kontak
        if (id == R.id.menu_contact) {
            Intent i = new Intent(this, MenuContact.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

}
