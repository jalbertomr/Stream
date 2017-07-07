package stream;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.System.out;

/**
 * Created by Bext on 22/02/2017.
 */
public class ItemSubItemFill {
    public static void main(String[] args) {
        //crea Items y subItems
        //crea subItems
        List<Item> listItems = new ArrayList<>();
        List<SubItem> listSubItems = new ArrayList<>();
        List<String> referencias = new ArrayList<>();

        referencias.add("Menonitas");
        referencias.add("transformacion desierto a Zona Argicola");
        listSubItems.add(new SubItem(new Ciudad("cd. Cuauhtemoc"), referencias));
        referencias = new ArrayList<>();
        referencias.add("Queso");
        referencias.add("Perrito");
        listSubItems.add(new SubItem(new Ciudad("Chihuahua"), referencias));
        referencias = new ArrayList<>();
        referencias.add("JuanGa");
        referencias.add("El Cartel de Juarez");
        listSubItems.add(new SubItem(new Ciudad("cd. Juarez"), referencias));
        listItems.add(new Item(new Estado("Chihuahua"), listSubItems));

        //listSubItems = null;
        listSubItems = new ArrayList<>();
        referencias = new ArrayList<>();
        referencias.add("Entrada de Fayuca");
        referencias.add("Alla se fue Roberto Col");
        listSubItems.add(new SubItem(new Ciudad("Tijuana"), referencias));
        referencias = new ArrayList<>();
        referencias.add("Quien sabe?");
        listSubItems.add(new SubItem(new Ciudad("Ensenada"), referencias));
        referencias = new ArrayList<>();
        referencias.add("Los Vinos");
        listSubItems.add(new SubItem(new Ciudad("Mexicali"), referencias));
        listItems.add(new Item(new Estado("Baja California"), listSubItems));

        //listSubItems = null;
        listSubItems = new ArrayList<>();
        referencias = new ArrayList<>(); referencias.add("la puntita");
        listSubItems.add(new SubItem(new Ciudad("La Paz"), referencias));
        referencias = new ArrayList<>(); referencias.add("De Vacaciones vamonos");
        listSubItems.add(new SubItem(new Ciudad("Los Cabos"), referencias));
        listItems.add(new Item(new Estado("Baja California Sur"), listSubItems));

        //Asi fue como se llena la estructura item - subitem

        //Despliegue sin lambda
        listItems.stream().forEach( new Consumer() {
            public void accept(Object o) {
                Item item = (Item)o;
                out.println("Estado: " + item.getKey1().getName());
                item.getSubItems().stream().forEach(new Consumer<SubItem>() {
                    public void accept(SubItem subItem) {
                      out.format("Ciudad: %s%n", subItem.getKey2().getName());
                      System.out.println(subItem.getReferencias());
                      if (subItem.getReferencias() != null)
                          subItem.getReferencias().stream()
                              .filter(s -> s == null)
                              .forEach(s -> System.out.print( s + " "));

                    }
                });
                out.println();
            }
        });

        out.println("------ Con Lambdas --------");
        //Con Lambdas
        listItems.stream()
                .forEach(i -> {
                            out.println( i.getKey1().getName());
                            i.getSubItems().stream()
                                .forEach(si -> {
                                    out.print(" " + si.getKey2().getName());
                                    System.out.println(si.getReferencias());
                                 }
                                );
                            out.println();
                        }
                );

        /*
        listItems.stream()
                .forEach(i -> System.out.println(i.getKey1()));  //despliega el Estado

        //Estado{Name='Chihuahua'}
        //Estado{Name='Baja California'}
        //Estado{Name='Baja California Sur'}
        */

        /*
        listItems.stream()
                .flatMap(item -> item.getSubItems().stream())    //carga el stream con ciudades, ya no se tiene
                                                                    // acceso a Estado, ahora sera Ciudad
                .forEach((i -> System.out.println(i.getKey2())));   // Despliega Ciudad
        //Ciudad{Name='cd. Cuauhtemoc'}
        //Ciudad{Name='Chihuahua'}
        //Ciudad{Name='cd. Juarez'}
        //Ciudad{Name='Tijuana'}
        //Ciudad{Name='Ensenada'}
        //Ciudad{Name='Mexicali'}
        //Ciudad{Name='La Paz'}
        //Ciudad{Name='Los Cabos'}
        */

        // crea Map de Ciudad List<String>
        Map<Ciudad, List<String>> mapCiudadList =
        listItems.stream()
                .flatMap(item -> item.getSubItems().stream())
                .collect(Collectors.toMap( SubItem::getKey2 ,SubItem::getReferencias));

        System.out.println(mapCiudadList);

        // crea Map de Ciudad List<String>
        Map<Ciudad, List<String>> mapCiudadList2 =
                listItems.stream()
                        .flatMap(item -> item.getSubItems().stream())
                        .collect(HashMap::new ,
                                (m ,c ) -> m.put(c.getKey2(), c.getReferencias()),
                                (m,u) -> {});

        System.out.println(mapCiudadList2);
/*
        // crea Map de Ciudad List<String>
        Map<Ciudad, List<String>> mapCiudadList3 =
                listItems.stream()
                        .flatMap(item -> item.getSubItems().stream())
                        .collect(Collectors.mapping( XXX, XXX
                                )));

        System.out.println(mapCiudadList3);
*/
        /*
        Map<SubItem, List<String>> mapSubItemList2 =
                listItems.stream()
                        .flatMap(item -> item.getSubItems().stream()
                        .collect(Collectors.mapping(Map.Entry::getValue,
                                Collectors.groupingBy(SubItem::getKey2));
*/

/*
        Map<SubItem, List<String>> mapSubItemList =
                listItems.stream()
                .flatMap(item -> item.getSubItems().stream()
                //.collect(Collectors.groupingBy( SubItem::getThis, SubItem::getReferencias));
                .collect(Collectors.toMap(XXxxXXSubItem, SubItem::getReferencias)));
*/
 //       Map<SubItem, List<String>>


        //Map<SubItem, List<String>> mapSubItemlist = listItems.stream()
        //        .flatMap(item -> item.getSubItems().stream().forEach(System.out.println(item));
                      /*  .forEach(sub -> {System.out.println(sub.getKey2().getName());
                                        System.out.println(sub.getReferencias());
                                }
                        )); */
                    //.map(sub -> new AbstractMap.SimpleImmutableEntry(sub, new ArrayList<String>())))
                //.collect(Collectors.groupingBy(SubItem::getKey2, SubItem::getReferencias)));

     /*   Map<Item,Map<SubItem,List<String> mapItemSubItemReferencias =  listItems.stream()
                .flatMap(item -> item.getSubItems().stream()
                    .map(sub -> new AbstractMap.SimpleImmutableEntry<>(item, sub)))
                .collect(Collectors.groupingBy(e -> e.getKey1())
*/
    }
}
