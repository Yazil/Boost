package fr.yazil.boost.boosts;

import lombok.*;

@Getter
@RequiredArgsConstructor
public class Boost {
    
    // Current xp multiplier of the boost
    @NonNull
    private float multiplier;

    // Tracks the time in minutes for which the boost is active
    private int activeFor = 0;

    // Duration of the boost
    @NonNull
    private int duration;

    //Add the multiplier and duration of another boost to the current one.
    public void addBoost(Boost boostToAdd) {
        this.multiplier += boostToAdd.getMultiplier();
        this.duration += boostToAdd.getDuration();
    }

    public void incrementActiveFor() {
        this.activeFor++;
    }



}
