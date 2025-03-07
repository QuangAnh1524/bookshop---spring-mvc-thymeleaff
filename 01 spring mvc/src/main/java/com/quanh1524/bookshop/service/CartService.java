package com.quanh1524.bookshop.service;

import com.quanh1524.bookshop.domain.Cart;
import com.quanh1524.bookshop.domain.CartDetail;
import com.quanh1524.bookshop.domain.Product;
import com.quanh1524.bookshop.domain.User;
import com.quanh1524.bookshop.repository.CartDetailRepository;
import com.quanh1524.bookshop.repository.CartRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final ProductService productService;
    private final UserService userService;

    public CartService(CartRepository cartRepository, CartDetailRepository cartDetailRepository,
                       ProductService productService, UserService userService) {
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.productService = productService;
        this.userService = userService;
    }

    //tao gio hang cho user
    public Cart getOrCreateCart(User user, HttpSession session) {
        if (user == null) {
            throw new IllegalStateException("User must be logged in");
        }
        Cart cart = cartRepository.findByUserId(user.getId());
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setSum(0);
            cart.setCartDetails(new ArrayList<>());
            cartRepository.save(cart);
        }
        return cart;
    }

    //them san pham vao gio
    public void addProductToCart(Long productId, int quantity, User user, HttpSession session) {
        Cart cart = getOrCreateCart(user, session);
        Product product = productService.getProductById(productId);
        if (product == null) return;
        CartDetail cartDetail = cartDetailRepository.findByCartIdAndProductId(cart.getId(), productId);
        if (cartDetail == null) {
            cartDetail = new CartDetail();
            cartDetail.setCart(cart);
            cartDetail.setProduct(product);
            cartDetail.setQuantity(quantity);
            cartDetail.setPrice(product.getPrice());
            cart.getCartDetails().add(cartDetail);
            cartDetailRepository.save(cartDetail);
        } else {
            cartDetail.setQuantity(cartDetail.getQuantity() + quantity);
            cartDetailRepository.save(cartDetail);
        }
        int sum = 0;
        for (CartDetail detail : cart.getCartDetails()) {
            long cartDetail1Quantity = detail.getQuantity();
            sum += (int) cartDetail1Quantity;
        }
        cart.setSum(sum);
        cartRepository.save(cart);
    }

    public void updateQuantity(long cartDetailId, int change, User user, HttpSession session) {
        Cart cart = getOrCreateCart(user, session);
        CartDetail cartDetail = cartDetailRepository.findById(cartDetailId).orElse(null);
        if (cartDetail != null && cartDetail.getCart().getId() == (cart.getId())) {
            int newQuantity = (int) (cartDetail.getQuantity() + change);
            if (newQuantity <= 0) {
                cart.getCartDetails().remove(cartDetail);
                cartDetailRepository.delete(cartDetail);
            } else {
                cartDetail.setQuantity(newQuantity);
                cartDetailRepository.save(cartDetail);
            }
            int sum = 0;
            for (CartDetail detail : cart.getCartDetails()) {
                int quantity = (int) detail.getQuantity();
                sum += quantity;
            }
            cart.setSum(sum);
            cartRepository.save(cart);
        }
    }

    public void removeFromCart(Long cartDetailId, User user, HttpSession session) {
        Cart cart = getOrCreateCart(user, session);
        CartDetail cartDetail = cartDetailRepository.findById(cartDetailId).orElse(null);
        if (cartDetail != null && cartDetail.getCart().getId() == cart.getId()) {
            cart.getCartDetails().remove(cartDetail);
            cartDetailRepository.delete(cartDetail);
            int sum = 0;
            for (CartDetail detail : cart.getCartDetails()) {
                int quantity = (int) detail.getQuantity();
                sum += quantity;
            }
            cart.setSum(sum);
            cartRepository.save(cart);
        }
    }
}
