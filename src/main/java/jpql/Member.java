package jpql;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@NamedQuery( // 쿼리문을 미리 정의해둠.
        name = "Member.findByUsername",  // 아래 query 내용을 직접 입력하지 않고도 Member.findByUsername으로 대치해서 사용이 가능함.
        query = "select m from Member m where m.username = :username"  // 프로그램 로딩시 쿼리문에 문제가 있다면 에러 발생(검증가능)
)
public class Member {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "USERNAME")
    private String username;
    private int age;

    Member(String name, int age) {
        this.username = name;
        this.age = age;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private MemberType type;

    // 연관관계 메소드
    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }

    // setter
    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setType(MemberType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}
