package com.devbaltasarq.configuradorcoche;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

/** Representa un configurador de coches. */
public class MainActivity extends AppCompatActivity {
    private static final String LogTag = MainActivity.class.getSimpleName();

    /** Modelos de coches disponibles. */
    public final static String[] Modelos = {
            "Basic 1.1 T",
            "Elegance 1.1 T",
            "Ambiance 1.1 T",
            "Basic 1.4 TDI",
            "Elegance 1.4 TDI",
            "Ambiance 1.4 TDI",
            "Sport 2.0 TSI"
    };

    /** Los posibles extras a incluir en el coche. */
    public final static String[] Extras = {
            "Bluetooth",
            "Wifi",
            "Techo solar",
            "A/C"
    };

    /** Los extras incluidos. Este vector y el anterior
      * deben tener la misma longitud.
      */
    private boolean[] extrasActivados = {
            false, false, false, false
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btLegal = this.findViewById( R.id.btLegal );
        final RadioButton rbDiesel = this.findViewById( R.id.rbDiesel );
        final RadioButton rbGasolina = this.findViewById( R.id.rbGasolina );
        final Button btModelo = this.findViewById( R.id.btModelo );
        final TextView lblModelo = this.findViewById( R.id.lblModelo );
        final Button btExtras = this.findViewById( R.id.btExtras );
        final Button btPersonaliza = this.findViewById( R.id.btPersonaliza );
        final EditText edExtras = this.findViewById( R.id.edExtras );
        final TextView lblPersonaliza = this.findViewById( R.id.lblPersonaliza );

        // Chk
        if ( Extras.length != extrasActivados.length ) {
            Log.e( LogTag, "longitud de extras y extras activados diferente" );
            this.finish();
        }

        // Preparar valores iniciales
        lblModelo.setText( Modelos[ 0 ] );
        this.muestraExtras();
        this.muestraModeloFinal();

        // Gestores de eventos
        btLegal.setOnClickListener( (v) -> this.muestraDialogoLegal() );
        rbDiesel.setOnClickListener( (v) -> this.manejaBotonesRadio( v ) );
        rbGasolina.setOnClickListener( (v) -> this.manejaBotonesRadio( v ) );
        btModelo.setOnClickListener( (v) -> this.seleccionaModelo() );
        btExtras.setOnClickListener( (v) -> this.seleccionaExtras() );
        btPersonaliza.setOnClickListener( (v) -> this.personaliza() );

        // Modelo final siempre actualizado
        final TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                MainActivity.this.muestraModeloFinal();
            }
        };

        lblModelo.addTextChangedListener( textWatcher );
        rbDiesel.setOnCheckedChangeListener( (b, v) -> this.muestraModeloFinal() );
        rbGasolina.setOnCheckedChangeListener( (b, v) -> this.muestraModeloFinal() );
        edExtras.addTextChangedListener( textWatcher );
        lblPersonaliza.addTextChangedListener( textWatcher );
    }

    /** Muestra el modelo de coche con todas las opciones seleccionadas. */
    private void muestraModeloFinal()
    {
        final TextView lblFinal = this.findViewById( R.id.lblFinal );
        final TextView lblPersonaliza = this.findViewById( R.id.lblPersonaliza );
        final TextView lblModelo = this.findViewById( R.id.lblModelo );
        final RadioButton rbDiesel = this.findViewById( R.id.rbDiesel );
        final RadioButton rbGasolina = this.findViewById( R.id.rbGasolina );
        final EditText edExtras = this.findViewById( R.id.edExtras );
        final StringBuilder msg = new StringBuilder();

        msg.append( lblModelo.getText().toString() );
        msg.append( ' ' );

        if ( rbDiesel.isChecked() ) {
            msg.append( rbDiesel.getText().toString() );
            msg.append( ' ' );
        }

        if ( rbGasolina.isChecked() ) {
            msg.append( rbGasolina.getText().toString() );
            msg.append( ' ' );
        }

        msg.append( edExtras.getText().toString() );
        msg.append( ' ' );
        msg.append( lblPersonaliza.getText().toString() );
        lblFinal.setText( msg.toString() );
    }

    /** Pregunta al usuario el mensaje para personalizar el coche. */
    private void personaliza()
    {
        final TextView lblPersonaliza = this.findViewById( R.id.lblPersonaliza );
        final AlertDialog.Builder dlg = new AlertDialog.Builder( this );
        final EditText edTexto = new EditText( this );

        dlg.setTitle( "Personaliza" );
        dlg.setView( edTexto );
        dlg.setPositiveButton( "Ok", (d, i) -> {
            lblPersonaliza.setText( edTexto.getText().toString() );
        });

        dlg.create().show();
    }

    /** Pregunta al usuario los extras que quiere. */
    private void seleccionaExtras()
    {
        final AlertDialog.Builder dlg = new AlertDialog.Builder( this );

        dlg.setTitle( "Extras" );
        dlg.setMultiChoiceItems(Extras,
                this.extrasActivados, (d, op, isChecked) -> {
                    this.extrasActivados[ op ] = isChecked;
                    this.muestraExtras();
                });

        dlg.create().show();
    }

    /** Muestra los extras seleccionados por el usuario. */
    private void muestraExtras()
    {
        final StringBuilder msgExtras = new StringBuilder();
        final EditText edExtras = this.findViewById( R.id.edExtras );

        for(int i = 0; i < this.extrasActivados.length; ++i) {
            if ( this.extrasActivados[ i ] ) {
                msgExtras.append( Extras[ i ] );
                msgExtras.append( ' ' );
            }
        }

        String msgFinalExtras = msgExtras.toString().trim();

        if ( msgFinalExtras.isEmpty() ) {
            msgFinalExtras = "Ninguno";
        }

        edExtras.setText( msgFinalExtras );
    }

    /** Selecciona uno de los modelos de coches disponibles. */
    private void seleccionaModelo()
    {
        final AlertDialog.Builder dlg = new AlertDialog.Builder( this );
        final TextView lblModelo = this.findViewById( R.id.lblModelo );

        dlg.setTitle( R.string.app_name );
        dlg.setItems( Modelos, (d, i) -> lblModelo.setText( Modelos[ i ] ) );

        dlg.create().show();
    }

    /** Gestiona los motores diesel o gasolina. */
    private void manejaBotonesRadio(View v)
    {
        final RadioButton rbDiesel = this.findViewById( R.id.rbDiesel );
        final RadioButton rbGasolina = this.findViewById( R.id.rbGasolina );
        final RadioButton radioButton = (RadioButton) v;
        final boolean chk = radioButton.isChecked();

        if ( radioButton == rbGasolina ) {
            rbDiesel.setChecked( !chk );
        } else {
            rbGasolina.setChecked( !chk );
            Toast.makeText( this,
                            "Terrorista medioambiental",
                            Toast.LENGTH_LONG ).show();
        }

        return;
    }

    /** Muestra un "texto legal" poco serio. */
    private void muestraDialogoLegal()
    {
        final AlertDialog.Builder dlg = new AlertDialog.Builder( this );

        dlg.setTitle( "Configurador" );
        dlg.setMessage( "No te tomes demasiado en serio esta aplicación." );
        dlg.setPositiveButton( "Aceptar", null );
        dlg.setNegativeButton("Rechazar", (d, i) -> MainActivity.this.finish() );
        dlg.setCancelable( false );

        dlg.create().show();
    }
}
