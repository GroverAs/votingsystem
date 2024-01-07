package ru.savelyev.votingsystem.util;

import lombok.experimental.UtilityClass;
import ru.savelyev.votingsystem.model.Dish;
import ru.savelyev.votingsystem.to.DishTo;
import ru.savelyev.votingsystem.to.NamedTo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class DishUtil {

    public static DishTo createDishTo(Dish dish) {
        return new DishTo(dish.getId(), dish.getName(), dish.getPrice());
    }

    public static List<DishTo> createDishTos(List<Dish> dishes) {
        return dishes.stream()
                .map(DishUtil::createDishTo)
                .sorted(Comparator.comparing(NamedTo::getName))
                .collect(Collectors.toList());
    }

    public static Dish getDish(DishTo dishTo) {
        return new Dish(dishTo.getId(), dishTo.getName(), dishTo.getPrice());
    }

}
