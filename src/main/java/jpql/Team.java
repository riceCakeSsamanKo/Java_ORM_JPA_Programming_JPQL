package jpql;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Team {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    Team(String name) {
        this.name = name;
    }

    Team(String name, Member... members) {
        this.name = name;
        for (Member member : members) {
            member.changeTeam(this);
        }
    }
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "team")
    private List<Member> members = new ArrayList<>();

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
