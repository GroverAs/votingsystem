package ru.savelyev.votingsystem.to;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
public class DishTo extends NamedTo {

    Integer price;
    LocalDate creatingDate;

    public DishTo(Integer id, String name, Integer price, LocalDate creatingDate) {
        super(id, name);
        this.price = price;
        this.creatingDate = creatingDate;
    }
}

