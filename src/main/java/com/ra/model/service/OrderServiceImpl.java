package com.ra.model.service;

import com.ra.model.dao.OrderDAO;
import com.ra.model.dao.OrderDetailDAO;
import com.ra.model.dao.ProductDao;
import com.ra.model.entity.CartItem;
import com.ra.model.entity.Order;
import com.ra.model.entity.OrderDetail;
import com.ra.model.entity.Product;
import com.ra.model.service.cartItem.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    OrderDAO orderDAO;
    @Autowired
    HttpSession session;
    @Autowired
    OrderDetailDAO orderDetailDAO;
    @Autowired
    CartItemService cartItemService;
    @Autowired
    ProductService productService;
        @Override
    public List<Order> findAll() {
        return orderDAO.findAll();
    }

//    @Override
//    public void reduceStock(Integer productId, int quantity) {
//            // Lấy thông tin sản phẩm từ cơ sở dữ liệu
//            Product product = productDao.findById(productId);
//
//            // Kiểm tra xem có đủ số lượng tồn kho hay không
//            int currentStock = product.getStock();
//            int newStock = currentStock - quantity;
//
//            if (newStock >= 0) {
//                // Cập nhật số lượng tồn kho
//                product.setStock(newStock);
//                productDao.save(product);
//            } else {
//                throw new RuntimeException("Not enough stock for product with ID: " + productId);
//            }
//    }

    @Override
    public void order(Order order) {
       int orderId= orderDAO.save(order);
       List<CartItem>cartItemList= cartItemService.findAll();
        for (CartItem cartItem : cartItemList){
            Product product = cartItem.getProduct();
            int orderedQuantity = cartItem.getQuantity();
            OrderDetail orderDetail=new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setProduct(cartItem.getProduct());
            orderDetail.setPrice(cartItem.getProduct().getPrice());
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetailDAO.save(orderDetail);
            productService.reduceStock(product.getProductId(), orderedQuantity);
        }
    }

    @Override
    public void statusOrder(Integer id, int status) {
        orderDAO.statusOrder(id,status);
    }

    @Override
    public Order findById(Integer id) {
        return orderDAO.findById(id);
    }

    @Override
    public List<Order> findALLById(Integer id) {
        return orderDAO.findALLById(id);
    }

    @Override
    public Order findByIdOrDer(Integer id) {
        return orderDAO.findByIdOrDer(id);
    }


//    @Transactional(rollbackFor = Exception.class)
//    public void cancelReduceStock(Long productId, int quantity) {
//        // Lấy thông tin sản phẩm từ cơ sở dữ liệu
//        Product product = productDAO.findById(productId);
//
//        // Cộng trở lại số lượng tồn kho
//        int currentStock = product.getStock();
//        int newStock = currentStock + quantity;
//        product.setStock(newStock);
//
//        // Cập nhật số lượng tồn kho
//        productDAO.save(product);
//    }
}
