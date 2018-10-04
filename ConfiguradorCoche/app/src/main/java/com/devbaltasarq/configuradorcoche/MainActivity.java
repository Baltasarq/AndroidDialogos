package com.devbaltasarq.configuradorcoche;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public final static String[] Modelos = {
            "Basic 1.1 T",
            "Elegance 1.4 TDI",
            "Sport 2.0 TSI"
    };

    public final static String[] Extras = {
            "Bluetooth",
            "Wifi",
            "Techo solar",
            "A/C"
    };

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

        lblModelo.setText( Modelos[ 0 ] );

        btLegal.setOnClickListener( (v) -> this.muestraDialogoLegal() );
        rbDiesel.setOnClickListener( (v) -> this.manejaBotonesRadio( v ) );
        rbGasolina.setOnClickListener( (v) -> this.manejaBotonesRadio( v ) );
        btModelo.setOnClickListener( (v) -> this.seleccionaModelo() );
        btExtras.setOnClickListener( (v) -> this.seleccionaExtras() );
        btPersonaliza.setOnClickListener( (v) -> this.personaliza() );

        this.muestraExtras();
    }

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

    private void seleccionaModelo()
    {
        final AlertDialog.Builder dlg = new AlertDialog.Builder( this );
        final TextView lblModelo = this.findViewById( R.id.lblModelo );

        dlg.setTitle( R.string.app_name );
        dlg.setItems( Modelos, (d, i) -> lblModelo.setText( Modelos[ i ] ) );

        dlg.create().show();
    }

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
