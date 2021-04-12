/*
 Navicat Premium Data Transfer

 Source Server         : MySQL80
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : salted_fish

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 12/04/2021 16:53:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for evaluation
-- ----------------------------
DROP TABLE IF EXISTS `evaluation`;
CREATE TABLE `evaluation`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `content` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评价内容',
  `flower_id` bigint(0) NULL DEFAULT NULL COMMENT '评价的商品id',
  `order_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单id',
  `parent_id` bigint(0) NULL DEFAULT 0 COMMENT '父id',
  `to_user_id` bigint(0) NOT NULL COMMENT '被评论人Id',
  `to_user_type` int(0) NULL DEFAULT NULL COMMENT '被评论人类型(0:C端，1：B端）',
  `creator_id` bigint(0) NULL DEFAULT NULL COMMENT '评论人',
  `creator_type` int(0) NULL DEFAULT NULL COMMENT '创建者类型(0:C端，1：B端）',
  `created_time` datetime(0) NULL DEFAULT NULL,
  `updated_time` datetime(0) NULL DEFAULT NULL,
  `is_deleted` tinyint(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`, `to_user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '评价/评论' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_address
-- ----------------------------
DROP TABLE IF EXISTS `tb_address`;
CREATE TABLE `tb_address`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `uid` bigint(0) NULL DEFAULT NULL COMMENT '用户id',
  `consignee` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收件人',
  `consignee_phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收件人手机号',
  `province` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '省',
  `city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '市',
  `district` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '区',
  `detail` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '详细地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '地址' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_address
-- ----------------------------
INSERT INTO `tb_address` VALUES (1, 3, 'maple', '17986897562', '湖南省', '长沙市', '岳麓区', '科教新村709');
INSERT INTO `tb_address` VALUES (5, 2, 'sad', '15876899876', '河北省', '怀化市', '撒旦区', '阿萨的撒反对发598');
INSERT INTO `tb_address` VALUES (6, 1, 'maple', '18343743243', '四川省', '成都市', '阿萨区', '啊实打实的888');

-- ----------------------------
-- Table structure for tb_background_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_background_user`;
CREATE TABLE `tb_background_user`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `role` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'admin' COMMENT '角色',
  `avatar_image` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '/profile/upload/2021/03/10/dbced90a-9594-4aa7-b228-05a482c26937.png' COMMENT '头像',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_background_user
-- ----------------------------
INSERT INTO `tb_background_user` VALUES (1, 'maple', '123456', '18976618156', NULL, '/profile/upload/2021/04/12/aeb30bc8-adec-4cb9-b738-1b3f3427c007.jpg', '2021-03-10 15:51:21');
INSERT INTO `tb_background_user` VALUES (2, 'maple2', '123456', '17976618156', NULL, '/profile/upload/2021/03/10/dbced90a-9594-4aa7-b228-05a482c26937.png', '2021-03-10 16:06:24');
INSERT INTO `tb_background_user` VALUES (3, 'maple3', '123456', '16976618156', NULL, '/profile/upload/2021/03/10/dbced90a-9594-4aa7-b228-05a482c26937.png', '2021-03-15 14:35:11');
INSERT INTO `tb_background_user` VALUES (4, 'maple4', '123456', '15679876356', NULL, '/profile/upload/2021/03/10/dbced90a-9594-4aa7-b228-05a482c26937.png', '2021-03-15 14:45:52');
INSERT INTO `tb_background_user` VALUES (5, 'maple5', '123456', '16679876356', NULL, '/profile/upload/2021/03/10/dbced90a-9594-4aa7-b228-05a482c26937.png', '2021-03-15 14:46:08');
INSERT INTO `tb_background_user` VALUES (6, 'maple6', '123456', '15879876356', NULL, '/profile/upload/2021/03/10/dbced90a-9594-4aa7-b228-05a482c26937.png', '2021-03-15 14:46:24');

-- ----------------------------
-- Table structure for tb_cart
-- ----------------------------
DROP TABLE IF EXISTS `tb_cart`;
CREATE TABLE `tb_cart`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户id',
  `seller_id` bigint(0) NULL DEFAULT NULL COMMENT '卖家id',
  `background_agent_id` bigint(0) NULL DEFAULT NULL COMMENT '后台代理用户id',
  `sku_id` bigint(0) NULL DEFAULT NULL COMMENT '商品id',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品标题',
  `image` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片',
  `price` double NULL DEFAULT NULL COMMENT '价格 单位元',
  `num` int(0) NULL DEFAULT NULL COMMENT '数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '购物车' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_cart
-- ----------------------------
INSERT INTO `tb_cart` VALUES (10, 1, 1, NULL, 1, '一路上有你 [泰国进口] 真空玫瑰花，红玫瑰系列', '/profile/upload/2021/03/16/3ac4761a-619b-44d1-9889-dac25275185c.jpg', 368, 1);
INSERT INTO `tb_cart` VALUES (11, 1, 2, NULL, 3, '蝴蝶兰4株', '/profile/upload/2021/03/16/aefb92ae-6dd4-4510-baa3-a09144f794be.jpg', 388, 1);
INSERT INTO `tb_cart` VALUES (12, 1, 3, NULL, 8, '留住好时光  [精选昆明A级花材] 粉绣球1枝，粉雪山玫瑰6枝', '/profile/upload/2021/03/18/5e45d3f3-4e3c-416c-96af-0c2f71e0da29.jpg', 66, 1);
INSERT INTO `tb_cart` VALUES (13, 1, 2, NULL, 4, '吉祥如意', '/profile/upload/2021/03/16/d4832824-a70e-4704-8261-09b748868102.jpg', 218, 1);
INSERT INTO `tb_cart` VALUES (14, 1, 2, NULL, 2, '你是唯一 [11枝新品 一心一意的爱] 卡罗拉红玫瑰11枝', '/profile/upload/2021/03/16/2e691a23-9678-4bd4-bdbd-23bfeff3f5b8.jpg', 168, 1);

-- ----------------------------
-- Table structure for tb_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_category`;
CREATE TABLE `tb_category`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_category
-- ----------------------------
INSERT INTO `tb_category` VALUES (1, '日用品');
INSERT INTO `tb_category` VALUES (2, '电子产品');
INSERT INTO `tb_category` VALUES (3, '服饰/鞋子');
INSERT INTO `tb_category` VALUES (4, '其他');

-- ----------------------------
-- Table structure for tb_flower
-- ----------------------------
DROP TABLE IF EXISTS `tb_flower`;
CREATE TABLE `tb_flower`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `cid` bigint(0) NOT NULL COMMENT '分类id',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题',
  `images` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '封面',
  `images_list` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图片列表',
  `price` double NOT NULL COMMENT '价格 单位元',
  `specification` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '规格',
  `old_new_level` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '新旧程度',
  `brief` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简介',
  `type` int(0) NOT NULL COMMENT '类型',
  `contact_way` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `depositing_time` datetime(0) NULL DEFAULT NULL COMMENT '寄放时间',
  `audit_status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '审核状态',
  `saleable` tinyint(0) NULL DEFAULT 1 COMMENT '是否上架，0下架，1上架',
  `create_id` bigint(0) NOT NULL COMMENT '发布人',
  `background_agent_id` bigint(0) NULL DEFAULT NULL COMMENT '后台代理人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(0) NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '花' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_flower
-- ----------------------------
INSERT INTO `tb_flower` VALUES (1, 2, '一路上有你 [泰国进口] 真空玫瑰花，红玫瑰系列', '/profile/upload/2021/03/16/3ac4761a-619b-44d1-9889-dac25275185c.jpg', '/profile/upload/2021/03/18/06cdf7be-b59f-4125-a159-e665cb2c66ba.jpg,/profile/upload/2021/03/18/0a3bb2c4-5d82-468f-a343-56834ffa060f.jpg,', 368, '情感美丽永恒', '全新', NULL, 0, NULL, NULL, '1', 1, 2, NULL, '2021-03-16 14:25:27', '2021-03-18 17:09:32', 0);
INSERT INTO `tb_flower` VALUES (2, 2, '你是唯一 [11枝新品 一心一意的爱] 卡罗拉红玫瑰11枝', '/profile/upload/2021/03/16/2e691a23-9678-4bd4-bdbd-23bfeff3f5b8.jpg', '/profile/upload/2021/03/18/0e27ece2-49ec-407f-b7ca-3dc1b4ecfbf1.jpg,/profile/upload/2021/03/18/b4e22182-bb97-4379-b598-d1d9523363cd.jpg,/profile/upload/2021/03/18/5d377f93-df0a-4f6c-b0a7-b1edde1a5099.jpg,/profile/upload/2021/03/18/e8d7c402-4877-49a9-9c18-a012526b7b0e.jpg,', 168, '情感美丽永恒', '全新', NULL, 0, NULL, NULL, '1', 1, 2, NULL, '2021-03-16 15:21:00', '2021-03-18 17:12:10', 0);
INSERT INTO `tb_flower` VALUES (3, 4, '蝴蝶兰4株', '/profile/upload/2021/03/16/aefb92ae-6dd4-4510-baa3-a09144f794be.jpg', '/profile/upload/2021/03/16/aefb92ae-6dd4-4510-baa3-a09144f794be.jpg', 388, '4种被称为“卡特莱女王”的蝴蝶兰越来越受到花迷的青睐', '全新', NULL, 0, NULL, NULL, '1', 1, 2, NULL, '2021-03-16 15:29:58', '2021-03-16 15:29:58', 0);
INSERT INTO `tb_flower` VALUES (4, 4, '吉祥如意', '/profile/upload/2021/03/16/d4832824-a70e-4704-8261-09b748868102.jpg', '/profile/upload/2021/03/16/d4832824-a70e-4704-8261-09b748868102.jpg', 218, '幸福美好', '全新', NULL, 0, NULL, NULL, '1', 1, 2, NULL, '2021-03-16 15:32:22', '2021-03-16 15:32:22', 0);
INSERT INTO `tb_flower` VALUES (5, 3, '一往情深', '/profile/upload/2021/03/16/5601f330-1006-4bfb-a748-6212b95705b8.jpg', '/profile/upload/2021/03/18/81fbf2b0-2bce-44f5-8daf-b9cef307aa28.jpg,/profile/upload/2021/03/18/82937213-4767-4746-b418-1ea8a90f7882.jpg,/profile/upload/2021/03/18/1fb65fb2-9f68-47e1-af09-0e6eba018a9e.jpg,/profile/upload/2021/03/18/0c742913-c9c9-4175-9a06-099216a32fda.jpg,', 328, '你的轻柔像阵微风，让我从容不迫，想让你知道，我对你始终一往情深。', '全新', NULL, 0, NULL, NULL, '1', 1, 2, NULL, '2021-03-16 15:36:00', '2021-03-18 17:10:54', 0);
INSERT INTO `tb_flower` VALUES (6, 1, '恋恋情深 [经典款式 简约设计] 11枝香槟玫瑰，白色多头百合2枝', '/profile/upload/2021/03/16/4ac113d3-3db0-47bc-8d41-ea72bb86fd50.jpg', '/profile/upload/2021/03/18/70f8274e-4e12-4ebb-b263-c566116b9815.jpg,/profile/upload/2021/03/18/d8572165-e2d9-412d-9e2d-74823b78ed16.jpg,/profile/upload/2021/03/18/86728f42-0ac1-4c3f-a113-5ec62b183888.jpg,', 265, ' 喜欢像是一阵风，而爱你是细水长流', '全新', NULL, 0, NULL, NULL, '1', 1, 3, NULL, '2021-03-16 15:52:20', '2021-03-18 17:09:57', 0);
INSERT INTO `tb_flower` VALUES (7, 1, '狗尾巴花', '/profile/upload/2021/03/18/d01aec61-8c8e-4a38-9a5b-be17ff784873.jpg', '/profile/upload/2021/03/18/bcb852dc-57c7-4ee4-abaf-4310d2f63e92.jpg,/profile/upload/2021/03/18/b6caa864-f12f-4a1d-8297-45699d71f91a.jpg,', 168, '偷偷的暗恋着你，就是对喜欢的人始终无法找到合适的机会表达自己的情感，就这样悄无声息的进行陪伴着那个人。这样坚忍的毅力很多人是无法做到的，所以也代表着一种默默奉献之情。', '全新', NULL, 0, NULL, NULL, '1', 1, 3, NULL, '2021-03-18 10:12:37', '2021-03-18 16:42:05', 0);
INSERT INTO `tb_flower` VALUES (8, 2, '留住好时光  [精选昆明A级花材] 粉绣球1枝，粉雪山玫瑰6枝', '/profile/upload/2021/03/18/5e45d3f3-4e3c-416c-96af-0c2f71e0da29.jpg', '/profile/upload/2021/03/18/58d61881-07bc-40ae-b9b1-8d205069e8fc.jpg,/profile/upload/2021/03/18/d40d357c-494a-4ae2-a870-d916c49ffaa8.jpg,/profile/upload/2021/03/18/c905aaef-c322-4217-8108-0b53a6136328.jpg,', 66.5, ' 让每点阳光，洒于你脸庞；令你的微笑，比花更盛放！', '全新', NULL, 0, NULL, NULL, '1', 1, 3, NULL, '2021-03-18 16:45:56', '2021-03-18 16:59:11', 0);
INSERT INTO `tb_flower` VALUES (9, 1, 'dfd时代的', '/profile/upload/2021/04/09/6805793e-ae0e-4ac4-a8e6-4eacd7e85b73.png', ',/profile/upload/2021/04/09/6805793e-ae0e-4ac4-a8e6-4eacd7e85b73.png,/profile/upload/2021/04/09/db853fc4-b57e-4883-8904-b35abdbdec8b.png', 10, '三种方法阿斯顿撒旦', '全新', 'asd三国杀根深蒂固士大夫大师傅', 0, NULL, '2021-04-09 08:00:00', '1', 1, 1, NULL, '2021-04-09 13:55:49', '2021-04-09 13:55:49', 0);
INSERT INTO `tb_flower` VALUES (10, 1, '阿萨大大', '/profile/upload/2021/04/09/68d97cc8-d86a-4e38-bcb2-2eb67ac32343.png', ',/profile/upload/2021/04/09/68d97cc8-d86a-4e38-bcb2-2eb67ac32343.png,/profile/upload/2021/04/09/8c8fb620-4601-45c6-a348-03841d9c8ffc.jpg', 100, '阿斯顿撒', '全新', 'sfasf税控盘看过是东方扣税的奋斗史\n的身份美女撒旦发射点发射点是电脑辐射的\n士大夫士大夫矛盾双方手动阀手动阀\n', 0, NULL, '2021-04-09 08:00:00', '2', 0, 1, NULL, '2021-04-09 14:04:44', '2021-04-09 14:04:44', 0);
INSERT INTO `tb_flower` VALUES (11, 1, '啊实打实打算', '/profile/upload/2021/04/09/1c646ee2-c978-4cff-a968-ce1f16ecf400.jpg', ',/profile/upload/2021/04/09/1c646ee2-c978-4cff-a968-ce1f16ecf400.jpg,/profile/upload/2021/04/09/e9076f1f-0b81-4100-99bb-41bb7cce5ea2.jpg,/profile/upload/2021/04/09/6098e28b-5781-4d6e-b6fd-87a6d87e7f55.jpg', 900, '阿萨的倒十分', '全新', 'asdasfsdfsd我去额前问问微软微软我fdsfdsf俺认为人情味w\'qwq\n\n士大夫士大夫士大夫撒旦发射点发\nsdfsdfsdfsdf\n\n\n适当方式付款', 1, '18975678999', '2021-08-06 08:00:00', '1', 1, 1, 1, '2021-04-09 14:06:49', '2021-04-09 14:06:49', 0);

-- ----------------------------
-- Table structure for tb_flower_favorite
-- ----------------------------
DROP TABLE IF EXISTS `tb_flower_favorite`;
CREATE TABLE `tb_flower_favorite`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `uid` bigint(0) NULL DEFAULT NULL COMMENT '用户id',
  `favorite_id` bigint(0) NULL DEFAULT NULL COMMENT '花id',
  `create_time` bigint(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '收藏' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_flower_favorite
-- ----------------------------
INSERT INTO `tb_flower_favorite` VALUES (6, 3, 3, NULL);
INSERT INTO `tb_flower_favorite` VALUES (7, 3, 5, NULL);
INSERT INTO `tb_flower_favorite` VALUES (8, 3, 1, NULL);
INSERT INTO `tb_flower_favorite` VALUES (12, 3, 8, NULL);
INSERT INTO `tb_flower_favorite` VALUES (15, 3, 7, NULL);
INSERT INTO `tb_flower_favorite` VALUES (16, 3, 6, NULL);
INSERT INTO `tb_flower_favorite` VALUES (20, 1, 11, NULL);

-- ----------------------------
-- Table structure for tb_order
-- ----------------------------
DROP TABLE IF EXISTS `tb_order`;
CREATE TABLE `tb_order`  (
  `order_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单id',
  `total_pay` bigint(0) NULL DEFAULT NULL COMMENT '总金额 单位分',
  `actual_pay` bigint(0) NULL DEFAULT NULL COMMENT '实付金额 单位分',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `seller_id` bigint(0) NULL DEFAULT NULL COMMENT '卖家id',
  `background_agent_id` bigint(0) NULL DEFAULT NULL COMMENT '后台代理者',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户id',
  `buyer_nick` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '买家昵称',
  `receiver` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '买家全称',
  `receiver_mobile` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货人手机',
  `receiver_state` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '省份',
  `receiver_city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '城市',
  `receiver_district` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '区/县 ',
  `receiver_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货地址，如：xx路xx号',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '留言',
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_deleted` tinyint(0) NULL DEFAULT 0,
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `tb_order_detail`;
CREATE TABLE `tb_order_detail`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '订单详情id ',
  `order_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单id',
  `sku_id` bigint(0) NOT NULL COMMENT 'sku商品id',
  `num` int(0) NOT NULL COMMENT '购买数量',
  `title` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品标题',
  `own_spec` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '商品动态属性键值集',
  `price` double NOT NULL COMMENT '价格,单位：元',
  `image` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '商品图片',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `key_order_id`(`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 151 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单详情表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_order_status
-- ----------------------------
DROP TABLE IF EXISTS `tb_order_status`;
CREATE TABLE `tb_order_status`  (
  `order_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单id',
  `status` int(0) NULL DEFAULT NULL COMMENT '状态：1、未付款 2、已付款,未发货 3、已发货,未确认 4、交易成功 5、交易关闭',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '订单创建时间',
  `payment_time` datetime(0) NULL DEFAULT NULL COMMENT '付款时间',
  `consign_time` datetime(0) NULL DEFAULT NULL COMMENT '发货时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '交易完成时间',
  `close_time` datetime(0) NULL DEFAULT NULL COMMENT '交易关闭时间',
  PRIMARY KEY (`order_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单状态表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_order_status
-- ----------------------------
INSERT INTO `tb_order_status` VALUES ('1374292737267879936', 3, '2021-03-23 17:31:36', '2021-03-24 14:27:16', '2021-03-25 14:55:22', NULL, NULL);
INSERT INTO `tb_order_status` VALUES ('1374299887356727296', 5, '2021-03-23 18:00:01', '2021-03-24 15:07:44', NULL, NULL, NULL);
INSERT INTO `tb_order_status` VALUES ('1374894696794546176', 4, '2021-03-25 09:23:35', '2021-03-25 10:01:15', '2021-03-25 14:56:09', '2021-03-26 16:21:46', '2021-03-25 09:54:04');
INSERT INTO `tb_order_status` VALUES ('1375371868126601216', 1, '2021-03-26 16:59:41', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户姓名',
  `student_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学号',
  `grade` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '年级',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `role` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'student' COMMENT '角色',
  `avatar_image` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '/profile/upload/2021/03/10/dbced90a-9594-4aa7-b228-05a482c26937.png' COMMENT '头像',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (1, 'maple', '876876546379', '大四', '123456', '18976618156', 'student', '/profile/upload/2021/03/10/dbced90a-9594-4aa7-b228-05a482c26937.png', '2021-03-10 15:51:21');
INSERT INTO `tb_user` VALUES (2, 'maple', '654786547687', '大二', '123456', '13976618156', 'student', '/profile/upload/2021/03/10/dbced90a-9594-4aa7-b228-05a482c26937.png', '2021-03-10 16:06:24');
INSERT INTO `tb_user` VALUES (3, 'maple16', '987654327865', '大三', '123456', '16976618156', 'student', '/profile/upload/2021/04/01/f0865468-d19a-4eae-b6ec-18985d14be25.png', '2021-03-18 18:34:11');

SET FOREIGN_KEY_CHECKS = 1;
