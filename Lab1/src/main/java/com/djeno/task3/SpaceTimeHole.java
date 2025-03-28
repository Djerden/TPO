package com.djeno.task3;

public class SpaceTimeHole {
    private boolean isRandom;
    private int size;

    public SpaceTimeHole(boolean isRandom, int size) {
        this.isRandom = isRandom;
        this.size = size;
    }

    public boolean canTransport(Phrase phrase) {
        return phrase.getText().length() <= size;
    }

    public void transport(Phrase phrase, Galaxy galaxy) {
        if (isRandom && canTransport(phrase)) {
            System.out.println("Дыра в пространстве-времени открылась и перенесла фразу в галактику: " + galaxy.getName());
        } else {
            System.out.println("Фраза слишком большая для переноса.");
        }
    }
}
