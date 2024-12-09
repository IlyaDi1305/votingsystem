package ru.didorenko.votingsystem.restaurant.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
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
import ru.didorenko.votingsystem.common.model.NamedEntity;
import java.time.LocalDate;

@Entity
@Table(name = "menu_item")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuItem extends NamedEntity {

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
    @Schema(hidden = true)
    private Restaurant restaurant;

    public MenuItem(Integer id, String name, String description, @NotNull Double price, @NotNull LocalDate date) {
        super(id, name);
        this.description = description;
        this.price = price;
        this.date = date;
    }

    @Override
    public String toString(){
        return "MenuItem{" +
                "description='" + description + '\'' +
                ", price=" + price +
                ", date=" + date +
                ", name=" + name +
                ", id=" + id +
                '}';
    }
}
