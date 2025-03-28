package com.djeno.task3;

import com.djeno.task3.enums.EmotionalState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Phrase {
    private String text;
    private EmotionalState emotion;

    public Phrase(String text,  EmotionalState emotion) {
        this.text = text;
        this.emotion = emotion;
    }
}
