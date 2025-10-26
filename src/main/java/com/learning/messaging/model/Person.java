package com.learning.messaging.model;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Person {
    String name;
    String surname;
    int age;
}
