package jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class JpqlMain {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member1 = new Member();
            Member member2 = new Member();
            Member member3 = new Member();
            member1.setUsername("회원1");
            member2.setUsername("회원2");
            member3.setUsername("회원3");

            Team teamA = new Team();
            Team teamB = new Team();
            Team teamC = new Team();
            teamA.setName("팀A");
            teamB.setName("팀B");
            teamC.setName("팀C");

            member1.changeTeam(teamA);
            member2.changeTeam(teamA);
            member3.changeTeam(teamB);
            em.persist(teamA);
            em.persist(teamB);
            em.persist(teamC);


            String query = "select t from Team t ";
            List<Team> teams = em.createQuery(query, Team.class)
                    .setFirstResult(0)
                    .setMaxResults(2)
                    .getResultList();

            System.out.println("teams = " + teams.size());


            for (Team team : teams) {
                //페치 조인으로 회원과 팀을 함께 조회해서 지연 로딩X
                System.out.println("teamName = " + team.getName() + " team.members= "+team.getMembers().size());

            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback(); // 오류 발생 시 롤백
            e.printStackTrace(); // 에러 내용 출력
        } finally {
            em.close(); // 종료
        }
        emf.close();
    }
}


