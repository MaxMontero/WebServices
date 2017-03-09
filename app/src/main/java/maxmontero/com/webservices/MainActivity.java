package maxmontero.com.webservices;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import maxmontero.com.webservices.POJO.Usuario;
import maxmontero.com.webservices.Parsers.UsuarioJSONParser;
import maxmontero.com.webservices.Parsers.UsuarioXMLParser;

//en esta rama trabajaremos con JASON nombre de branch Jason
public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    TextView textview;
    Button button;
    //creamos un lista de tareas
    List<MyAsintask> taskList;
    //creamos la lista de objetos usuario
    List<Usuario> usuarioList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textview = (TextView) findViewById(R.id.textview_main);
        button = (Button) findViewById(R.id.button_main);
        progressBar = (ProgressBar) findViewById(R.id.progressbar_main);
        //desactivamos el progress Bar
        progressBar.setVisibility(View.INVISIBLE);
        //la siguiente linea habilita el scroll view
        textview.setMovementMethod(new ScrollingMovementMethod());
        taskList = new ArrayList<>();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //pregunta si esta en linea
                if (isOnline()){
                    //trabajar con XML
                    //pedirDatos("http://maloschistes.com/maloschistes.com/jose/usuarios.xml");
                    pedirDatos("http://maloschistes.com/maloschistes.com/jose/webservice.php");
                }else{
                    Toast.makeText(getApplicationContext(),"Sin conexion",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void cargarDatos(String datos){

        if (usuarioList != null){
            for (Usuario usuario: usuarioList){
                textview.append(usuario.getNombre()+"\n");
            }
        }
    }

    //este metodo sirve para verificar la conexion a internet
    public boolean isOnline(){
        ConnectivityManager connectivityManager= (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        //esta conectado o esta siendo conectado
        if(networkInfo != null && networkInfo.isConnectedOrConnecting())
            return  true;
        else
            return false;

    }

    public void pedirDatos(String uri){
        //creamos la tarea en sgundo plano
        MyAsintask tarea=new MyAsintask();
        //la siguiente Linea ejecuta los hilos de manera serial
        tarea.execute(uri);
        //la siguiente linea la ejecuta de manera paralela
        //tarea.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


    //creamos el asyntask
    //se necesitan tres atributos
    //params: es el tipo de parametros que estas enviando
    // progress: tipo de dato en el progreso
    //result: el tipo de dato n el resultado
    private class MyAsintask extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            cargarDatos("Inicio de carga");

            if(taskList.size() == 0) {
                progressBar.setVisibility(View.VISIBLE);
            }
            //agregamos el hilo a mi lista de tareas
            taskList.add(this);

        }
        //lo que hace en el background osea en el segundo plano
        //no podemos utilizar la interfaz aqui
        @Override
        protected String doInBackground(String... params) {
            /* EJEMPLO
            for(int i=0;i<=10;i++){
                //esta linea da error por que afecta la jerarquia
                //cargarDatos("Número:" + i);
                //la siguiente funcion se encarga de actualizar
                //se va a onProgressUpdate
                publishProgress("Número: "+i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            */

            String content = HttpManager.getData(params[0]);
            return content;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            cargarDatos(values[0]);
        }

        //despues de la ejecucion
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            usuarioList = UsuarioJSONParser.parse(result);

            cargarDatos(result);
            taskList.remove(this);
            if (taskList.size()==0){
                progressBar.setVisibility(View.INVISIBLE);
            }


        }

    }
}