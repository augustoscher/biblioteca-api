package br.com.objetive.biblioteca.utils;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamUtils {

    public static <T> Stream<T> asStream(Iterable<T> sourceIterator) {
        return asStream(sourceIterator, false);
    }

    public static <T> Stream<T> asStream(Iterable<T> sourceIterator, boolean parallel) {
        return StreamSupport.stream(sourceIterator.spliterator(), parallel);
    }
}
