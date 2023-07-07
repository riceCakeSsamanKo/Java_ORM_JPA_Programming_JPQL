package jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class JpqlMain {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            for (int i = 0; i < 3; i++) {
                Member member = new Member();
                member.setUsername(""+i);
                member.setAge(i);
                em.persist(member);

                Team team = new Team();
                team.setName(""+i);
                em.persist(team);

            }

            List<Object[]> resultList = em.createQuery("select m, t from Member m left join Team t on m.username = t.name").getResultList();
            for (Object[] o: resultList) {
                System.out.println("member = " + o[0]);
                System.out.println("team = " + o[1]);
            }  //Member와 Team이 연관관계는 없어도 join을 통해서 같이 가져옴

            List<Member> resultList2 = em.createQuery("select m from Member m left join Team t on m.username = t.name",Member.class).getResultList();
            for (Member m: resultList2) {
                System.out.println("m.getTeam() = " + m.getTeam());
            } //m.getTeam() == Null => 연관관계 없음

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


