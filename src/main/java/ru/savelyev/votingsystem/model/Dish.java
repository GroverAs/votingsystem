package ru.savelyev.votingsystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;
import ru.savelyev.votingsystem.HasId;

import java.time.LocalDate;

@Entity
@Table(name = "dish", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "actual_date", "restaurant_id"},
        name = "dish_unique_restaurant_name_idx")})
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Dish extends NamedEntity implements HasId {

    @Column(name = "price", nullable = false)
    @NotNull
    private int price;

    @Column(name = "actual_date", nullable = false)
    @NotNull
    private LocalDate actualDate = LocalDate.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonBackReference
    @NotNull
    private Restaurant restaurant;

    public Dish(Integer id, String name, int price, Restaurant restaurant, LocalDate actualDate) {
        super(id, name);
        this.price = price;
        this.restaurant = restaurant;
        this.actualDate = actualDate;

    }

    public Dish(Dish dish) {
        super(dish.getId(), dish.getName());
        this.price = dish.getPrice();
        this.actualDate = dish.getActualDate();
        this.restaurant = dish.getRestaurant();
    }

    public Dish(Integer id, String name, int price) {
        super(id, name);
        this.price = price;
    }
}
