package com.example.gabriel.ejemplosql;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    Context context = this;
    String seleccionado;
    ArrayList<ListData> arrayListData;
    BDHelper MyDB;
    ListView lvDatos;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyDB = new BDHelper(this);
        lvDatos = (ListView) findViewById(R.id.listView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.agregarmenuitem) {
            Intent intent = new Intent(getApplicationContext(), Agregar.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public class ViewAdapter extends BaseAdapter {
        LayoutInflater mInflater;

        public ViewAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return arrayListData.size();
        }

        @Override
        public Object getItem(int position) {
            return arrayListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.listitem,null);
            }
            final TextView nameText = (TextView) convertView.findViewById(R.id.textView5);
            nameText.setText("Nombre : "+arrayListData.get(position).getNombre());
            final TextView ageText = (TextView) convertView.findViewById(R.id.textView4);
            ageText.setText("Descripcion : "+arrayListData.get(position).getDescription());

            final Button edit = (Button) convertView.findViewById(R.id.button2);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bundle = new Bundle();
                    bundle.putString("nombre", arrayListData.get(position).getNombre());
                    Intent intent = new Intent(getApplicationContext(), Editar.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            final Button delete = (Button) convertView.findViewById(R.id.button);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    seleccionado = arrayListData.get(position).getNombre();
                    confirmar_eliminar();
                }
            });
            return convertView;
        }


    }
    @Override
    protected void onResume() {
        arrayListData = MyDB.getListData();

        //lvDatos.setAdapter(new MyBaseAdapter(this));
        lvDatos.setAdapter(new ViewAdapter(this));
        super.onResume();
}
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK)
            moveTaskToBack(true);//Deshabilita regresar al activity anterior de nuestra APP
    return super.onKeyDown(keyCode, event);
    }
    public void confirmar_eliminar(){
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Importante");
        dialogo1.setMessage("¿ Desea eliminar el producto ?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                eliminar();
            }
        });
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

            }
        });
        dialogo1.show();
    }
    public void eliminar(){
        MyDB.deleteContact(seleccionado);
        onResume();
    }





}
