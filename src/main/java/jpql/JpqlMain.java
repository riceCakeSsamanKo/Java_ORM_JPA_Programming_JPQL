package jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpqlMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            team.setName("TEAM1");

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            member.changeTeam(team);

            em.persist(member);

            em.flush();
            em.clear();


            List<Team> resultList =
                    em.createQuery("select m.team from Member m", Team.class).getResultList();
            Team team1 = resultList.get(0);
            System.out.println("team1 = " + team1.getName());

            tx.commit();
        } catch (Exception e) {
            tx.rollback(); // 오류 발생 시 롤백
            e.printStackTrace(); // 에러 내용 출력
        } finally {
            //종료
            em.close();
        }
        emf.close();
    }
}
