package ficha.dam.org.fichafutbol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;


public class Main extends Activity {

    EditText _etNombre;
    EditText _etApellido1;
    EditText _etApellido2;
    EditText _etDNI;
    EditText _etEdad;
    RadioButton _rbHombre;
    RadioButton _rbMujer;
    RadioGroup _rgSexo;
    Spinner _spiCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _etNombre = (EditText) findViewById(R.id.etNombre);
        _etApellido1 = (EditText) findViewById(R.id.etApellido1);
        _etApellido2 = (EditText) findViewById(R.id.etApellido2);
        _etDNI = (EditText) findViewById(R.id.etDNI);
        _etEdad = (EditText) findViewById(R.id.etEdad);
        _rbHombre = (RadioButton) findViewById(R.id.rbHombre);
        _rbMujer = (RadioButton) findViewById(R.id.rbMujer);
        _rgSexo = (RadioGroup) findViewById(R.id.rgSexo);
        _spiCategoria = (Spinner) findViewById(R.id.spiCategoria);

        cargarCategorias();

        Button bEnviar = (Button) findViewById(R.id.bEnviar);
        bEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(compruebaCampos() && validarDNI()) {
                    enviarDatos();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(requestCode == 1234 && resultCode == RESULT_OK){
            resetearDatos();
            Toast.makeText(this,"Los datos se han guardado correctamente", Toast.LENGTH_LONG).show();
        }
    }

    //Pone los datos por defecto
    private void resetearDatos(){
        _etNombre.setText("");
        _etApellido1.setText("");
        _etApellido2.setText("");
        _etDNI.setText("");
        _etEdad.setText("");
        _rbHombre.setChecked(false);
        _rbMujer.setChecked(false);
        cargarCategorias();
    }

    /**
     * Carga las categorias correspondientes en el spinner
     */
    private void cargarCategorias(){
        Spinner spinner_categorias = (Spinner) findViewById(R.id.spiCategoria);

        /**
         * Creamos un adaptador de array a partir del fichero arrays.xml donde se encuentra la array
         de las categorias
         */
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categorias,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Asociamos a nuestro spinner de categorias el adaptador donde se encuentra la array
        spinner_categorias.setAdapter(adapter);
    }

    /**
     * Comprueba los campos vacios e incorrectos para mostrar un mensaje con los que falten
     */
    private boolean compruebaCampos(){
        String strMensaje = "Los siguientes campos están incorrectos: ";
        boolean bIncorrecto = false;
        if (_etNombre.getText().toString().equals("") || _etNombre.getText().toString().matches(".*[0-9].*")){
            strMensaje += "\n Nombre";
            bIncorrecto = true;
        }
        if (_etApellido1.getText().toString().equals("") || _etApellido1.getText().toString().matches(".*[0-9].*")){
            strMensaje += "\n Apellido";
            bIncorrecto = true;
        }
        if (_etApellido2.getText().toString().equals("") || _etApellido2.getText().toString().matches(".*[0-9].*")){
            strMensaje += "\n Apellido2";
            bIncorrecto = true;
        }
        if (_etDNI.getText().toString().equals("") ){
            strMensaje += "\n DNI";
            bIncorrecto = true;
        }
        if (_etEdad.getText().toString().equals("") || !_etEdad.getText().toString().matches(".*[0-9].*")){
            strMensaje += "\n Edad";
            bIncorrecto = true;
        }
        if (!_rbHombre.isChecked() && !_rbMujer.isChecked()){
            strMensaje += "\n Sexo";
            bIncorrecto = true;
        }
        if (bIncorrecto){
            Toast.makeText(this, strMensaje, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    /**
     * Comprueba la longitud del DNI y que sean números
     * @return
     */
    private boolean validarDNI(){
        if(_etDNI.getText().toString().length() > 8 || _etDNI.getText().toString().length() < 8){
            Toast.makeText(this,"El DNI tiene que tener 8 números",Toast.LENGTH_LONG).show();
            return false;
        }
        String strDNI = _etDNI.getText().toString();
        if(strDNI.matches("[0-9]*")){
            return true;
        }
        return false;
    }

    /**
     * Calcula la letra del DNI a partir de su número     *
     * @return Letra del DNI
     */
    private char calculaLetra()
    {
        int nDNI = Integer.parseInt(_etDNI.getText().toString());
        String juegoCaracteres="TRWAGMYFPDXBNJZSQVHLCKET";
        int modulo= nDNI % 23;
        char letra = juegoCaracteres.charAt(modulo);
        return letra;
    }

    public void enviarDatos(){
        Intent intent = new Intent(this, Datos.class);
        intent.putExtra("nombre", _etNombre.getText().toString());
        intent.putExtra("apellido1", _etApellido1.getText().toString());
        intent.putExtra("apellido2", _etApellido2.getText().toString());
        //Pasa el campo DNI añadiendole la letra
        intent.putExtra("dni", _etDNI.getText().toString() + calculaLetra());
        intent.putExtra("edad", _etEdad.getText().toString());
        if (_rbHombre.isChecked()){
            intent.putExtra("sexo", "Hombre");
        }
        else{
            intent.putExtra("sexo", "Mujer");
        }
        intent.putExtra("categoria", _spiCategoria.getSelectedItem().toString());
        startActivityForResult(intent, 1234);
    }
}
