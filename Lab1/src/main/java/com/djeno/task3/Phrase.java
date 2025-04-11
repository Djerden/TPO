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
public class Phrase {
    private String text;
    private EmotionalState emotion;
}
