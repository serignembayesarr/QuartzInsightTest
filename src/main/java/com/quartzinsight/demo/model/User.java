package com.quartzinsight.demo.model;


import lombok.*;

import javax.persistence.*;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
public class User  {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    @ElementCollection
    @CollectionTable(name="user_game", joinColumns=@JoinColumn(name="user_id"))
    @Column(name="game_id")
    private Set<Integer> gameIds;


    @ElementCollection
    @CollectionTable(name="friendship", joinColumns=@JoinColumn(name="adder_id"))
    @Column(name="added_id")
    private Set<Long> friendsId = new HashSet<>();

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.username);
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
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", name='").append(username).append('\'');
        sb.append(", email=").append(email);
        sb.append('}');
        return sb.toString();
    }


}