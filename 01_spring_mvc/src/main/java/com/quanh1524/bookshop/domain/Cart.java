package com.quanh1524.bookshop.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

import java.util.List;

@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Min(value = 0)
    private int sum;

    //user_id (1 user chi co 1 gio hang)
    @OneToOne()
    @JoinColumn(name = "user_id")
    private User user;

    //cart_detail_id (1 gio hang co nhieu ban ghi cua thang detail)
    @OneToMany(mappedBy = "cart")
    List<CartDetail> cartDetails;

    public Cart() {}

    public Cart(long id, int sum, User user, List<CartDetail> cartDetails) {
        this.id = id;
        this.sum = sum;
        this.user = user;
        this.cartDetails = cartDetails;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Min(value = 0)
    public int getSum() {
        return sum;
    }

    public void setSum(@Min(value = 0) int sum) {
        this.sum = sum;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartDetail> getCartDetails() {
        return cartDetails;
    }

    public void setCartDetails(List<CartDetail> cartDetails) {
        this.cartDetails = cartDetails;
    }

    public double getTotalPrice() {
        double result = 0.0;
        for (CartDetail detail : cartDetails) {
            double v = detail.getQuantity() * detail.getPrice();
            result += v;
        }
        return result;
    }

    public double getTotalPriceWithTax() {
        double total = 0.0;
        for (CartDetail detail : cartDetails) {
            double v = detail.getQuantity() * detail.getPrice();
            total += v;
        }
        return total + 30000;
    }

}
