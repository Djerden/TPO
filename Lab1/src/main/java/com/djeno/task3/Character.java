package com.djeno.task3;

import com.djeno.task3.enums.EmotionalState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Character {
    private String name;
    private EmotionalState state;

    public Character(String name) {
        this.name = name;
        this.state = EmotionalState.CALM;
    }

    public Character(String name, EmotionalState state) {
        this.name = name;
        this.state = state;
    }

    public Phrase say(String text) {
        return new Phrase(text, state);
    }
}
