package ru.didorenko.votingsystem.restaurant.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.didorenko.votingsystem.common.model.NamedEntity;
import java.time.LocalDate;

@Entity
@Table(name = "menus")
@Getter
@Setter
@ToString
public class Menu extends NamedEntity {

    @Column(name = "description")
    @Size(min = 2, max = 128)
    @NotBlank
    private String description;

    @Column(name = "price")
    @NotNull
    private Double price;

    @Column(name = "date")
    @NotNull
    private LocalDate date;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
}
