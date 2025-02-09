package org.example;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    public static String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "Hola " + name;
    }

    @GetMapping("/pi")
    public static String pi(String value) {
        return Double.toString(Math.PI);
    }

    @GetMapping("/pipe")
    public static String pipe(String name) { return  name;}
}
