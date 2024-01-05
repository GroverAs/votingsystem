package ru.savelyev.votingsystem.web.dish;

import lombok.experimental.UtilityClass;
import ru.savelyev.votingsystem.model.Dish;
import ru.savelyev.votingsystem.to.DishTo;
import ru.savelyev.votingsystem.web.MatcherFactory;

import java.time.LocalDate;

@UtilityClass
public class DishTestData {

    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant");
    public static final MatcherFactory.Matcher<DishTo> DISH_TO_MATCHER = MatcherFactory.usingEqualsComparator(DishTo.class);

    public static final Dish moscowTime_Dish1 = new Dish(1, "Pizza", 480);
    public static final Dish moscowTime_Dish2 = new Dish(2, "Soup", 350);
    public static final Dish moscowTime_Dish3 = new Dish(3, "Coffee", 120);
    public static final Dish meatPlace_Dish1 = new Dish(4, "Steak", 1990);
    public static final Dish meatPlace_Dish2 = new Dish(5, "Burger", 290);
    public static final Dish meatPlace_Dish3 = new Dish(6, "Tea", 95);
    public static final Dish yaponaPapa_Dish1 = new Dish(7, "Sushi set", 95);
    public static final Dish yaponaPapa_Dish2 = new Dish(8, "Fish", 470);
    public static final Dish yaponaPapa_Dish3 = new Dish(9, "WOK", 650);

    public static Dish getNew() {
        return new Dish(null, "New_Dish11", 111);
    }

    public static Dish getUpdated() {
        Dish updated = new Dish(moscowTime_Dish1);
        updated.setName("Pizza updated");
        return updated;
    }
}
