package ru.savelyev.votingsystem.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.LocalDate;

    @Value
    @EqualsAndHashCode
    @ToString(callSuper = true)
    public class VoteTo {
        LocalDate votingDate;
        int restaurantId;

        public VoteTo(LocalDate votingDate, int restaurantId) {
            this.votingDate = votingDate;
            this.restaurantId = restaurantId;
        }

}
