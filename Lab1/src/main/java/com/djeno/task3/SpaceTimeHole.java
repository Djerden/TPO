package com.djeno.task3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpaceTimeHole {
    private int validPhraseSize;
    private Galaxy galaxy;

    public boolean canTransport(Phrase phrase) {
        return phrase.getText().length() <= validPhraseSize;
    }

    public void transport(Phrase phrase) {
        if (canTransport(phrase)) {
            System.out.println("Дыра в пространстве-времени открылась и перенесла фразу в галактику: " + galaxy.getName());

            galaxy.getPhraseFromSpaceTimeHole(phrase);

        } else {
            System.out.println("Фраза слишком большая для переноса.");
        }
    }
}
