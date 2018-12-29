package com.cy.demo;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author chenying
 * @Date 2018/7/19
 */
public class DemoTest {
    public static void main(String[] args) {

        List<String> collect1 = Stream.of("one", "two", "three", "four")
                .filter(e -> e.length() > 3)
                .peek(e -> System.out.println("Filtered value: " + e))
                .map(String::toUpperCase)
                .peek(e -> System.out.println("Mapped value: " + e))
                .collect(Collectors.toList());
        System.out.println(collect1);

        List<String> collect = Stream.iterate(LocalDate.now().plusDays(3), localDate -> localDate.plusDays(1))
                .limit(3).flatMap((item) -> {
                    return Stream.of(item.toString());
                }).collect(Collectors.toList());

        System.out.println(collect);
    }

   
}
