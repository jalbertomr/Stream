package stream;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Bext on 13/02/2017.
 * from www.baeldung.com/java-8-streams-introduction
 */
public class StreamAPIIntro {

    public static void main(String[] args) {

        //Stream Creation
        String[] arr = new String[]{"a","b","c"};
        Stream<String> streamString = Arrays.stream(arr);
        Stream<String> streamNotInitialized;

        streamString.forEach(e -> System.out.print(e + " "));  System.out.println();

        streamNotInitialized = Stream.of("a1","b2","c3");
        streamNotInitialized.forEach(e -> System.out.print(e + " "));  System.out.println();

        List<String> listString = Arrays.asList(new String[]{"list","of","Strings"});
        Stream<String> streamFromCollection = listString.stream();
        streamFromCollection.forEach(e -> System.out.print(e + " "));  System.out.println();

        //Multi-threading with Streams
        listString.parallelStream().forEach(e -> System.out.print(e + " ")); System.out.println();

        //Stream Operations
        List<String> listElementos = Arrays.asList(new String[]{"aire","tierra","fuego","agua","agua","tierra"});
        Stream<String> streamElementos = listElementos.stream();
        streamElementos.forEach(e -> System.out.print(e + " ")); System.out.println();
        streamElementos = listElementos.stream();  //se reinicaliza por que con la operacion anterior se consumio
        System.out.println(String.valueOf(streamElementos.count()));
        streamElementos = listElementos.stream();
        System.out.println(streamElementos.distinct().collect(Collectors.toList()));
        streamElementos = listElementos.stream();
        System.out.println(String.valueOf(streamElementos.distinct().count()));
        //streamElementos.distinct().forEach(e -> System.out.print(e + " ")); System.out.println();

        //Iterating
        boolean ifexist = false;
        for(String elem: listElementos) {
            if (elem.contains("ag")) {
                ifexist = true;
                System.out.println("constains(\"ag\"): " + ifexist);
            }
        }

        boolean ifexist2 = listElementos.stream().anyMatch(e -> e.contains("ag"));
        System.out.println("constains(\"ag\"): " + ifexist2);

        //Filtering
        ArrayList<String> listFiltar = new ArrayList<>();
        listFiltar.add("One");
        listFiltar.add("OneAndOnly");
        listFiltar.add("Derek");
        listFiltar.add("do");
        listFiltar.add("Change");
        listFiltar.add("re");
        listFiltar.add("factory");
        listFiltar.add("mi");
        listFiltar.add("justBefore");
        listFiltar.add("Italy");
        listFiltar.add("Italy");
        listFiltar.add("Thursday");
        listFiltar.add("");
        listFiltar.add("");

        System.out.println(listFiltar.stream().filter(e -> e.contains("d")).collect(Collectors.toList()));
        System.out.println(listFiltar);

        //Mapping
        List<String> uris = new ArrayList<>();
        uris.add("C:\\my.txt");
        uris.add("D:\\otro.txt");
        uris.add("E:\\aunmas.txt");
        Path path = Paths.get(uris.get(0));
        System.out.println(path);
        //Stream<String> is converted to stream<Path> by applying a specific lambda expresion to every element of the initial stream
        Stream<Path> streamPath = uris.stream().map(uri -> Paths.get(uri + "\\modificado"));
        streamPath.forEach(e -> System.out.print(e + " "));   System.out.println();

        Stream<String> streamStringMapped = listFiltar.stream()
                .map(e -> e.concat(" lenght: " + e.length()));
        streamStringMapped.forEach(e -> System.out.println(e + " ")); System.out.println();


        streamStringMapped = listFiltar.stream()
                .filter(e -> e.length() < 3)
                .map( e -> e.concat(" length: " + e.length()));
        streamStringMapped.forEach(e -> System.out.println(e + " ")); System.out.println();

        //flatMap to create a stream of the elements, wich each element has his own sequence of elements
        List<Detail> listDetail = new ArrayList<>();
        listDetail.add(new Detail());

        Stream<String> streamDetail = listDetail.stream()
                .map(e -> e.getParts().get(0));
        streamDetail.forEach(e -> System.out.println(e + " ")); System.out.println();

        /*
        streamDetail = listDetail.stream()
                .map(e -> e.getParts().forEach(e -> System.out.print(e + " ")));
        streamDetail.forEach(e -> System.out.println(e + " ")); System.out.println();
        */

        /*  impossible to get stream with map
        streamDetail = listDetail.stream()
                .map(e -> e.getParts().stream());
        */
        streamDetail = listDetail.stream()
                .flatMap(e -> e.getParts().stream());

        streamDetail.forEach(e -> System.out.print(e + " ")); System.out.println();

        //Matching
        List<String> listForMatch = Arrays.asList(new String[]{
                "abeto","salia","sala","ernesta","aaa","juana"
        });

        System.out.println("listForMatch: " + listForMatch);
        System.out.println(".anyMatch(e -> e.contains(\"i\"): " + listForMatch.stream().anyMatch(e -> e.contains("i")));
        System.out.println(".anyMatch(e -> e.contains(\"a\")): " +listForMatch.stream().anyMatch(e -> e.contains("a")));
        System.out.println(".anyMatch(e -> e.contains(\"z\")): " + listForMatch.stream().anyMatch(e -> e.contains("z")));
        System.out.println(".allMatch(e -> e.contains(\"a\")): " + listForMatch.stream().allMatch(e -> e.contains("a")));
        System.out.println(".allMatch(e -> e.contains(\"e\")): " + listForMatch.stream().allMatch(e -> e.contains("e")));
        System.out.println(".noneMatch(e -> e.contains(\"a\")): " + listForMatch.stream().noneMatch(e -> e.contains("a")));
        System.out.println(".noneMatch(e -> e.contains(\"z\")): " + listForMatch.stream().noneMatch(e -> e.contains("z")));

        //Reduction  reduce( startValue, accumulator function)
        List<Integer> listInteger = Arrays.asList(new Integer[]{1,1,1});
        System.out.println(listInteger.stream().reduce(23, (a,b) -> a + b));

        //Collection
        listForMatch = listForMatch.stream()
                .map(e -> e.toUpperCase())
                .collect(Collectors.toList());
        System.out.println(listForMatch);


    }
}
