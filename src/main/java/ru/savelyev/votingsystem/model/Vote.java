package ru.savelyev.votingsystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "vote", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "voting_date"},
        name = "vote_unique_local_date_idx"))
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vote extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @JsonBackReference
    private User user;

    @Column(name = "voting_date", nullable = false, updatable = false, columnDefinition = "timestamp default now()")
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate votingDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @JsonBackReference
    private Restaurant restaurant;

    public Vote(Integer id, LocalDate votingDate, User user, Restaurant restaurant) {
        super(id);
        this.votingDate = votingDate;
        this.user = user;
        this.restaurant = restaurant;
    }

    public Vote(LocalDate votingDate, User user, Restaurant restaurant) {
        this.votingDate = votingDate;
        this.user = user;
        this.restaurant = restaurant;
    }

}
