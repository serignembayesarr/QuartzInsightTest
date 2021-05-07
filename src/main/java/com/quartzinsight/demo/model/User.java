package com.quartzinsight.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")


@Getter
@Setter
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;


    @ElementCollection
    @CollectionTable(name="user_game", joinColumns=@JoinColumn(name="user_id"))
    @Column(name="game_id")
    public Set<Integer> gameIds;

    @ElementCollection
    @CollectionTable(name="friendship", joinColumns=@JoinColumn(name="adder_id"))
    @Column(name="added_id")
    public Set<Long> friendsId = new HashSet<>();


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.id);
        hash = 31 * hash + Objects.hashCode(this.username);
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
        final User other = (User) obj;
        if (this.username != other.username) {
            return false;
        }

        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        final StringBuilder description = new StringBuilder("User{");
        description.append("id=").append(id);
        description.append(", name='").append(username).append('\'');
        description.append(", email=").append(email);
        description.append('}');
        return description.toString();
    }


}