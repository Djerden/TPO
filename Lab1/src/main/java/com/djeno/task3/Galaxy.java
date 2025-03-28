package com.djeno.task3;

import com.djeno.task3.enums.EmotionalState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Galaxy {
    private String name;
    private String description;
    private List<Creatures> creatures;
    private boolean isWar = false;

    public void addCreature(Creatures creature) {
        creatures.add(creature);
    }

    public void getPhraseFromSpaceTimeHole(Phrase phrase) {
        EmotionalState emotionPhrase = phrase.getEmotion();

        switch (emotionPhrase) {
            case HAPPY:
                for (Creatures creature : creatures) {
                    creature.changeAggressionLevel(-50);
                }
                break;
            case SAD:
                for (Creatures creature : creatures) {
                    creature.changeAggressionLevel(10);
                }
                break;
            case PANIC:
                for (Creatures creature : creatures) {
                    creature.changeAggressionLevel(30);
                }
                break;
            case ANGRY:
                for (Creatures creature : creatures) {
                    creature.changeAggressionLevel(50);
                }
                System.out.println("Уровень агрессии в галактике не поменялс");
                break;
            case CALM:
                System.out.println("Уровень агрессии в галактике не поменялся");
                break;

        }
        checkWarStatus();
    }

    private void checkWarStatus() {
        boolean hasAggressiveCreatures = creatures.stream()
                .anyMatch(c -> c.getAggressionLevel() > 70);

        if (!isWar && hasAggressiveCreatures) {
            isWar = true;
            System.out.println("В галактике началась война");
        } else if (isWar && !hasAggressiveCreatures) {
            isWar = false;
            System.out.println("Война в галактике завершилась");
        }
    }

}
