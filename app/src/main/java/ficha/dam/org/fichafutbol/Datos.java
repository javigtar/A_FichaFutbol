package ficha.dam.org.fichafutbol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Datos extends Activity {


    TextView _tvNombre;
    TextView _tvApellido1;
    TextView _tvApellido2;
    TextView _tvDNI;
    TextView _tvEdad;
    TextView _tvSexo;
    TextView _tvCategoria;
    Button _bSi;
    Button _bNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);

        _tvNombre = (TextView) findViewById(R.id.tvNombre);
        _tvApellido1 = (TextView) findViewById(R.id.tvApellido1);
        _tvApellido2 = (TextView) findViewById(R.id.tvApellido2);
        _tvDNI = (TextView) findViewById(R.id.tvDNI);
        _tvEdad = (TextView) findViewById(R.id.tvEdad);
        _tvSexo = (TextView) findViewById(R.id.tvSexo);
        _tvCategoria = (TextView) findViewById(R.id.tvCategoria);
        _bSi = (Button) findViewById(R.id.bSi);
        _bNo = (Button) findViewById(R.id.bNo);

        mostrarDatos();

        _bSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                devolverDatos();
            }
        });

        _bNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.datos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Muestra los datos recogidos la la actividad principal
    private void mostrarDatos(){
        Bundle extras = getIntent().getExtras();
        _tvNombre.setText(extras.getString("nombre"));
        _tvApellido1.setText(extras.getString("apellido1"));
        _tvApellido2.setText(extras.getString("apellido2"));
        _tvDNI.setText(extras.getString("dni"));
        _tvEdad.setText(extras.getString("edad"));
        _tvSexo.setText(extras.getString("sexo"));
        _tvCategoria.setText(extras.getString("categoria"));
    }

    //Devuelve los datos a la actividad principal y finaliza la actividad
    private void devolverDatos(){
        Intent intent = new Intent();
        intent.putExtra("nombre", _tvNombre.getText().toString());
        intent.putExtra("apellido1", _tvApellido1.getText().toString());
        intent.putExtra("apellido2", _tvApellido2.getText().toString());
        intent.putExtra("dni", _tvDNI.getText().toString());
        intent.putExtra("edad", _tvEdad.getText().toString());
        intent.putExtra("sexo", _tvSexo.getText().toString());
        intent.putExtra("categoria", _tvCategoria.getText().toString());
        setResult(RESULT_OK,intent);
        finish();
    }

}
