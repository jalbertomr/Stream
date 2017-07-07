package stream;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.*;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * Created by Bext on 14/02/2017.
 * Que hace findAny? siempre regresa el mismo datos aunque tenga varias opciones?
 */
public class StreamAPI {

    private static long count;

    private static void wasCalled() {
        count++;
    }

    public <T> Stream<T> streamOf(List<T> list) {
        return (list == null || list.isEmpty() ? Stream.empty() : list.stream());
    }

    static public void main(String[] args){
        //Stream Creation
        //Once created the instance WILL NOT MODIFY ITS SOURCE

        // Create with Empty stream
        List<String> list = Arrays.asList(new String[]{});

        Stream<String> streamEmpty = list.stream();
        streamEmpty.forEach(e -> System.out.println(e));

        streamEmpty = Stream.empty();

        // Create from Collection
        Collection<String> collection = Arrays.asList( "a","b","c");
        Stream<String> streamFromCollection = collection.stream();

        // Create from Array
        Stream<String> streamOfArray = Stream.of("a","b","c");
        // Created from Array or part of Array
        String[] arr = new String[]{"aa","bb","cc","dd","ee","ff","gg","hh"};
        Stream<String> streamOfArrayFull = Arrays.stream(arr);
        Stream<String> streamOfArrayPart = Arrays.stream(arr, 2,4);

        // Stream.builder()
        // when method builder is used, the desired type should be additionally  specified in the right part
        // of the statement  Stream.<T>builder()...   otherwise a Stream<Object> will be created

        Stream<String> streamBuilder = Stream.<String>builder()
                .add("Stream.").add("<String>").add(".builder()")
                .add(".add(\"a\")").add(".add(\"b\")").add(".add(\"c\")").add(".build()")
                .build();
        streamBuilder.forEach(e -> System.out.print(e)); System.out.println();

        // Stream.generate()
        // generate method accepts a Supplier<T> for element generation, must be specified de limit or reaches the memory limit
        Stream<String> streamGenerate = Stream.generate(() -> "elem").limit(10);
        streamGenerate.forEach(e -> System.out.print(e + " "));  System.out.println();

        final Random random2 = new Random();
        Stream<Integer> streamGenerateInt = Stream.generate(() -> random2.nextInt()).limit(10);
        streamGenerateInt.forEach(r -> System.out.print(r + " ")); System.out.println();

        // Stream.itereate()
        Stream<Integer> streamIterate = Stream.iterate( 40, n -> n + 2).limit(20);
        streamIterate.forEach(e -> System.out.print(e + " "));  System.out.println();

        Stream<String> streamIterateString = Stream.iterate( "a", n -> n + 1).limit(10);
        streamIterateString.forEach(e -> System.out.print(e + " "));  System.out.println();

        //Stream of Primitives  Int, Long and Double
        IntStream intStream = IntStream.range(1,5);             //  1...4
        LongStream longStream = LongStream.rangeClosed(1,5);    //  1...5
        intStream.forEach(e -> System.out.print(e + " ")); System.out.println();
        longStream.forEach(e -> System.out.print(e + " ")); System.out.println();

        Random random = new Random();
        DoubleStream doubleStream = random.doubles(3);
        doubleStream.forEach(e -> System.out.print(e + " ")); System.out.println();

        doubleStream = random.doubles(5, 10, 20);
        doubleStream.forEach(e -> System.out.print(e + " ")); System.out.println();

        // Stream of String
        IntStream streamOfChars = ("abcdef").chars();
        streamOfChars.forEach(e -> System.out.print(e + " ")); System.out.println();

        streamOfChars = ("abcdef").codePoints();
        streamOfChars.forEach(e -> System.out.print(e + " ")); System.out.println();

        Stream<String> streamOfString = Pattern.compile(", ").splitAsStream("token1, token2, token3");
        streamOfString.forEach(e -> System.out.print(e + " ")); System.out.println();

        // Stream of File
        try {
            BufferedReader br;
            br = new BufferedReader(new FileReader("test.txt"));
            Stream<String> streamFile = br.lines();
            streamFile.forEach(e -> System.out.println("linea: " + e)); System.out.println();
        } catch (IOException e ){
            System.err.println(e);
            System.exit(1);
        }

        try {
            Path path = Paths.get(System.getProperty("user.dir"), "test.txt");
            System.out.println("Path: " + path);
            Stream<String> streamFile = Files.lines(path);
            streamFile.forEach(e -> System.out.println("linea: " + e)); System.out.println();
            streamFile = Files.lines(path, Charset.forName("UTF-8"));
            streamFile.forEach(e -> System.out.println("linea: " + e)); System.out.println();
        } catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }
        // Referencing a Stream
        //Stream<String> stream = Arrays.asList("a","b","c","bb").stream().filter(e -> e.contains("b"));
        //Stream<String> stream = Stream.of("a","b","c","bb").filter(e -> e.contains("b"));
        //Optional<String> anyElement = stream.findAny();
        //System.out.println(anyElement);
        /*  IllegalStateException because findAny() is terminal operation
        Optional<String> findFirst = stream.findFirst();
        */
        //Adjusting de code
        List<String> elements = Stream.of("a","bb","b","c","bc","cbc","ccc").filter(e -> e.contains("b")).collect(Collectors.toList());
        Optional<String> anyElement = elements.stream().findAny();
        Optional<String> findElement = elements.stream().findFirst();
        System.out.println("anyElement: " + anyElement);
        System.out.println("firstElement: " + findElement);

        // Stream Pipeline
        // source, intermediate operation(s), terminal operation
        Stream<String> onceModifiedStream = Stream.of(
                "unoUno","dosDos","tresTRES","cuatroCUATRO").skip(1);
        onceModifiedStream.forEach(e -> System.out.print(e + " ")); System.out.println();
        // dos tres cuatro
        Stream<String> twiceModifiedStream = Stream.of(
                "unoUno","dosDos","tresTRES","cuatroCUATRO")
                .skip(1)
                .map( e -> e.substring(1,5));
        twiceModifiedStream.forEach(e -> System.out.print(e + " ")); System.out.println();

        List<String> list2 = Arrays.asList("abc1","abc2","abc3");
        long size = list2.stream()
                .skip(1)
                .map(e -> e.substring(0,3))
                .count();
        System.out.println("list skip map count :" + size);

        //Lazy Invocation
        Optional<String> option = list2.stream()
                .filter(e -> {
                    log.println("filter() was called");
                    return e.contains("2");
                })
                .map(e -> {
                    log.println("map() was called");
                    return e.toUpperCase();
                }).findFirst();
        System.out.println(option);

        list2.stream()
                .filter(e -> {
                    log.println("filter() was called");
                    return e.contains("2");
                })
                .map(e -> {
                    log.println("map() was called");
                    return e.toUpperCase();
                }).forEach(e -> System.out.println(e + " ")); System.out.println();

        // Order of excecution
        // skip(), filter(), distinct() must be first in the pupeline
        list2.stream().forEach(e -> System.out.print(e + " ")); System.out.println();
        count = 0;
        long sizeResp = list2.stream()
                .map(e -> {
                    wasCalled();
                    return e.substring(0,3);
                })
                .skip(2)
                .count();
        System.out.println("sizeResp: " + sizeResp + " count: " + count);

        count = 0;
        sizeResp = list2.stream()
                .skip(2)
                .map(e -> {
                    wasCalled();
                    return e.substring(0,3);
                })
                .count();
        System.out.println("sizeResp: " + sizeResp + " count: " + count);

        // Stream Reduction
        // the reduce() method
        // Identity - the initial value for an accumulator, or a default return value if a stream is empty
        //            and there is nothing to accumulate.
        // Acumulator - a function wich specifies a logic of aggregation of elements. As accumulator creates
        //             new value for every step of reducing, the quantity of new values equals to the stream's
        //              size and only the last value is usefull. this is not very good for the performance.
        // Combiner   - a function wich aggregates results of the accumulator. Combiner is called only in a
        //              parallel mode to reduce results of accumulators from different threads

        OptionalInt reduced = IntStream.range(1, 4).reduce((a, b) -> a + b );
        System.out.println("reduced: " + reduced);
        //reduced = 6 = (1 + 2 + 3)

        int reducedTwoParams = IntStream.range(1, 4).reduce( 10, (a, b) -> a + b);
        System.out.println("reducedTwoParams: " + reducedTwoParams);
        //reducedTwoParams = 16 = 10 + ( 1 + 2 + 3)

        int reduceParams = Stream.of(1, 2, 3)
                .reduce( 10 , (a, b) -> a + b, (a, b) -> {
                    log.println("combiner was called");
                    return a + b ;
                });
        System.out.println("reduceParams: " + reduceParams);
        //reduce params =  16 = 10 + ( 1 + 2 + 3)  combiner was not called

        int reducedParallel = Arrays.asList(1, 2, 3).parallelStream()
                .reduce( 10, (a, b) -> a + b, (a, b) -> {
                    log.println("combiner was caller");
                    return a + b;
                });
        System.out.println("reducedParallel: " + reducedParallel);
        //(10 + 1 = 11; 10 + 2 = 12; 10 + 3 = 13;).
        // the combiner merge (12 + 13 = 25; 25 + 11 = 36).

        // Collect method... continues in StreamAPI2 class

     }
}
