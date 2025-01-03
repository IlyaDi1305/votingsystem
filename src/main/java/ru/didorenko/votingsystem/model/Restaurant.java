package ru.didorenko.votingsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.didorenko.votingsystem.common.model.BaseEntity;

import java.util.List;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor
public class Restaurant extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    @Size(min = 2, max = 128)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "restaurant")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<MenuItem> menuItems;

    public Restaurant(Integer id, String name) {
        super(id);
        this.name = name;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
