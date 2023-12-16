package ru.savelyev.votingsystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

@Entity
@Table(name = "dish", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "local_date", "restaurant_id"},
        name = "dish_unique_restaurant_name_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dish extends NamedEntity {

    @Column(name = "name", nullable = false)
    @Size(max = 150)
    @NotBlank
    private String name;

    @Column(name = "price", nullable = false)
    @Range(min = 10, max = 8000)
    @NotNull
    private Integer price;

    @Column(name = "local_date", nullable = false)
    @NotNull
    private LocalDate localDate = LocalDate.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    @NotNull
    private Restaurant restaurant;

    public Dish(String name, Integer price) {
        super(null, name);
        this.price = price;
    }

    public Dish(Integer id, String name, Integer price, Restaurant restaurant, LocalDate localDate) {
        super(id, name);
        this.price = price;
        this.restaurant = restaurant;
        this.localDate = localDate;
    }

    public Dish(Dish dish) {
        this(dish.getId(), dish.getName(), dish.getPrice(), dish.getLocalDate(), dish.getRestaurant());
    }

    public Dish(Integer id, String name, Integer price, LocalDate localDate, Restaurant restaurant) {
        super(id, name);
        this.price = price;
        this.localDate = localDate;
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", localDate=" + localDate +
                ", restaurant=" + restaurant +
                '}';
    }
}
