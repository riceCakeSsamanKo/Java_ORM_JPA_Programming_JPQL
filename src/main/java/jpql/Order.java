package jpql;

import lombok.Getter;
import javax.persistence.*;

@Entity
@Getter
@Table(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    private int orderAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Embedded
    private Address address;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    // 연관관계 메소드
    public void changeMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    // setter
    public void setAddress(Address address) {
        this.address = address;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
