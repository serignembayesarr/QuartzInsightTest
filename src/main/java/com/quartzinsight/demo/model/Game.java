package com.quartzinsight.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "games")
public class Game implements Serializable {

    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String url;

    private Integer available = 1;


    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_game", joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "Id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "Id"))
    private Set<User> users = new HashSet<>();



    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.id);
        hash = 31 * hash + Objects.hashCode(this.title);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Game other = (Game) obj;
        if (this.title != other.title) {
            return false;
        }

        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        final StringBuilder description = new StringBuilder("Game{");
        description.append("id=").append(id);
        description.append(", Title='").append(title).append('\'');
        description.append(", Url=").append(url);
        description.append('}');
        return description.toString();
    }
}