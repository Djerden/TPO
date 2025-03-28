package com.djeno.task3;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Creatures {
    private String type;
    private String status;
    private int aggressionLevel;

    public Creatures(String type, String status) {
        this.type = type;
        this.status = status;
        this.aggressionLevel = 50; // Средний уровень агрессии
    }
}
