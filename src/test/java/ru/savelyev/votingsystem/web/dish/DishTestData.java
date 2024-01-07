package ru.savelyev.votingsystem.web.dish;

import lombok.experimental.UtilityClass;
import ru.savelyev.votingsystem.model.Dish;
import ru.savelyev.votingsystem.to.DishTo;
import ru.savelyev.votingsystem.web.MatcherFactory;

import java.time.LocalDate;
import java.util.List;

@UtilityClass
public class DishTestData {

    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant");
    public static final MatcherFactory.Matcher<DishTo> DISH_TO_MATCHER = MatcherFactory.usingEqualsComparator(DishTo.class);

    public static final Dish moscowTime_Dish1 = new Dish(1, "Pizza", 480, null, LocalDate.now());
    public static final Dish moscowTime_Dish2 = new Dish(2, "Soup", 350, null, LocalDate.now());
    public static final Dish moscowTime_Dish3 = new Dish(3, "Coffee", 120, null, LocalDate.now());
    public static final Dish meatPlace_Dish1 = new Dish(4, "Steak", 1990, null, LocalDate.now());
    public static final Dish meatPlace_Dish2 = new Dish(5, "Burger", 290, null, LocalDate.now());
    public static final Dish meatPlace_Dish3 = new Dish(6, "Tea", 95, null, LocalDate.now());
    public static final Dish yaponaPapa_Dish1 = new Dish(7, "Sushi set", 95, null, LocalDate.now());
    public static final Dish yaponaPapa_Dish2 = new Dish(8, "Fish", 470, null, LocalDate.now());
    public static final Dish yaponaPapa_Dish3 = new Dish(9, "WOK", 650, null, LocalDate.now());

    public static final Dish yaponaPapa_old1 = new Dish(10, "Sushi roll", 399, null, LocalDate.now().minusDays(1));

    public static final List<Dish> moscowTime_menu = List.of(moscowTime_Dish3, moscowTime_Dish2, moscowTime_Dish1);
    public static final List<Dish> meatPlace_menu = List.of(meatPlace_Dish3, meatPlace_Dish2, meatPlace_Dish1);
    public static final List<Dish> yaponaPapa_menu = List.of(yaponaPapa_Dish3, yaponaPapa_Dish2, yaponaPapa_Dish1);

    public static final List<Dish> yaponaPapa_menu_allDays = List.of(yaponaPapa_Dish3, yaponaPapa_Dish2, yaponaPapa_Dish1, yaponaPapa_old1);

    public static Dish getNew() {
        return new Dish(null, "New_Dish11", 111, null, LocalDate.now());
    }

    public static Dish getUpdated() {
        Dish updated = new Dish(moscowTime_Dish1);
        updated.setName("Pizza updated");
        return updated;
    }
}
