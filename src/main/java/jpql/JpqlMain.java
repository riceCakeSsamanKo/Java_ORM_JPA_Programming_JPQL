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
            team.setName("teamA");

            Member member = new Member();
            member.setUsername("memberA");
            member.setAge(10);
            member.setType(MemberType.ADMIN);
            member.setTeam(team);

            Member member2 = new Member();
            member2.setUsername("memberB");
            member2.setAge(20);
            member2.setType(MemberType.ADMIN);
            member2.setTeam(team);

            em.persist(member);
            em.persist(member2);

            em.flush();
            em.clear();

            String query = "select m, 'HELLO', true From Member m  " +
                            "where m.type =  jpql.MemberType.ADMIN";
            List<Object[]> result = em.createQuery(query)
                    .getResultList();

            for (Object[] objects : result) {
                System.out.println("objects = " + objects[0]);
                System.out.println("objects = " + objects[1]);
                System.out.println("objects = " + objects[2]);
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