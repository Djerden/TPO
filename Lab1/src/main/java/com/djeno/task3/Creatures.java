package com.djeno.task3;

import com.djeno.task3.enums.Race;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Creatures {
    private Race type;
    private int aggressionLevel;

    public void changeAggressionLevel(int change) {
        if (change < 0) {
            this.aggressionLevel = Math.max(0, this.aggressionLevel + change);
        } else {
            this.aggressionLevel = Math.min(100, this.aggressionLevel + change);
        }
    }

}
