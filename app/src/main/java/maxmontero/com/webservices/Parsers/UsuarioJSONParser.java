package maxmontero.com.webservices.Parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import maxmontero.com.webservices.POJO.Usuario;

/**
 * Created by Dell on 9/03/2017.
 */
//nueva clase para parsear JASON
public class UsuarioJSONParser {

    public static List<Usuario> parse(String content){
        try {
            //lista para trabajar con json
            JSONArray jsonArray=new JSONArray(content);
            List<Usuario> usuarioList=new ArrayList<>();
            for (int i=0; i< jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Usuario usuario=new Usuario();

                usuario.setUsuarioId(jsonObject.getInt("usuarioid"));
                usuario.setNombre(jsonObject.getString("nombre"));
                usuario.setTwitter(jsonObject.getString("twitter"));

                usuarioList.add(usuario);
            }
            return usuarioList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
