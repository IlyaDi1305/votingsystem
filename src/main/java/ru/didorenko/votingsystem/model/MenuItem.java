package ru.didorenko.votingsystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.didorenko.votingsystem.common.model.NamedEntity;
import ru.didorenko.votingsystem.common.validation.NoHtml;
import java.time.LocalDate;

@Entity
@Table(name = "menu_item")
@Getter
@Setter
@NoArgsConstructor
public class MenuItem extends NamedEntity {

    @Column(name = "description")
    @Size(min = 2, max = 128)
    @NotBlank
    @NoHtml
    private String description;

    @Column(name = "price")
    @NotNull
    private Integer price; // Price in cents for better precision

    @Column(name = "menu_item_date")
    @NotNull
    private LocalDate menuItemDate;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "restaurant_id")
    @Schema(hidden = true)
    private Restaurant restaurant;

    public MenuItem(Integer id, String name, String description, Integer price, LocalDate menuItemDate) {
        super(id, name);
        this.description = description;
        this.price = price;
        this.menuItemDate = menuItemDate;
    }

    @Override
    public String toString(){
        return "MenuItem{" +
                "description='" + description + '\'' +
                ", price=" + price +
                ", date=" + menuItemDate +
                ", name=" + name +
                ", id=" + id +
                '}';
    }
}