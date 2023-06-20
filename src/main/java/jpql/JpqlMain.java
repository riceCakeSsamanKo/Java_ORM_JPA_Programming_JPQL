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
            Member member1 = new Member("회원 1", 20);
            Member member2 = new Member("회원 2", 40);
            Member member3 = new Member("회원 3", 60);
            em.persist(member1);
            em.persist(member2);
            em.persist(member3);

            // 모든 member의 age를 100으로 update
            String query = "update Member m set m.age = 100";
            int i = em.createQuery(query).executeUpdate(); //executeUpdate()로 벌크 연산, i는 update된 row의 수

            Member findMember = em.find(Member.class, member1.getId());
            System.out.println("findMember = " + findMember.getAge());  // 영속성 컨텍스트의 내용이 DB와 다른 값이 출력
            // 영속성 컨텍스트 비우기
            em.clear();

            // 영속성 컨텍스트가 비워져 있으니 DB에 먼저 접근 후 영속성 컨텍스트 초기화
            Member findMember2 = em.find(Member.class, member1.getId());
            System.out.println("findMember = " + findMember2.getAge()); // DB와 같은 값이 출력

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


