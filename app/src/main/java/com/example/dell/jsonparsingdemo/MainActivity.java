package com.example.dell.jsonparsingdemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button btnhit;
    private TextView jsonitem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText apiurl = (EditText) findViewById(R.id.apiurl);
        btnhit=(Button)findViewById(R.id.btnhit);
        jsonitem = (TextView) findViewById(R.id.jsonitem);

        btnhit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new JSONTask().execute("http://jsonparsing.parseapp.com/jsonData/moviesDemoItem.txt");
            }
        });

    }
}
class JSONTask extends AsyncTask<String,String,String>{
    @Override
    protected String doInBackground(String... urls) {
        HttpURLConnection connection = null;
        BufferedReader reader=null;
        try{
            String api="";
            URL url=new URL(urls[0]);
            connection=(HttpURLConnection)url.openConnection();
            connection.connect();

            InputStream stream=connection.getInputStream();
            reader=new BufferedReader((new InputStreamReader(stream)));
            StringBuffer buffer=new StringBuffer();

            String line="";
            while((line=reader.readLine())!=null) {
                buffer.append(line);
            }
            return buffer.toString();
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            if(connection!=null)
                connection.disconnect();
            try {
                if(reader!=null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        jsonitem.setText(s);
        super.onPostExecute(s);

    }
}