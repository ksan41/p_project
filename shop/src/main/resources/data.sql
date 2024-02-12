
-- test data
insert into 
  manager (
    name, 
    password, 
    phone
  )
values
  (
    "홍길동", 
    "ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f", 
    "01012345678"
  );

insert into product (product_id, barcode, category, cost_price, expiration_date, price, product_name, size, manager_id) values (1, '201402102690', '커피', 1830, null, 4694, '유자차', 'large', '01012345678');
insert into product (product_id, barcode, category, cost_price, expiration_date, price, product_name, size, manager_id) values (2, '201402109854', '음료', 2378, null, 4684, '아이스티', 'large', '01012345678');
insert into product (product_id, barcode, category, cost_price, expiration_date, price, product_name, size, manager_id) values (3, '201402103648', '커피', 1393, '2023-09-10 06:20:49', 4041, '카페라떼', 'small', '01012345678');
insert into product (product_id, barcode, category, cost_price, expiration_date, price, product_name, size, manager_id) values (4, '201402107028', '음료', 1642, '2023-08-14 04:45:38', 4135, '자몽에이드', 'small', '01012345678');
insert into product (product_id, barcode, category, cost_price, expiration_date, price, product_name, size, manager_id) values (5, '201402108354', '음료', 2708, '2023-04-08 18:35:22', 6862, '유자차', 'large', '01012345678');
insert into product (product_id, barcode, category, cost_price, expiration_date, price, product_name, size, manager_id) values (6, '201402108392', '차', 1008, null, 5794, '밀크쉐이크', 'large', '01012345678');
insert into product (product_id, barcode, category, cost_price, expiration_date, price, product_name, size, manager_id) values (7, '201402103528', '커피', 2336, null, 5648, '망고스무디', 'large', '01012345678');
insert into product (product_id, barcode, category, cost_price, expiration_date, price, product_name, size, manager_id) values (8, '201402102142', '음료', 2363, '2023-10-24 08:44:21', 5560, '아메리카노', 'large', '01012345678');
insert into product (product_id, barcode, category, cost_price, expiration_date, price, product_name, size, manager_id) values (9, '201402109641', '차', 1613, '2023-10-14 17:29:35', 4583, '자몽에이드', 'large', '01012345678');
insert into product (product_id, barcode, category, cost_price, expiration_date, price, product_name, size, manager_id) values (10, '201402101156', '차', 1552, '2023-08-04 17:35:42', 5651, '자몽에이드', 'large', '01012345678');
insert into product (product_id, barcode, category, cost_price, expiration_date, price, product_name, size, manager_id) values (11, '201402103788', '커피', 1381, null, 4232, '카푸치노', 'large', '01012345678');
insert into product (product_id, barcode, category, cost_price, expiration_date, price, product_name, size, manager_id) values (12, '201402105864', '차', 1359, '2023-10-21 09:02:51', 5882, '카푸치노', 'large', '01012345678');
insert into product (product_id, barcode, category, cost_price, expiration_date, price, product_name, size, manager_id) values (13, '201402103000', '커피', 2286, '2023-11-11 03:12:02', 4063, '아이스티', 'large', '01012345678');
insert into product (product_id, barcode, category, cost_price, expiration_date, price, product_name, size, manager_id) values (14, '201402102127', '차', 2036, '2023-05-28 00:01:58', 5543, '레몬차', 'small', '01012345678');
insert into product (product_id, barcode, category, cost_price, expiration_date, price, product_name, size, manager_id) values (15, '201402104015', '음료', 1593, null, 4100, '카푸치노', 'small', '01012345678');
insert into product (product_id, barcode, category, cost_price, expiration_date, price, product_name, size, manager_id) values (16, '201402109829', '커피', 2306, null, 5497, '유자차', 'large', '01012345678');
insert into product (product_id, barcode, category, cost_price, expiration_date, price, product_name, size, manager_id) values (17, '201402108967', '커피', 1857, null, 4180, '아이스티', 'large', '01012345678');
insert into product (product_id, barcode, category, cost_price, expiration_date, price, product_name, size, manager_id) values (18, '201402101714', '커피', 1576, null, 6901, '카푸치노', 'large', '01012345678');
insert into product (product_id, barcode, category, cost_price, expiration_date, price, product_name, size, manager_id) values (19, '201402108127', '음료', 2859, '2023-08-19 08:35:53', 6425, '레몬차', 'large', '01012345678');
insert into product (product_id, barcode, category, cost_price, expiration_date, price, product_name, size, manager_id) values (20, '201402101498', '차', 1457, null, 5390, '망고스무디', 'small', '01012345678');
insert into product (product_id, barcode, category, cost_price, expiration_date, price, product_name, size, manager_id) values (21, '201402104964', '커피', 1511, null, 6853, '자몽에이드', 'small', '01012345678');
insert into product (product_id, barcode, category, cost_price, expiration_date, price, product_name, size, manager_id) values (22, '201402106625', '음료', 1269, '2024-02-02 15:47:20', 6227, '자몽에이드', 'small', '01012345678');
insert into product (product_id, barcode, category, cost_price, expiration_date, price, product_name, size, manager_id) values (23, '201402107175', '커피', 2252, null, 6841, '아이스티', 'large', '01012345678');
insert into product (product_id, barcode, category, cost_price, expiration_date, price, product_name, size, manager_id) values (24, '201402104488', '음료', 1781, null, 4571, '망고스무디', 'large', '01012345678');
insert into product (product_id, barcode, category, cost_price, expiration_date, price, product_name, size, manager_id) values (25, '201402107573', '차', 2050, '2023-04-11 19:33:32', 4120, '레몬차', 'large', '01012345678');
insert into product (product_id, barcode, category, cost_price, expiration_date, price, product_name, size, manager_id) values (26, '201402106915', '차', 1811, '2023-05-22 06:10:08', 4469, '카푸치노', 'large', '01012345678');
insert into product (product_id, barcode, category, cost_price, expiration_date, price, product_name, size, manager_id) values (27, '201402103753', '음료', 1989, '2023-08-07 19:20:27', 5859, '망고스무디', 'large', '01012345678');
insert into product (product_id, barcode, category, cost_price, expiration_date, price, product_name, size, manager_id) values (28, '201402107008', '차', 1791, '2024-01-22 13:00:24', 5927, '밀크쉐이크', 'small', '01012345678');
insert into product (product_id, barcode, category, cost_price, expiration_date, price, product_name, size, manager_id) values (29, '201402107127', '커피', 2528, '2023-05-09 00:53:03', 5674, '레몬차', 'small', '01012345678');
insert into product (product_id, barcode, category, cost_price, expiration_date, price, product_name, size, manager_id) values (30, '201402103936', '차', 1798, null, 5126, '자몽에이드', 'small', '01012345678');
