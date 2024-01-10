package ru.savelyev.votingsystem.util;

import lombok.experimental.UtilityClass;
import ru.savelyev.votingsystem.model.Dish;
import ru.savelyev.votingsystem.to.DishTo;
import ru.savelyev.votingsystem.to.NamedTo;

import java.util.Comparator;
import java.util.List;

@UtilityClass
public class DishUtil {

    public static DishTo createDishTo(Dish dish) {
        return new DishTo(dish.getId(), dish.getName(), dish.getPrice());
    }

    public static List<DishTo> getDishTos(List<Dish> dishes) {
        return dishes.stream()
                .map(DishUtil::createDishTo)
                .sorted(Comparator.comparing(NamedTo::getName))
                .toList();
    }

    public static Dish getDish(DishTo dishTo) {
        return new Dish(dishTo.getId(), dishTo.getName(), dishTo.getPrice());
    }

}
