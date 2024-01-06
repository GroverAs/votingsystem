package ru.savelyev.votingsystem.util;

import lombok.experimental.UtilityClass;
import ru.savelyev.votingsystem.model.Dish;
import ru.savelyev.votingsystem.to.DishTo;
import ru.savelyev.votingsystem.to.NamedTo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@UtilityClass
public class DishUtil {

    public static DishTo createDishTo(Dish dish) {
        return new DishTo(dish.getId(), dish.getName(), dish.getPrice());
    }

    public static List<DishTo> createDishTos(List<Dish> dishes) {
        List<DishTo> dishTos = new ArrayList<>();
        for (Dish dish : dishes) {
            dishTos.add(createDishTo(dish));
        }
        dishTos.sort(Comparator.comparing(NamedTo::getName));
        return dishTos;
    }

    public static Dish getDish(DishTo dishTo) {
        return new Dish(dishTo.getId(), dishTo.getName(), dishTo.getPrice());
    }

}
