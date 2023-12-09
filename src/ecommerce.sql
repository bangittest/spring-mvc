create database ecommerce;
use ecommerce;
create table category(
                         id int primary key NOT NULL auto_increment,
                         name varchar(255) NOT NULL unique ,
                         status bit(1) default 1
);

create table product(
                        id int primary key NOT NULL auto_increment,
                        category_id int NOT NULL ,
                        foreign key (category_id) REFERENCES category(id),
                        name varchar(255) NOT NULL unique,
                        description varchar(255) NOT NULL,
                        price double,
                        url_image varchar(255),
                        quantity int,
                        status bit(1) default 1
);
create table image(
                      id int primary key NOT NULL auto_increment,
                      url varchar(255) NOT NULL ,
                      product_id int not null,
                      foreign key (product_id) REFERENCES product(id)
);

create table customer(
                         id int primary key NOT NULL auto_increment,
                         name varchar(255) NOT NULL,
                         email varchar(255) NOT NULL unique ,
                         address varchar(255) NOT NULL,
                         phone varchar(255) not null ,
                         password varchar(255) NOT NULL,
                         image varchar(255),
                         roles varchar(50) default 'USER',
                         cus_status bit(1) default 1
);

CREATE TABLE orders (
                        id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
                        customer_id INT NOT NULL,
                        order_date DATE,
                        order_status BIT(1) DEFAULT 0,
                        FOREIGN KEY (customer_id) REFERENCES customer(id),
                        total DOUBLE NOT NULL
);

create table order_detail(
                             order_id int not null,
                             foreign key (order_id) references orders(id),
                             product_id int not null,
                             foreign key (product_id) references product(id),
                             quantity int NOT NULL,
                             price double NOT NULL,
                             primary key (order_id, product_id)
);

create table cart(
                     id int primary key NOT NULL auto_increment,
                     customer_id int not null,
                     foreign key (customer_id) references customer(id)
);

create table cart_item(
                          id int primary key NOT NULL auto_increment,
                          cart_id int not null,
                          foreign key (cart_id) references cart(id),
                          product_id int not null,
                          price double NOT NULL,
                          quantity int NOT NULL
);


# category
DELIMITER //
CREATE PROCEDURE PROC_CREATE_CATEGORY(
    IN p_name VARCHAR(255),IN p_status bit
)
BEGIN
    INSERT INTO category (name,status)
    VALUES (p_name,p_status);
END //
DELIMITER ;
# Cập nhật thông tin của một danh mục:


DELIMITER //
CREATE PROCEDURE PROC_UPDATE_CATEGORY(
    IN p_id INT,
    IN p_name VARCHAR(255),
    IN p_status bit
)
BEGIN
    UPDATE category
    SET name = p_name, status = p_status
    WHERE id = p_id;
END //
DELIMITER ;
# đổi trạng thái danh mục:

DELIMITER //
CREATE PROCEDURE PROC_CHANGE_STATUS_CATEGORY(
    IN p_id INT
)
BEGIN
    UPDATE category set status=not status WHERE id = p_id;
END //
DELIMITER ;
#oder by esc
# drop PROCEDURE PROC_ORDER_BY_CATEGORY;
DELIMITER //
CREATE PROCEDURE PROC_ORDER_BY_CATEGORY(
    in _name varchar(255)
)
BEGIN
    SELECT *FROM category order by + _name+ 'desc'  ;
END //
DELIMITER ;

# findall
DELIMITER //
CREATE PROCEDURE PROC_FIND_ALL_CATEGORY(
)
BEGIN
    SELECT* FROM category;
END //
DELIMITER ;

#findByID

DELIMITER //
CREATE PROCEDURE PROC_FIND_BY_ID_CATEGORY(IN _id int
)
BEGIN
    SELECT* FROM category where id=_id;
END //
DELIMITER ;

# #phân trang
# DELIMITER //
# CREATE PROCEDURE PROC_PAGINATION_CATEGORY(IN limit_in int, IN current_page int, OUT total_page int)
# begin
#     DECLARE offset_page int;
#     SET offset_page = (current_page - 1) * limit_in;
#     SET total_page = CEIL((SELECT count(*) from category) / limit_in);
#     SELECT * FROM category LIMIT limit_in offset offset_page;
# end //
DELIMITER //

CREATE PROCEDURE PROC_PAGINATION_SEACH_CATEGORY(
    IN limit_in INT,
    IN current_page INT,
    IN search_keyword VARCHAR(255),  -- Tham số cho tìm kiếm
    OUT total_page INT
)
BEGIN
    DECLARE offset_page INT;
    SET offset_page = (current_page - 1) * limit_in;

    -- Đếm tổng số bản ghi theo điều kiện tìm kiếm
    SET total_page = CEIL((SELECT COUNT(*) FROM category WHERE name LIKE CONCAT('%', search_keyword, '%')) / limit_in);

    -- Lấy dữ liệu với điều kiện tìm kiếm và phân trang
    SELECT * FROM category
    WHERE name LIKE CONCAT('%', search_keyword, '%')
    LIMIT limit_in OFFSET offset_page;
END //

DELIMITER ;


#product

# Thêm một sản phẩm mới:

DELIMITER //
CREATE PROCEDURE PROC_CREATE_PRODUCT(
    IN p_category_id INT,
    IN p_name VARCHAR(255),
    IN p_description VARCHAR(255),
    IN p_price DOUBLE,
    in _url_image varchar(255),
    IN p_quantity INT
)
BEGIN
    INSERT INTO product (category_id, name, description, price,url_image, quantity)
    VALUES (p_category_id, p_name, p_description, p_price,_url_image, p_quantity);
END //
DELIMITER ;
# Cập nhật thông tin của một sản phẩm:


DELIMITER //
CREATE PROCEDURE PROC_UPDATE_PRODUCT(

    IN p_category_id INT,
    IN p_name VARCHAR(255),
    IN p_description VARCHAR(255),
    IN p_price DOUBLE,
    IN p_quantity INT,
    IN p_url varchar(255),
    IN p_status bit,
        IN p_id INT
)
BEGIN
    UPDATE product
    SET category_id = p_category_id,
        name = p_name,
        description = p_description,
        price = p_price,
        quantity = p_quantity,
        url_image=p_url,
        status = p_status
    WHERE id = p_id;
END //
DELIMITER ;
# Xóa một sản phẩm:


DELIMITER //
CREATE PROCEDURE PROC_DELETE_PRODUCT(
    IN p_id INT
)
BEGIN
    DELETE FROM product WHERE id = p_id;
END //
DELIMITER ;
#oder by esc
DELIMITER //
CREATE PROCEDURE PROC_ORDER_BY_PRODUCT(
in _name varchar(255)
)
BEGIN
    SELECT *FROM product where name=_name order by name asc  ;
END //
DELIMITER ;

#findall
DELIMITER //
CREATE PROCEDURE PROC_FIND_ALL_PRODUCT(
)
BEGIN
    SELECT *FROM product;
END //
DELIMITER ;

# findbyid

DELIMITER //
CREATE PROCEDURE PROC_FIND_BY_ID_PRODUCT(
    IN p_id INT
)
BEGIN
    SELECT *FROM product WHERE id = p_id;
END //
DELIMITER ;


#image
DELIMITER //
CREATE PROCEDURE PROC_CREATE_IMAGE(
    IN p_url VARCHAR(255),
    IN p_product_id INT
)
BEGIN
    INSERT INTO image (url, product_id)
    VALUES (p_url, p_product_id);
END //
DELIMITER ;
# Cập nhật thông tin của một hình ảnh:


DELIMITER //
CREATE PROCEDURE PROC_UPDATE_IMAGE(
    IN p_id INT,
    IN p_url VARCHAR(255),
    IN p_product_id INT
)
BEGIN
    UPDATE image
    SET url = p_url,
        product_id = p_product_id
    WHERE id = p_id;
END //
DELIMITER ;
# Xóa một hình ảnh:

DELIMITER //
CREATE PROCEDURE PROC_DELETE_IMAGE(
    IN p_id INT
)
BEGIN
    DELETE FROM image WHERE id = p_id;
END //
DELIMITER ;

#find_all
DELIMITER //
CREATE PROCEDURE PROC_FIND_ALL_IMAGE(
)
BEGIN
    SELECT * FROM image;
END //
DELIMITER ;

#find_by_id

DELIMITER //
CREATE PROCEDURE PROC_FIND_BY_ID_IMAGE(
    IN p_id INT
)
BEGIN
    SELECT *FROM image WHERE id = p_id;
END //
DELIMITER ;


#customer
DELIMITER //
CREATE PROCEDURE PROC_CREATE_CUSTOMER(
    IN p_name VARCHAR(255),
    IN p_email VARCHAR(255),
    IN p_address VARCHAR(255),
    IN p_phone VARCHAR(255),
    IN p_password VARCHAR(255)
)
BEGIN
    INSERT INTO customer (name, email, address, phone, password)
    VALUES (p_name, p_email, p_address, p_phone, p_password);
END //
DELIMITER ;
# Cập nhật thông tin của một khách hàng:

DELIMITER //
CREATE PROCEDURE PROC_UPDATE_CUSTOMER(
    IN p_id INT,
    IN p_name VARCHAR(255),
    IN p_email VARCHAR(255),
    IN p_address VARCHAR(255),
    IN p_phone VARCHAR(255),
    IN p_url VARCHAR(255)
)
BEGIN
    UPDATE customer
    SET name = p_name,
        email = p_email,
        address = p_address,
        phone = p_phone,
        image=p_url
        where id = p_id;
END //
DELIMITER ;

#block

DELIMITER //
CREATE PROCEDURE PROC_CHANGE_STATUS_CUSTOMER(
    IN p_id INT
)
BEGIN
    Update customer set cus_status=not cus_status WHERE id = p_id;
END //
DELIMITER ;

#phân quyền
DELIMITER //
CREATE PROCEDURE PROC_ROLE_CUSTOMER(
    IN p_id INT,_roles varchar(50)
)
BEGIN
    Update customer set roles=_roles WHERE id = p_id;
END //
DELIMITER ;

#find_by_all
DELIMITER //
CREATE PROCEDURE PROC_FIND_ALL_CUSTOMER(
)
BEGIN
    Select *from customer;
END //
DELIMITER ;

#find_by_email
DELIMITER //
CREATE PROCEDURE PROC_FIND_BY_EMAIL_CUSTOMER(
    IN p_email VARCHAR(255)
)
BEGIN
    Select *from customer WHERE email = p_email;
END //
DELIMITER ;
#find_by_id

DELIMITER //
CREATE PROCEDURE PROC_FIND_BY_ID_CUSTOMER(
    IN p_id INT
)
BEGIN
    Select *from customer WHERE id = p_id;
END //
DELIMITER ;


#orders

# Thêm một đơn hàng mới:

DELIMITER //
CREATE PROCEDURE PROC_CREATE_ORDERS(
    IN p_customer_id INT,
    IN p_total DOUBLE
)
BEGIN
    INSERT INTO orders (customer_id, total)
    VALUES (p_customer_id, p_total);
END //
DELIMITER ;

#update trạng thái
DELIMITER //
CREATE PROCEDURE PROC_CHANGE_STATUS_ORDERS(
    IN p_id INT,
    IN p_order_status BIT
)
BEGIN
    UPDATE orders
    SET
        order_status = p_order_status
    WHERE id = p_id;
END //
DELIMITER ;

# FIND_ALL:

DELIMITER //
CREATE PROCEDURE PROC_FIND_ALL_ORDERS(
)
BEGIN
    SELECT *FROM orders;
END //
#FIND_BY_ID
DELIMITER //
CREATE PROCEDURE PROC_FIND_BY_ID_ORDERS(
    IN p_id INT
)
BEGIN
    SELECT *FROM orders WHERE id = p_id;
END //


#ORDER_DETAIL

DELIMITER //
CREATE PROCEDURE PROC_CREATE_ORDER_DETAIL(
    IN p_order_id INT,
    IN p_product_id INT,
    IN p_quantity INT,
    IN p_price DOUBLE
)
BEGIN
    INSERT INTO order_detail (order_id, product_id, quantity, price)
    VALUES (p_order_id, p_product_id, p_quantity, p_price);
END //
DELIMITER ;
# Update order detail information:

DELIMITER //
CREATE PROCEDURE PROC_UPDATE_ORDER_DETAIL(
    IN p_order_id INT,
    IN p_product_id INT,
    IN p_quantity INT,
    IN p_price DOUBLE
)
BEGIN
    UPDATE order_detail
    SET quantity = p_quantity,
        price = p_price
    WHERE order_id = p_order_id AND product_id = p_product_id;
END //
DELIMITER ;
# Delete an order detail:

DELIMITER //
CREATE PROCEDURE PROC_DELETE_ORDER_DETAIL(
    IN p_order_id INT,
    IN p_product_id INT
)
BEGIN
    DELETE FROM order_detail WHERE order_id = p_order_id AND product_id = p_product_id;
END //
DELIMITER ;

#find_all
DELIMITER //
CREATE PROCEDURE PROC_FIND_ALL_ORDER_DETAIL(
)
BEGIN
    SELECT * FROM order_detail;
END //
DELIMITER ;

#cart

DELIMITER //
CREATE PROCEDURE PROC_CREATE_CART(
    IN p_customer_id INT
)
BEGIN
    INSERT INTO cart (customer_id)
    VALUES (p_customer_id);
END //
DELIMITER ;
# Update cart information:

DELIMITER //
CREATE PROCEDURE PROC_UPDATE_CART(
    IN p_cart_id INT,
    IN p_customer_id INT
)
BEGIN
    UPDATE cart
    SET customer_id = p_customer_id
    WHERE id = p_cart_id;
END //
DELIMITER ;
# Delete a cart entry:
DELIMITER //
CREATE PROCEDURE PROC_DELETE_CART(
    IN p_cart_id INT
)
BEGIN
    DELETE FROM cart WHERE id = p_cart_id;
END //
DELIMITER ;

#FINALL
DELIMITER //
CREATE PROCEDURE PROC_FIND_ALL_CART(
)
BEGIN
    SELECT * FROM cart;
END //
DELIMITER ;

#find_by_id

DELIMITER //
CREATE PROCEDURE PROC_FIND_BY_ID_CART(
    IN p_cart_id INT
)
BEGIN
    SELECT *FROM cart WHERE id = p_cart_id;
END //
DELIMITER ;



#cart_item
DELIMITER //
CREATE PROCEDURE PROC_CREATE_CART_ITEM(
    IN p_cart_id INT,
    IN p_product_id INT,
    IN p_price DOUBLE,
    IN p_quantity INT
)
BEGIN
    INSERT INTO cart_item (cart_id, product_id, price, quantity)
    VALUES (p_cart_id, p_product_id, p_price, p_quantity);
END //
DELIMITER ;
# Update cart item

DELIMITER //
CREATE PROCEDURE PROC_UPDATE_CART_ITEM(
    IN p_item_id INT,
    IN p_cart_id INT,
    IN p_product_id INT,
    IN p_price DOUBLE,
    IN p_quantity INT
)
BEGIN
    UPDATE cart_item
    SET cart_id = p_cart_id,
        product_id = p_product_id,
        price = p_price,
        quantity = p_quantity
    WHERE id = p_item_id;
END //
DELIMITER ;
# Delete an item

DELIMITER //
CREATE PROCEDURE PROC_DELETE_CART_ITEM(
    IN p_item_id INT
)
BEGIN
    DELETE FROM cart_item WHERE id = p_item_id;
END //
DELIMITER ;


-- Lấy tất cả mục giỏ hàng
DELIMITER //
CREATE PROCEDURE PROC_FIND_ALL_CART_ITEM()
BEGIN
    SELECT * FROM cart_item;
END; //


-- Lấy mục giỏ hàng theo ID
DELIMITER //
CREATE PROCEDURE PROC_FIND_BY_ID_CART_ITEM(
    IN cart_item_id INT
)
BEGIN
    SELECT * FROM cart_item WHERE id = cart_item_id;
END; //