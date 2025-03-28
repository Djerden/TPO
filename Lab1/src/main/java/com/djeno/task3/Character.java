package com.djeno.task3;

import com.djeno.task3.enums.EmotionalState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Character {
    private String name;
    private EmotionalState state;

    public Character(String name) {
        this.name = name;
        this.state = EmotionalState.CALM;
    }

    public Phrase say(String text) {
        return new Phrase(text, state);
    }
}
