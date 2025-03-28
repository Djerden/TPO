package com.djeno.task3;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Galaxy {
    private String name;
    private String description;
    private List<Creatures> creatures;

    public Galaxy(String name, String description) {
        this.name = name;
        this.description = description;
        this.creatures = new ArrayList<>();
    }

    public void addCreature(Creatures creature) {
        creatures.add(creature);
    }
}
