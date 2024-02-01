CREATE TABLE member
(
    MEMBER_ID  BIGINT primary key AUTO_INCREMENT,
    EMAIL VARCHAR (50) not null ,
    PASSWORD TEXT not null,
    NAME VARCHAR (30) not null ,
    PHONE varchar(500) not null ,
    ADDRESS text not null,
    REPORT_COUNT BIGINT);

CREATE TABLE member_roles (
                              id  BIGINT AUTO_INCREMENT PRIMARY KEY,
                              member_member_id BIGINT,
                              roles VARCHAR(255),
                              FOREIGN KEY (member_member_id) REFERENCES member(member_id)
);

CREATE TABLE board (

                       BOARD_ID BIGINT PRIMARY KEY AUTO_INCREMENT,
                       TITLE VARCHAR(100) NOT NULL,
                       MEMBER_ID BIGINT NOT NULL,
                       CONTENT VARCHAR(1000),
                       VIEW_COUNT BIGINT,
                       LIKE_COUNT BIGINT,
                       BOARD_CATEGORY VARCHAR(100) NOT NULL,
                       CREATED_AT TIMESTAMP,
                       LAST_MODIFIED_AT TIMESTAMP,
                       FOREIGN KEY (MEMBER_ID) REFERENCES member(MEMBER_ID)
);

CREATE TABLE comment (
                         COMMENT_ID BIGINT PRIMARY KEY AUTO_INCREMENT,
                         BOARD_ID BIGINT NOT NULL,
                         MEMBER_ID BIGINT NOT NULL,
                         CONTENT VARCHAR(1000),
                         LIKE_COUNT BIGINT,
                         CREATED_AT TIMESTAMP,
                         LAST_MODIFIED_AT TIMESTAMP,
                         FOREIGN KEY (BOARD_ID) REFERENCES board(BOARD_ID),
                         FOREIGN KEY (MEMBER_ID) REFERENCES member(MEMBER_ID)
);

CREATE TABLE item (
                      ITEM_ID BIGINT PRIMARY KEY AUTO_INCREMENT,
                      NAME VARCHAR(100),
                      PRICE BIGINT,
                      DETAIL VARCHAR(100),
                      STOCK BIGINT,
                      COLOR VARCHAR(100),
                      BRAND VARCHAR(100),
                      CATEGORY VARCHAR(100),
                      CREATED_AT TIMESTAMP,
                      LAST_MODIFIED_AT TIMESTAMP
);

CREATE TABLE cart (
                      CART_ID BIGINT PRIMARY KEY AUTO_INCREMENT,
                      MEMBER_ID BIGINT NOT NULL,
                      CREATED_AT TIMESTAMP,
                      LAST_MODIFIED_AT TIMESTAMP,
                      FOREIGN KEY (MEMBER_ID) REFERENCES member(MEMBER_ID)
);

CREATE TABLE review (
                        REVIEW_ID BIGINT PRIMARY KEY AUTO_INCREMENT,
                        ITEM_ID BIGINT NOT NULL,
                        MEMBER_ID BIGINT NOT NULL,
                        CONTENT VARCHAR(1000),
                        LIKE_COUNT  BIGINT,
                        UNLIKE_COUNT  BIGINT,
                        SCORE BIGINT,
                        CREATED_AT TIMESTAMP,
                        LAST_MODIFIED_AT TIMESTAMP,
                        FOREIGN KEY (ITEM_ID) REFERENCES item(ITEM_ID),
                        FOREIGN KEY (MEMBER_ID) REFERENCES member(MEMBER_ID)
);

create table user_vote
(
    vote_id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    member_id        BIGINT,
    review_id        BIGINT,
    foreign key (member_id) references member (member_id),
    foreign key (review_id) references review (review_id),
    user_like           boolean,
    unlike boolean,
    expiration_date timestamp,
    created_at        timestamp,
    last_modified_at timestamp
);

CREATE TABLE cart_item (
                           CART_ITEM_ID BIGINT PRIMARY KEY AUTO_INCREMENT,
                           COUNT BIGINT NOT NULL,
                           CART_ID BIGINT NOT NULL,
                           ITEM_ID BIGINT NOT NULL,
                           CREATED_AT TIMESTAMP,
                           LAST_MODIFIED_AT TIMESTAMP,
                           FOREIGN KEY (CART_ID) REFERENCES cart(CART_ID),
                           FOREIGN KEY (ITEM_ID) REFERENCES item(ITEM_ID)
);

CREATE TABLE complain (
                          COMPLAIN_ID BIGINT PRIMARY KEY AUTO_INCREMENT,
                          MEMBER_ID BIGINT NOT NULL,
                          ITEM_ID BIGINT NOT NULL,
                          title VARCHAR(20),
                          CONTENT VARCHAR(1000),
                          CREATED_AT TIMESTAMP,
                          LAST_MODIFIED_AT TIMESTAMP,
                          COMPLAIN_STATUS VARCHAR(255),
                          FOREIGN KEY (MEMBER_ID) REFERENCES member(MEMBER_ID),
                          FOREIGN KEY (ITEM_ID) REFERENCES item(ITEM_ID)
);

CREATE TABLE MESSAGE (
                         MESSAGE_ID BIGINT PRIMARY KEY AUTO_INCREMENT,
                         CONTENT VARCHAR(1000),
                         CREATED_AT TIMESTAMP,
                         SENDER_MEMBER_ID BIGINT NOT NULL,
                         RESPONSE_MEMBER_ID BIGINT NOT NULL,
                         FOREIGN KEY (SENDER_MEMBER_ID) REFERENCES MEMBER(MEMBER_ID),
                         FOREIGN KEY (RESPONSE_MEMBER_ID) REFERENCES MEMBER(MEMBER_ID)
);
CREATE TABLE delivery_info (
                               delivery_Id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               address varchar(250),
                               phone varchar(250),
                               recipient varchar(250),
                               order_id BIGINT,
                               CREATED_AT TIMESTAMP,
                               LAST_MODIFIED_AT TIMESTAMP

);

CREATE TABLE pay_info (
                          payment_Id bigint AUTO_INCREMENT PRIMARY KEY,
                         tid varchar(250),
                          order_id BIGINT
);

CREATE TABLE orders (
                        order_id BIGINT UNIQUE PRIMARY KEY ,
                        status BIGINT,
                        member_id BIGINT,
                        FOREIGN KEY (member_id) REFERENCES member (member_id),
                        delivery_id BIGINT,
                        foreign key (delivery_id) references delivery_info (delivery_id),
                        cart_id BIGINT,
                        foreign key (cart_id) references cart(cart_id),
                        payment_id bigint,
                        foreign key (payment_id) references pay_info(payment_id),
                        CREATED_AT TIMESTAMP,
                        LAST_MODIFIED_AT TIMESTAMP
);
create table order_item (
                            order_item_id bigint auto_increment primary key ,
                            item_id bigint,
                            order_id bigint,
                            foreign key (order_id) references orders (order_id),
                            count bigint,
                            foreign key (item_id) references item (item_id),
                            CREATED_AT TIMESTAMP,
                            LAST_MODIFIED_AT TIMESTAMP
);



create table item_image (
    item_image_id bigint auto_increment primary key,
    item_id bigint,
    image_name varchar(500),
    original_name varchar(500),
    path varchar(500),
    representation_image varchar(3),
    created_at timestamp,
    last_modified_at timestamp,
    foreign key (item_id) references item(item_id)
);
create table review_image (
                            review_image_id bigint auto_increment primary key,
                            review_id bigint,
                            image_name varchar(500),
                            original_name varchar(500),
                            path varchar(500),
                            representation_image varchar(3),
                            created_at timestamp,
                            last_modified_at timestamp,
                            foreign key (review_id) references review(review_id)
);

