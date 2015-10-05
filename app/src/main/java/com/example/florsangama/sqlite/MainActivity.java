package com.example.florsangama.sqlite;

import android.app.AlertDialog;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import Conexion.Sqlite;


public class MainActivity extends ActionBarActivity {
    Sqlite cx;
    EditText edt1, edt2;
    ListView lista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cx = new Sqlite(this, "bdusuario", null, 1);
        edt1 = (EditText) findViewById(R.id.edt1);
        edt2 = (EditText) findViewById(R.id.edt2);
        lista = (ListView) findViewById(R.id.list_item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void insert_us(View v) {
        if (cx.getWritableDatabase() != null) {
            cx.getWritableDatabase().execSQL("INSERT INTO usuario(campo1, campo2)values('" +
                    edt1.getText().toString() + "','" + edt2.getText().toString() + "')");
            Toast.makeText(this.getApplicationContext(), "Insertando", Toast.LENGTH_SHORT).show();
            red_us();
            limpiar();

        }
    }

    public  void red_us(){
     Cursor cu=cx.getWritableDatabase().rawQuery("SELECT campo1  FROM usuario",null);
        final String[] data=new String[cu.getCount()];
        int n=0;
        if (cu.moveToFirst()){
            do {
                data[n]=cu.getString(0).toString();
                n++;
            }while (cu.moveToNext());
        }
        lista.setAdapter(new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,data));
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            eliminar(data[position]);

                Toast.makeText(getApplicationContext(),"se elimino",Toast.LENGTH_LONG).show();

            }
        });
    }
    public  void limpiar(){
        edt1.setText("");
        edt2.setText("");
    }

    public void eliminar(String campo){
        cx.getWritableDatabase().delete("usuario","campo1='"+campo+"'",null);
        cx.close();
        red_us();
    }
}


