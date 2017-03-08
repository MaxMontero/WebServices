package maxmontero.com.webservices.Parsers;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import maxmontero.com.webservices.POJO.Usuario;

/**
 * Created by Dell on 8/03/2017.
 */
//esta clase parsea a usuario
public class UsuarioXMLParser {
    public static List<Usuario> parser(String content){
        //los XML se va leenedo deacuerdo a sus etiquetas
        //los XML tiene nodos tenemos que leer el XML deacuerdo a sus nodos
        boolean intDataItemTAG= false;
        String currentTAGName= "";
        Usuario usuario = null;
        List<Usuario> usuarioList = new ArrayList<>();

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            //defninimos
            XmlPullParser parser= factory.newPullParser();
            parser.setInput(new StringReader(content));

            int eventType= parser.getEventType();

            //leemos el XML
            //mientras tenamos algo que leer seguimos
            while(eventType != XmlPullParser.END_DOCUMENT){
                switch (eventType){
                    //manejamos tres tipos de eventos
                    //inicia la etiqueta
                    case  XmlPullParser.START_TAG:
                        currentTAGName = parser.getName();
                        //cada usuario que llegue se agregará a la lista
                        if(currentTAGName.equals("usuario")){
                            intDataItemTAG = true;
                            usuario=new Usuario();
                            usuarioList.add(usuario);
                        }
                        break;

                    //termina la etiqueta
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("usuario")){
                            intDataItemTAG=false;
                        }
                        currentTAGName ="";
                        break;

                    //lo que está dentro de la etiqueta
                    case XmlPullParser.TEXT:
                        if(intDataItemTAG && usuario!=null){
                            switch (currentTAGName){
                                case "usuarioId":
                                    usuario.setUsuarioId(Integer.parseInt(parser.getText()));
                                    break;
                                case "nombre":
                                    usuario.setNombre(parser.getText());
                                    break;
                                case "twitter":
                                    usuario.setTwitter(parser.getText());
                                    break;
                            }
                        }
                        break;
                }
                eventType = parser.next();
            }
            return usuarioList;


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
