package stream;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Bext on 15/02/2017.
 */
public class NestedGroupBy {
/*
    class Pojo {
        List<Item> items;
    }

    class Item {
        Estado key1;
        List<SubItem> subItems;
    }

    class SubItem {
        Ciudad key2;
    }
*/
    public static void main(String[] args) {

        // Carga los datos
        Map<Estado,Ciudad> mapEstadoCiudad = new HashMap<>();
        try{
            List<Estado> estados = new ArrayList<>();
            List<Ciudad> ciudades = new ArrayList<>();

           BufferedReader in = new BufferedReader(new FileReader("CiudadEstado.txt"));
            String word1Buffer = "";
            String word2Buffer = "";
           String line;
           while ( (line = in.readLine()) != null) {
               int tabIn = line.indexOf('\t');
               String word1 = line.substring(0,tabIn).trim();
               String word2 = line.substring(tabIn,line.length()).trim();
               //System.out.println("ciudad: " +  word1 + " estado: " + word2);

               /*
               if (word2.equals(word2Buffer)){ //no ha cambiado estado
                   ciudades.add(new Ciudad(word1));
               } else {
                   estados.add(new Estado(word2));
               }
                */

               mapEstadoCiudad.put(new Estado(word2), new Ciudad(word1));

               word1Buffer = new String(word1);
               word2Buffer = new String(word2);
           }
        }catch(IOException e){
            System.err.println(e);
            System.exit(1);
        }
        System.out.println(mapEstadoCiudad);
    }
}
