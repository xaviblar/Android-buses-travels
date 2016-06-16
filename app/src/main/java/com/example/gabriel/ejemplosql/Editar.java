package com.example.gabriel.ejemplosql;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class Editar extends Activity {
    BDHelper MyDB;
    EditText etNombre,etDescripcion,etPrecio;
    Bundle bundle;
    String oldName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        //Bloquea la rotación
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        MyDB = new BDHelper(this);
        etNombre = (EditText) findViewById(R.id.etnombreproducto);
        etDescripcion = (EditText) findViewById(R.id.etdescripcion);
        etPrecio = (EditText) findViewById(R.id.etprecio);
        bundle = getIntent().getExtras();
        oldName = bundle.getString("nombre");
        Cursor rs = MyDB.getData(oldName);
        if (rs.moveToFirst()) {
            etNombre.setText(rs.getString(0));
            etDescripcion.setText(rs.getString(1));
            etPrecio.setText(rs.getString(2));
            if (!rs.isClosed()) {
                rs.close();
            }
        } else {
            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editar, menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.agregarproducto) {
            if(!MyDB.updateContact(oldName,etNombre.getText().toString(),
            etDescripcion.getText().toString(),
            etPrecio.getText().toString())){
                Toast.makeText(getApplicationContext(),"Repetido",Toast.LENGTH_SHORT).show();
            }
            finish();
        }
        if (item.getItemId() == R.id.cancelar){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
