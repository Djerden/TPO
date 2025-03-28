package com.djeno.task3.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum Race {
    HUMAN("Человек"),
    ASARI("Азари"),
    KROGAN("Кроган"),
    TURIAN("Турианец"),
    SALARIAN("Саларианец"),
    QUARIAN("Кварианец"),
    GETH("Гет"),
    PROTHEAN("Протеанин"),
    VULCAN("Вулканец"),
    KLINGON("Клингон"),
    TWILEK("Тви'лек"),
    WOOKIE("Вуки"),
    YODA_RACE("Йодалинец"),
    CYBERMAN("Киберчеловек"),
    DALEK("Далек"),
    ZERG("Зерг"),
    PROTOSS("Протосс"),
    BORG("Борг");

    private final String name;
}
