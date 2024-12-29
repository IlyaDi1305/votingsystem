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
