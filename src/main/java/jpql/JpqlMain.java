package jpql;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpqlMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            

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
