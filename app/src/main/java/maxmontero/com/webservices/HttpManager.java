package maxmontero.com.webservices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Dell on 8/03/2017.
 */
//esta clase sirve para la peticion a nuestro XML
public class HttpManager {
    public static String getData(String uri){
        //clase que se utiliza para leer archivos
        BufferedReader bufferedReader = null;
        try {
            //capturamos la url
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            StringBuilder stringBuilder=new StringBuilder();
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String linea;
            //si el contenido de la linea es diferente a null
            while((linea = bufferedReader.readLine())!= null){
                stringBuilder.append(linea+"\n");
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (bufferedReader != null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

    }
}
