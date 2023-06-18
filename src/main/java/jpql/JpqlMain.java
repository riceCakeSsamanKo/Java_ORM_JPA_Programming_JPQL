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
            for(int i=0;i<5;i++){
                Team team = new Team();
                team.setName("team"+i);

                Member member = new Member();
                member.setUsername("member" + i);
                member.changeTeam(team);

                em.persist(team);
            }

            // 세타 조인: Member와 Team을 모두 가져온 다음 그중 where문의 조건에 맞는 것들만 추림
            String query = "select m, t from Member m left join m.team t on t.name = 'team3'";
            List result = em.createQuery(query)
                    .getResultList();

            System.out.println(result.get(0));


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