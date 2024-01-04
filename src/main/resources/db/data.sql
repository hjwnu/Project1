
INSERT INTO member VALUES (101, 'test1@test.com', '{noop}test1', '테스트1', '010-1234-5678', '서울시 ㅇㅇ구 ㅇㅇ동', 37);
INSERT INTO member VALUES (102, 'test2@test.com','{noop}test2',  '테스트2', '010-7654-3210', '경기도 xx시 xx구 xx동', 0);
INSERT INTO member VALUES  (103, 'test3@test.com','{noop}test3',  '테스트3', '010-7777-7777', '인천시 ㅁㅁ구 ㅁㅁ동', 5);
INSERT INTO member VALUES  (104, 'admin@gmail.com','{noop}1234',  '관리자', '010-5325-5325', '인천시 ㅁㅁ구 ㅁㅁ동', 5);
INSERT INTO member_roles VALUES  (101, 104, 'ADMIN');
INSERT INTO member_roles VALUES  (102, 104, 'USER');
INSERT INTO board VALUES (101, '첫 번째 게시물', 101, '첫 번째 게시물 내용입니다.', 100, 50, '자유게시판', NOW(),NOW());
INSERT INTO board VALUES (102, '두 번째 게시물', 102, '두 번째 게시물 내용입니다.', 120, 60, '스터디모집', NOW(),NOW());
INSERT INTO board VALUES (104, '공지사항 제목1', 101, '공지사항 내용입니다. 첫 번째 공지사항입니다.', 0, 0, '공지사항', NOW(), NOW());
INSERT INTO board VALUES (105, '공지사항 제목2', 102, '공지사항 내용입니다. 두 번째 공지사항입니다.', 0, 0, '공지사항', NOW(), NOW());

INSERT INTO comment VALUES (101, 101,101, '첫 번째 게시물에 대한 댓글입니다.', 31, NOW(),NOW());
INSERT INTO comment VALUES (102, 101, 102, '첫 번째 게시물에 대한 댓글입니다.', 7, NOW(),NOW());
INSERT INTO comment VALUES (103, 102, 103, '두 번째 게시물에 대한 댓글입니다.', 0, NOW(),NOW());
INSERT INTO comment VALUES (104, 102, 101, '두 번째 게시물에 대한 댓글입니다.', 54, NOW(),NOW());

INSERT INTO ITEM VALUES (101, '로지텍 키보드', 150000, '키보드', 0, 'WHITE',  'Logitec', 'Keyboard',NOW(),NOW());
INSERT INTO ITEM VALUES (102, '맥북 PRO 13인치', 2500000, 'RAM 16GB', 11, 'SPACE GRAY',  'APPLE', 'Notebook',NOW(),NOW());
INSERT INTO ITEM VALUES (103, '로지텍 마우스', 100000, '로지텍 마우스', 200, 'BLACK',  'Logitec', 'Mouse',NOW(),NOW());
INSERT INTO ITEM VALUES (104, '매직 마우스', 100000, '매직 마우스', 200, 'BLACK',  'APPLE', 'Mouse',NOW(),NOW());
INSERT INTO ITEM VALUES (105, '로지텍 키보드2', 150000, '키보드', 0, 'WHITE',  'Logitec', 'Keyboard',NOW(),NOW());
INSERT INTO ITEM VALUES (106, '맥북 PRO 13인치2', 2500000, 'RAM 16GB', 11, 'SPACE GRAY',  'APPLE', 'Notebook',NOW(),NOW());
INSERT INTO ITEM VALUES (107, '로지텍 마우스2', 100000, '로지텍 마우스', 200, 'BLACK',  'Logitec', 'Mouse',NOW(),NOW());
INSERT INTO ITEM VALUES (108, '매직 마우스2', 100000, '매직 마우스', 200, 'BLACK',  'APPLE', 'Mouse',NOW(),NOW());
INSERT INTO ITEM VALUES (109, '로지텍 키보드3', 150000, '키보드', 0, 'WHITE',  'Logitec', 'Keyboard',NOW(),NOW());
INSERT INTO ITEM VALUES (110, '맥북 PRO 13인치3', 2500000, 'RAM 16GB', 11, 'SPACE GRAY',  'APPLE', 'Notebook',NOW(),NOW());
INSERT INTO ITEM VALUES (111, '로지텍 마우스3', 100000, '로지텍 마우스', 200, 'BLACK',  'Logitec', 'Mouse',NOW(),NOW());
INSERT INTO ITEM VALUES (112, '매직 마우스3', 100000, '매직 마우스', 200, 'BLACK',  'APPLE', 'Mouse',NOW(),NOW());


INSERT INTO cart VALUES (101, 101,NOW(),NOW());
INSERT INTO cart  VALUES (102, 102,NOW(),NOW());

INSERT INTO cart_item VALUES (101, 2, 101, 101,NOW(),NOW());
INSERT INTO cart_item VALUES (102, 1, 102, 102,NOW(),NOW());

INSERT INTO complain VALUES (101, 101, 101, '제목','상품에 이상이 있습니다.',NOW(),NOW(),'COMPLAIN_EXIST');
INSERT INTO complain VALUES (102, 101, 103, '제목','상품이 파손되었습니다.',NOW(),NOW(),'COMPLAIN_NOT_EXIST');
INSERT INTO complain VALUES (103, 102, 102, '제목','이거 언제 입고 되나요?',NOW(),NOW(),'COMPLAIN_EXIST');

INSERT INTO message VALUES (101, '안녕하세요. 문의드립니다.', NOW(), 101,102);
INSERT INTO message VALUES (102, '안녕하세요. 답변드립니다.', NOW(), 102, 101);

insert into delivery_info values (101, '주소' , '전화번호', '받는 분',101,now(),now() );

INSERT INTO orders  VALUES (202309120001, 1,101, 101, 101,null,NOW(),NOW());
INSERT INTO orders VALUES (202309130002, 1,102,101,101,null,NOW(),NOW());
INSERT INTO orders VALUES (202309140001, 1,103,101,101,null,NOW(),NOW());

insert into order_item values(101, 101, 202309120001, 3);
insert into order_item values(102, 102, 202309130002, 3);
insert into order_item values(103, 103, 202309140001, 3);

INSERT INTO REVIEW VALUES (101, 101, 101, '좋은 상품입니다.', 3, 5, 4,NOW(), NOW());
INSERT INTO REVIEW VALUES (102, 102, 101, '키보드 별로에요',14, 3, 4,NOW(), NOW());
INSERT INTO REVIEW VALUES (103, 103, 101, '맥북 사용해보니 좋아요.', 33, 7, 3,NOW(), NOW());
INSERT INTO REVIEW VALUES (104, 104, 101, '좋은 상품입니다.', 3171, 51, 1,NOW(), NOW());
INSERT INTO REVIEW VALUES (105, 105, 101, '마우스 별로에요',33, 3400, 5,NOW(), NOW());
INSERT INTO REVIEW VALUES (106, 106, 101, '좋은 상품입니다.', 3, 5, 4,NOW(), NOW());
INSERT INTO REVIEW VALUES (107, 107, 101, '키보드 별로에요',14, 3, 4,NOW(), NOW());
INSERT INTO REVIEW VALUES (108, 108, 101, '맥북 사용해보니 좋아요.', 33, 7, 3,NOW(), NOW());
INSERT INTO REVIEW VALUES (109, 109, 101, '좋은 상품입니다.', 3171, 51, 1,NOW(), NOW());
INSERT INTO REVIEW VALUES (110, 110, 101, '마우스 별로에요',33, 3400, 5,NOW(), NOW());
INSERT INTO REVIEW VALUES (111, 112, 101, '좋은 상품입니다.', 3, 5, 4,NOW(), NOW());
INSERT INTO REVIEW VALUES (112, 111, 101, '키보드 별로에요',14, 3, 4,NOW(), NOW());
INSERT INTO REVIEW VALUES (113, 112, 101, '맥북 사용해보니 좋아요.', 33, 7, 3,NOW(), NOW());
INSERT INTO REVIEW VALUES (114, 103, 101, '좋은 상품입니다.', 3171, 51, 1,NOW(), NOW());
INSERT INTO REVIEW VALUES (115, 103, 101, '마우스 별로에요',33, 3400, 5,NOW(), NOW());

INSERT INTO pay_info VALUES (1, 'PaymentIdentifier123', 202309140001);


