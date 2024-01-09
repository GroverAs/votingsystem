package ru.savelyev.votingsystem.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import ru.savelyev.votingsystem.HasId;

import java.time.LocalDate;

    @Value
    @EqualsAndHashCode(callSuper = false)
    @ToString(callSuper = true)
    public class VoteTo extends BaseTo implements HasId {
        LocalDate votingDate;
        int restaurantId;

        public VoteTo(LocalDate votingDate, int restaurantId) {
            this.votingDate = votingDate;
            this.restaurantId = restaurantId;
        }

}
