package me.cancit.soapdemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class SoapActivity extends AppCompatActivity {

    /** Called when the activity is first created. */
    private static String SOAP_ACTION1 = "http://www.w3schools.com/xml/FahrenheitToCelsius";
    private static String SOAP_ACTION2 = "http://www.w3schools.com/xml/CelsiusToFahrenheit";
    private static String NAMESPACE = "http://www.w3schools.com/xml/";
    private static String METHOD_NAME1 = "FahrenheitToCelsius";
    private static String METHOD_NAME2 = "CelsiusToFahrenheit";
    private static String URL = "http://www.w3schools.com/xml/tempconvert.asmx?WSDL";

    Button btnFar,btnCel,btnClear;
    EditText txtFar,txtCel;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soap);

        btnFar = (Button)findViewById(R.id.btnFar);
        btnCel = (Button)findViewById(R.id.btnCel);
        btnClear = (Button)findViewById(R.id.btnClear);
        txtFar = (EditText)findViewById(R.id.txtFar);
        txtCel = (EditText)findViewById(R.id.txtCel);

        btnFar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Initialize soap request + add parameters
                new C2Fahrenheit().execute(txtFar.getText().toString());

            }
        });

        btnCel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                new F2Celcius().execute(txtCel.getText().toString());
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                txtCel.setText("");
                txtFar.setText("");
            }
        });
    }
    public class C2Fahrenheit extends AsyncTask<String,String,SoapObject>{

        @Override
        protected SoapObject doInBackground(String... params) {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME1);

            //Use this to add parameters
            request.addProperty("Fahrenheit",params[0]);

            //Declare the version of the SOAP request
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.setOutputSoapObject(request);
            envelope.dotNet = true;

            try {
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

                //this is the actual part that will call the webservice
                androidHttpTransport.call(SOAP_ACTION1, envelope);

                // Get the SoapResult from the envelope body.
                SoapObject result = (SoapObject)envelope.bodyIn;
                return result;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(SoapObject result) {
            super.onPostExecute(result);
            if(result != null)
            {
                //Get the first property and change the label text
                txtCel.setText(result.getProperty(0).toString());
            }
            else
            {
                Toast.makeText(getApplicationContext(), "No Response",Toast.LENGTH_LONG).show();
            }
        }
    }
    public class F2Celcius extends AsyncTask<String,String,SoapObject>{

        @Override
        protected SoapObject doInBackground(String... params) {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME2);

            //Use this to add parameters
            request.addProperty("Celsius",params[0]);

            //Declare the version of the SOAP request
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.setOutputSoapObject(request);
            envelope.dotNet = true;

            try {
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

                //this is the actual part that will call the webservice
                androidHttpTransport.call(SOAP_ACTION2, envelope);

                // Get the SoapResult from the envelope body.
                SoapObject result = (SoapObject)envelope.bodyIn;
                return result;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(SoapObject result) {
            super.onPostExecute(result);
            if(result != null)
            {
                //Get the first property and change the label text
                txtFar.setText(result.getProperty(0).toString());
            }
            else
            {
                Toast.makeText(getApplicationContext(), "No Response",Toast.LENGTH_LONG).show();
            }
        }
    }
}
