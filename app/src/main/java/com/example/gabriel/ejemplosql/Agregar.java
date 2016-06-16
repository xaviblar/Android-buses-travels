package com.example.gabriel.ejemplosql;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class Agregar extends Activity {
    BDHelper MyDB;
    EditText etNombre,etDescripcion,etPrecio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);
        MyDB = new BDHelper(this);
        etNombre = (EditText) findViewById(R.id.etnombreproducto);
        etDescripcion = (EditText) findViewById(R.id.etdescripcion);
        etPrecio = (EditText) findViewById(R.id.etprecio);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== R.id.agregarproducto){
            Cursor rs = MyDB.getData(etNombre.getText().toString());
            if(rs.moveToFirst()) {
                Toast.makeText(getApplicationContext(),"Repetido", Toast.LENGTH_SHORT).show();
                if (!rs.isClosed()) {
                    rs.close();
                }
            }else {
                MyDB.insertContact(etNombre.getText().toString(), etDescripcion.getText().toString(),
            etPrecio.getText().toString());
                finish();
            }
        }
        if (item.getItemId()== R.id.editarproducto){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }




}
