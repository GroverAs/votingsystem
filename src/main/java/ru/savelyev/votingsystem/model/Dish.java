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
@Table(name = "dish", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "creating_date", "restaurant_id"},
        name = "dish_unique_restaurant_name_idx")})
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Dish extends NamedEntity implements HasId {

    @Column(name = "name", nullable = false)
    @Size(max = 150)
    @NotBlank
    private String name;

    @Column(name = "price", nullable = false)
    @Range(min = 10, max = 8000)
    @NotNull
    private int price;

    @Column(name = "creating_date", nullable = false)
    @NotNull
    private LocalDate creatingDate = LocalDate.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonBackReference
    @NotNull
    private Restaurant restaurant;

    public Dish(String name, int price) {
        super(null, name);
        this.price = price;
    }

    public Dish(Integer id, String name, int price, Restaurant restaurant, LocalDate creatingDate) {
        super(id, name);
        this.price = price;
        this.restaurant = restaurant;
        this.creatingDate = creatingDate;

    }

    public Dish(Dish dish) {
        super(dish.getId(), dish.getName());
        this.price = dish.getPrice();
        this.creatingDate = dish.getCreatingDate();
        this.restaurant = dish.getRestaurant();
    }

    public Dish(Integer id, String name, int price, LocalDate localDate, Restaurant restaurant) {
        super(id, name);
        this.price = price;
        this.creatingDate = localDate;
        this.restaurant = restaurant;
    }

    public Dish(Integer id, String name, int price) {
        super(id, name);
        this.price = price;
    }
}
