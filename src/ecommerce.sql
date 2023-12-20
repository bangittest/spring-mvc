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
                        description varchar(255) ,
                        price double,
                        url_image varchar(255),
                        stock int,
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
                        email VARCHAR(255) NOT NULL,
                        full_name VARCHAR(255) NOT NULL,
                        address VARCHAR(255) NOT NULL,
                        phone VARCHAR(255) NOT NULL,
                        notes TEXT,
                        order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        order_status int default 0,
                        total DOUBLE NOT NULL,
                        FOREIGN KEY (customer_id) REFERENCES customer(id)
);


create table order_detail(
                             order_id int not null,
                             foreign key (order_id) references orders(id),
                             product_id int not null,
                             foreign key (product_id) references product(id),
                             quantity int NOT NULL,
                             price double NOT NULL
);

create table cart(
                     id int primary key NOT NULL auto_increment,
                     customer_id int not null,
                     foreign key (customer_id) references customer(id)
);

create table cart_item(
                          cart_id int not null,
                          foreign key (cart_id) references cart(id),
                          product_id int not null,
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



DELIMITER //
CREATE PROCEDURE PROC_FIND_CATEGORY_ID_CATEGORY(
    IN p_id INT
)
BEGIN
    Select * from product where category_id=p_id;
END //
DELIMITER ;

#oder by esc
# drop PROCEDURE PROC_ORDER_BY_CATEGORY;
# DELIMITER //
#
# CREATE PROCEDURE PROC_ORDER_BY_CATEGORY(
#     IN _name VARCHAR(255)
# )
# BEGIN
#     SET @query = CONCAT('SELECT * FROM category ORDER BY ', _name, ' DESC');
#     PREPARE stmt FROM @query;
#     EXECUTE stmt;
#     DEALLOCATE PREPARE stmt;
# END //
#
# DELIMITER ;

# drop  PROCEDURE PROC_ORDER_BY_CATEGORY;
DELIMITER //

CREATE PROCEDURE PROC_ORDER_BY_CATEGORY(
    IN _columnName VARCHAR(255),
    IN _sortDirection VARCHAR(4)
)
BEGIN
    SET @sql = CONCAT('SELECT * FROM category ORDER BY ', _columnName, ' ', _sortDirection);
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
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

DELIMITER //
CREATE PROCEDURE PROC_FIND_BY_NAME_CATEGORY(IN p_name VARCHAR(255)
)
BEGIN
    SELECT* FROM category where name=p_name;
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
# phân trang tìm kiếm sắp xếp;
# drop PROCEDURE PROC_PAGINATION_SEARCH_SORT_CATEGORY;

# DELIMITER //
#
# CREATE PROCEDURE PROC_PAGINATION_SEARCH_SORT_CATEGORY(
#     IN limit_in INT,
#     IN current_page INT,
#     IN search_keyword VARCHAR(255),  -- Tham số cho tìm kiếm
#     IN sort_column VARCHAR(255),     -- Tham số cho tên cột sắp xếp
#     IN sort_direction VARCHAR(4),    -- Tham số cho hướng sắp xếp: 'ASC' hoặc 'DESC'
#     OUT total_page INT
# )
# BEGIN
#     DECLARE offset_page INT;
#     SET offset_page = (current_page - 1) * limit_in;
#
#     -- Đếm tổng số bản ghi theo điều kiện tìm kiếm
#     SET total_page = CEIL((SELECT COUNT(*) FROM category WHERE name LIKE CONCAT('%', search_keyword, '%')) / limit_in);
#
#     -- Lấy dữ liệu với điều kiện tìm kiếm, phân trang và sắp xếp
#     SET @query = CONCAT('SELECT * FROM category WHERE name LIKE ''%', search_keyword, '%'' ORDER BY ', sort_column, ' ', sort_direction, ' LIMIT ', limit_in, ' OFFSET ', offset_page);
#
#     -- In ra giá trị của @query để kiểm tra cú pháp câu truy vấn
#     SELECT @query;
#
#     PREPARE stmt FROM @query;
#     EXECUTE stmt;
#     DEALLOCATE PREPARE stmt;
# END //
#
# DELIMITER ;



# tìm kiếm phân trang
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

DELIMITER //
CREATE PROCEDURE PROC_GET_LAST_PRODUCT()
BEGIN
    SELECT * FROM product
    ORDER BY id DESC
    LIMIT 5;
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE PROC_FIND_BY_NAME_PRODUCT(IN p_name VARCHAR(255)
)
BEGIN
    SELECT* FROM product where name=p_name;
END //
DELIMITER ;

# Thêm một sản phẩm mới:
# drop PROCEDURE PROC_CREATE_PRODUCT

DELIMITER //
CREATE PROCEDURE PROC_CREATE_PRODUCT(
    IN p_category_id INT,
    IN p_name VARCHAR(255),
    IN p_description VARCHAR(255),
    IN p_price DOUBLE,
    in _url_image varchar(255),
    IN p_stock INT,
    OUT _productId INT
)
BEGIN
    INSERT INTO product (category_id, name, description, price,url_image, stock)
    VALUES (p_category_id, p_name, p_description, p_price,_url_image, p_stock);

    -- Lấy ID của sản phẩm vừa được thêm
    SELECT LAST_INSERT_ID() INTO _productId;
END //
DELIMITER ;
# Cập nhật thông tin của một sản phẩm:

# drop PROCEDURE PROC_UPDATE_PRODUCT

DELIMITER //
CREATE PROCEDURE PROC_UPDATE_PRODUCT(

    IN p_category_id INT,
    IN p_name VARCHAR(255),
    IN p_description VARCHAR(255),
    IN p_price DOUBLE,
    IN p_stock INT,
    IN p_url varchar(255),
    IN p_status bit(1),
        IN p_id INT
)
BEGIN
    UPDATE product
    SET category_id = p_category_id,
        name = p_name,
        description = p_description,
        price = p_price,
        stock = p_stock,
        url_image=p_url,
        status = p_status
    WHERE id = p_id;
END //
DELIMITER ;


# tìm kiếm phân trang
DELIMITER //

CREATE PROCEDURE PROC_PAGINATION_SEACH_PRODUCT(
    IN limit_in INT,
    IN current_page INT,
    IN search_keyword VARCHAR(255),  -- Tham số cho tìm kiếm
    OUT total_page INT
)
BEGIN
    DECLARE offset_page INT;
    SET offset_page = (current_page - 1) * limit_in;

    -- Đếm tổng số bản ghi theo điều kiện tìm kiếm
    SET total_page = CEIL((SELECT COUNT(*) FROM product WHERE name LIKE CONCAT('%', search_keyword, '%')) / limit_in);

    -- Lấy dữ liệu với điều kiện tìm kiếm và phân trang
    SELECT * FROM product
    WHERE name LIKE CONCAT('%', search_keyword, '%')
    LIMIT limit_in OFFSET offset_page;
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


DELIMITER //
CREATE PROCEDURE PROC_CHANGE_STATUS_PRODUCT(
    IN p_id INT
)
BEGIN
    update product set status=not status WHERE id = p_id;
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




DELIMITER //

CREATE PROCEDURE PROC_ORDER_BY_PRODUCT_ALL(
    IN _columnName VARCHAR(255),
    IN _sortDirection VARCHAR(4)
)
BEGIN
    SET @sql = CONCAT('SELECT * FROM product ORDER BY ', _columnName, ' ', _sortDirection);
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
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

DELIMITER //
CREATE PROCEDURE PROC_DELETE_FOREIGN_IMAGE(
    IN p_id INT
)
BEGIN
    DELETE FROM image WHERE product_id = p_id;
END //
DELIMITER ;

# PROC_FIND_BY_PRODUCT_ID_IMAGE
DELIMITER //
CREATE PROCEDURE PROC_FIND_BY_PRODUCT_ID_IMAGE(
    IN p_id INT
)
BEGIN
    SELECT *FROM image WHERE product_id = p_id;
END //




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
DELIMITER ;
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

# DELIMITER //
# CREATE PROCEDURE PROC_CREATE_CART(
#     IN p_customer_id INT
# )
# BEGIN
#     INSERT INTO cart (customer_id)
#     VALUES (p_customer_id);
# END //
# DELIMITER ;
# drop PROCEDURE PROC_CREATE_CART;

DELIMITER //

CREATE PROCEDURE PROC_CREATE_CART(
    IN userId_in INT,
    OUT cartId_out INT
)
BEGIN
    -- Tạo mới giỏ hàng
    INSERT INTO cart (customer_id) VALUES (userId_in);

    -- Lấy cart_id của giỏ hàng mới tạo
    SELECT LAST_INSERT_ID() INTO cartId_out;
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

DELIMITER //
CREATE PROCEDURE PROC_DELETE_CART_ITEMS_ID(
    IN p_cart_id INT
)
BEGIN
    DELETE FROM cart_item WHERE cart_id = p_cart_id;
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

DELIMITER //
CREATE PROCEDURE PROC_FIND_BY_ID_CUSTOMER_CART(
    IN p_cart_id INT
)
BEGIN
    SELECT *FROM cart WHERE customer_id = p_cart_id;
END //
DELIMITER ;



#cart_item
DELIMITER //
CREATE PROCEDURE PROC_CREATE_CART_ITEM(
    IN p_cart_id INT,
    IN p_product_id INT,
    IN p_quantity INT
)
BEGIN
    INSERT INTO cart_item (cart_id, product_id, quantity)
    VALUES (p_cart_id, p_product_id, p_quantity);
END //
DELIMITER ;


# Update cart item


DELIMITER //
CREATE PROCEDURE PROC_UPDATE_CART_ITEM(
    IN p_cart_id INT,
    IN p_product_id INT,
    IN p_quantity INT
)
BEGIN
    UPDATE cart_item
    SET
        quantity = p_quantity
    WHERE cart_id=p_cart_id and product_id=p_product_id;
END //
DELIMITER ;
# Delete an item

DELIMITER //
CREATE PROCEDURE PROC_DELETE_CART_ITEM(
    IN p_item_id INT
)
BEGIN
    DELETE FROM cart_item WHERE product_id = p_item_id;
END //
DELIMITER ;


-- Lấy tất cả mục giỏ hàng
DELIMITER //
CREATE PROCEDURE PROC_FIND_ALL_CART_ITEM()
BEGIN
    SELECT * FROM cart_item;
END; //


-- Lấy mục giỏ hàng theo ID
# DELIMITER //
# CREATE PROCEDURE PROC_FIND_BY_ID_CART_ID_ITEM(
#     IN cart_item_id INT
# )
# BEGIN
#     SELECT * FROM cart_item WHERE id = cart_item_id;
# END; //


DELIMITER //

CREATE PROCEDURE PROC_GET_CART_ITEMS_BY_CART_ID(
    IN cartId_in INT
)
BEGIN
    SELECT * FROM cart_item WHERE cart_id = cartId_in;
END //

DELIMITER ;







DELIMITER //
CREATE PROCEDURE PROC_GET_CART_ITEMS_BY_CART_ID_AND_PRODUCT_ID(
    IN cartId_in INT,
    IN p_product_id int
)
BEGIN
    SELECT * FROM cart_item WHERE  cart_id = cartId_in and product_id=p_product_id;
END //

DELIMITER ;


DELIMITER //
CREATE PROCEDURE PROC_GET_CART_ITEMS_BY_PRODUCT_CART_ID(
    IN p_product_id int
)
BEGIN
    SELECT * FROM cart_item WHERE  product_id=p_product_id;
END //

DELIMITER ;








DELIMITER //
CREATE PROCEDURE PROC_CREATE_ORDER(
    IN _customer_id int ,
    in _email varchar(255),
    in _full_name varchar(255),
    in _address varchar(255),
     in _phone varchar(255),
     in _notes text,
    IN _total double,
    OUT _order_id INT
)
BEGIN
    INSERT INTO orders (customer_id,email,full_name,address,phone,notes,total)
    VALUES (_customer_id,_email,_full_name,_address,_phone,_notes,_total);

    -- Lấy ID của sản phẩm vừa được thêm
    SELECT LAST_INSERT_ID() INTO _order_id;
END //
DELIMITER ;

DELIMITER //
create procedure  PROC_CREATE_ORDER_DETAILS(in _order_id int ,in _product_id int,in _quantity int,_price double)
begin
    insert into order_detail(order_id, product_id, quantity, price) VALUES (_order_id, _product_id,_quantity,_price);
end //
DELIMITER //;


DELIMITER //
CREATE PROCEDURE PROC_FIND_ALL_ORDERS_AND_ORDER_DETAIL()
BEGIN
#     SELECT o.id,o.email,o.full_name,o.address,o.phone,o.notes,o.order_date,o.order_status,o.total,
#            od.product_id,od.quantity,od.price
#            FROM orders o,order_detail od WHERE o.id  = od.order_id;
SELECT o.id,o.email, o.full_name, o.address, o.phone, o.notes, o.order_date, o.total, o.order_status,
                     od.product_id, od.quantity, od.price
                     FROM orders o
                     LEFT JOIN order_detail od ON o.id = od.order_id;
END //

DELIMITER ;









DELIMITER //
create procedure  PROC_FIND_ALL_ORDER_DETAILS( in _order_id int)
begin
   select * from order_detail where order_id=_order_id;
end //
DELIMITER //;


DELIMITER //
create procedure  PROC_UPDATE_STOCK_PRODUCT(in _id int, in _stock int)
begin
    UPDATE product set stock=_stock where id =_id;
end //
DELIMITER //;

DELIMITER //
create procedure  PROC_FIND_BY_ID_ORDERS_(in _id int)
begin
    SELECT * from orders where id=_id;
end //
DELIMITER //;






DELIMITER //
create procedure  PROC_CHANGE_STATUS_ORDERS_(in _id int,_status int)
begin
    UPDATE orders set order_status=_status where id =_id;
end //
DELIMITER //;


DELIMITER //
CREATE PROCEDURE PROC_FIND_ALL_ORDERS_BY_ID( in _id int
)
BEGIN
    SELECT *FROM orders where customer_id=_id;
END //
DELIMITER ;



DELIMITER //
CREATE PROCEDURE PROC_FIND_ALL_ORDERS_BY_ID_ORDERS( in _id int
)
BEGIN
    SELECT *FROM orders where id=_id;
END //
DELIMITER ;




DELIMITER //
CREATE PROCEDURE PROC_FIND_ALL_CUSTOMER_CART_ID( in _id int
)
BEGIN
    SELECT *FROM cart where customer_id=_id;
END //
DELIMITER ;



DELIMITER //
CREATE PROCEDURE PROC_EDIT_PROFILE_CUSTOMER( in _id int,in _name varchar(255),in _email varchar(255),
in _address varchar(255),_phone varchar(255) ,_image varchar(255)
)
BEGIN
    UPDATE customer SET name=_name,email=_email, address=_address,phone=_phone,image=_image  where id=_id;
END //
DELIMITER ;















