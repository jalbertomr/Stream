package stream;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by Bext on 14/02/2017.
 */
public class StreamAPI2 {
    public static void main(String[] args) {
        // Collect Method

        // create data
        List<Product> productList = Arrays.asList( new Product( 23, "potatoes"),
                new Product( 14, "orages"),
                new Product( 14, "orages"),
                new Product( 13, "lemons"),
                new Product(23,"bread"),
                new Product( 13, "sugar"),
                new Product( 21, "pear")
                );

        // convertind a stream to the Collections (Collection, List or Set)
        List<String> collectorCollections = productList.stream().map(Product::getName).collect(Collectors.toList());
        System.out.println("collectorCollections: " + collectorCollections);

        String listToString = productList.stream().map(Product::getName).toString();
        System.out.println("listToString: " + listToString);
        // prints  listToString: java.util.stream.ReferencePipeline$3@378bf509

        listToString = productList.stream()
                .map(Product::getName)
                .collect(Collectors.joining(", ", "[","]"));
        System.out.println(".stream().map(Product::getName).collect(Collectors.joining(\", \", \"[\",\"]\"));" + listToString);

        //Processing the average value of all the elements in the stream
        double average = productList.stream()
                .collect(Collectors.averagingInt(Product::getPrice));
        System.out.println("average: " + average);

        //Processing the sum of all elements in the stream
        int summingPrice = productList.stream()
                .collect(Collectors.summingInt(Product::getPrice));
        System.out.println("summingPrice: " + summingPrice);

        //Collectinc statistical  information about stream's element
        IntSummaryStatistics statistics = productList.stream()
                .collect(Collectors.summarizingInt(Product::getPrice));
        System.out.println("IntSummaryStatistics: " + statistics);
        //IntSummaryStatistics: IntSummaryStatistics{count=5, sum=86, min=13, average=17.200000, max=23}

        Map<Integer, List<Product>> collectorMapOfList = productList.stream()
                .collect(Collectors.groupingBy(Product::getPrice));
        System.out.println(collectorMapOfList);

        // Dividing stream's elements into groups according to some predicate
        Map<Boolean, List<Product>> mapPartitioned = productList.stream()
                .collect(Collectors.partitioningBy( e -> e.getPrice() > 15));
        System.out.println(mapPartitioned);

        // Pushing the collector to perform additional transformation
        Set<Product> unmodifiableSet = productList.stream()
                .collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
        System.out.println(unmodifiableSet);

        // Custom Collector
        Collector<Product, ?, LinkedList<Product>> toLinkedList =
                Collector.of(LinkedList::new, LinkedList::add,
                        (first, second) -> {
                            first.addAll(second);
                            return first;
                        });
        LinkedList<Product> linkedlistProduct = productList.stream()
                .collect( toLinkedList);

        // Parallel Streams

        Stream<Product> streamofCollection = productList.parallelStream();
        boolean isParallel = streamofCollection.isParallel();
        boolean bigPrice = streamofCollection
                .map(product -> product.getPrice() * 12)
                .anyMatch(price -> price > 200);
        System.out.println("bigPrice: " + bigPrice);

        streamofCollection = productList.stream();
        streamofCollection.map(p -> p.getPrice() * 12)
                .filter(price -> price > 200)
                .collect(Collectors.toList())
                .forEach(p -> System.out.println(p));

        IntStream intStreamParallel = IntStream.range(1, 150).parallel();
        isParallel = intStreamParallel.isParallel();
        System.out.println("isParallel intStreamParallel: " + isParallel);
        intStreamParallel.forEach(i -> System.out.print(i + " ")); System.out.println();

        IntStream intStreamSequential = intStreamParallel.sequential();
        isParallel = intStreamSequential.isParallel();
        System.out.println("isParallel intStreamSequential: " + isParallel);

        //The streams all mist be closed or finished Terminal operation

    }
}
