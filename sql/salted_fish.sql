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

 Date: 16/04/2021 10:28:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for evaluation
-- ----------------------------
DROP TABLE IF EXISTS `evaluation`;
CREATE TABLE `evaluation`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `content` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评价内容',
  `flower_id` bigint(0) NULL DEFAULT NULL COMMENT '评价的商品id',
  `parent_id` bigint(0) NULL DEFAULT 0 COMMENT '父id',
  `to_user_id` bigint(0) NULL DEFAULT 0 COMMENT '被评论人Id',
  `to_user_type` int(0) NULL DEFAULT 0 COMMENT '被评论人类型(0:C端，1：B端）',
  `creator_id` bigint(0) NOT NULL COMMENT '评论人',
  `creator_type` int(0) NULL DEFAULT 0 COMMENT '创建者类型(0:C端，1：B端）',
  `created_time` datetime(0) NULL DEFAULT NULL,
  `updated_time` datetime(0) NULL DEFAULT NULL,
  `is_deleted` tinyint(0) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 62 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '评价/评论' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of evaluation
-- ----------------------------
INSERT INTO `evaluation` VALUES (49, '非常nice', 3, 0, 0, 0, 1, 0, '2021-04-14 16:08:41', '2021-04-14 16:08:44', 0);
INSERT INTO `evaluation` VALUES (50, '爱啥看到你发萨芬', 3, 0, 0, 0, 1, 0, '2021-04-14 16:10:12', '2021-04-14 16:10:12', 0);
INSERT INTO `evaluation` VALUES (52, '啊实打实的啊实打实的阿萨的a\'s\'dasd啊大苏打啊实打实的阿萨大实打实大苏打实打实大大啊实打实大苏打撒旦啊实打实大苏打撒旦啊萨达萨达撒旦啊实打实的大萨芬士大夫', 3, 0, 0, 0, 1, 0, '2021-04-14 16:17:03', '2021-04-14 16:17:03', 0);
INSERT INTO `evaluation` VALUES (54, '不错不错', 3, 52, 1, 0, 1, 0, '2021-04-14 18:30:47', '2021-04-14 18:30:47', 0);
INSERT INTO `evaluation` VALUES (55, '非常不错哦', 11, 0, 0, 0, 1, 0, '2021-04-14 18:32:42', '2021-04-14 18:32:42', 0);
INSERT INTO `evaluation` VALUES (56, '你好', 3, 50, 1, 0, 1, 0, '2021-04-14 18:33:19', '2021-04-14 18:33:19', 0);
INSERT INTO `evaluation` VALUES (57, '真好', 3, 52, 1, 0, 2, 0, '2021-04-14 19:16:37', '2021-04-14 19:16:37', 0);
INSERT INTO `evaluation` VALUES (58, '你可以呀', 3, 52, 1, 0, 2, 0, '2021-04-14 19:16:47', '2021-04-14 19:16:47', 0);
INSERT INTO `evaluation` VALUES (59, '真好', 3, 50, 1, 0, 2, 0, '2021-04-14 19:17:21', '2021-04-14 19:17:21', 0);
INSERT INTO `evaluation` VALUES (60, '不错不错，测试测试', 3, 0, 0, 0, 1, 0, '2021-04-15 13:48:35', '2021-04-15 13:48:35', 0);
INSERT INTO `evaluation` VALUES (61, '你好啊', 3, 50, 1, 0, 1, 0, '2021-04-15 13:49:23', '2021-04-15 13:49:23', 0);
INSERT INTO `evaluation` VALUES (62, '这个产品也太好了', 3, 0, 0, 0, 1, 0, '2021-04-15 15:00:34', '2021-04-15 15:00:34', 0);
INSERT INTO `evaluation` VALUES (63, '哈哈哈', 3, 52, 1, 0, 1, 0, '2021-04-15 15:34:39', '2021-04-15 15:34:39', 0);
INSERT INTO `evaluation` VALUES (64, '我也觉得不错', 3, 0, 0, 0, 1, 0, '2021-04-15 17:25:14', '2021-04-15 17:25:14', 0);
INSERT INTO `evaluation` VALUES (65, '帅啊', 3, 50, 1, 0, 1, 0, '2021-04-16 10:22:26', '2021-04-16 10:22:26', 0);
INSERT INTO `evaluation` VALUES (66, '哈哈哈哈哈哈哈哈哈哈哈', 3, 50, 1, 0, 1, 0, '2021-04-16 10:23:40', '2021-04-16 10:23:40', 0);
INSERT INTO `evaluation` VALUES (67, '但是ADASD', 3, 52, 1, 0, 1, 0, '2021-04-16 10:24:20', '2021-04-16 10:24:20', 0);
INSERT INTO `evaluation` VALUES (68, '萨芬士大夫士大夫', 3, 52, 1, 0, 1, 0, '2021-04-16 10:24:47', '2021-04-16 10:24:47', 0);
INSERT INTO `evaluation` VALUES (69, 'sdfdsfdfds', 3, 50, 1, 0, 1, 0, '2021-04-16 10:25:35', '2021-04-16 10:25:35', 0);
INSERT INTO `evaluation` VALUES (70, '就啊打发撒旦', 3, 60, 1, 0, 1, 0, '2021-04-16 10:26:06', '2021-04-16 10:26:06', 0);
INSERT INTO `evaluation` VALUES (71, '的非官方大概', 3, 60, 1, 0, 1, 0, '2021-04-16 10:26:14', '2021-04-16 10:26:14', 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '地址' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_address
-- ----------------------------
INSERT INTO `tb_address` VALUES (1, 3, 'maple', '17986897562', '湖南省', '长沙市', '岳麓区', '科教新村709');
INSERT INTO `tb_address` VALUES (5, 2, 'sad', '15876899876', '河北省', '怀化市', '撒旦区', '阿萨的撒反对发598');
INSERT INTO `tb_address` VALUES (6, 1, 'maple', '18343743243', '四川省', '成都市', '阿萨区', '啊实打实的888');
INSERT INTO `tb_address` VALUES (7, 1, '撒大沙发', '16323433443', '大师傅大师傅但是', '士大夫d', '第三方的', '大概豆腐干豆腐干');

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
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_background_user
-- ----------------------------
INSERT INTO `tb_background_user` VALUES (1, 'maple', '123456', '18976618156', 'admin', '/profile/upload/2021/04/15/ae39170a-80dc-4724-9c69-2e148fdeb8b6.jpg', '2021-03-10 15:51:21');
INSERT INTO `tb_background_user` VALUES (2, 'maple2', '123456', '15976618156', 'general _admin', '/profile/upload/2021/04/12/aeb30bc8-adec-4cb9-b738-1b3f3427c007.jpg', '2021-03-10 16:06:24');
INSERT INTO `tb_background_user` VALUES (3, 'maple3', '123456', '16976618156', 'general _admin', '/profile/upload/2021/04/12/aeb30bc8-adec-4cb9-b738-1b3f3427c007.jpg', '2021-03-15 14:35:11');
INSERT INTO `tb_background_user` VALUES (4, 'maple4', '123456', '15679876356', 'general _admin', '/profile/upload/2021/04/12/aeb30bc8-adec-4cb9-b738-1b3f3427c007.jpg', '2021-03-15 14:45:52');
INSERT INTO `tb_background_user` VALUES (6, 'maple6', '123456', '15879876356', 'general _admin', '/profile/upload/2021/04/12/aeb30bc8-adec-4cb9-b738-1b3f3427c007.jpg', '2021-03-15 14:46:24');

-- ----------------------------
-- Table structure for tb_cart
-- ----------------------------
DROP TABLE IF EXISTS `tb_cart`;
CREATE TABLE `tb_cart`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户id',
  `seller_id` bigint(0) NULL DEFAULT NULL COMMENT '卖家id',
  `background_agent_id` bigint(0) NULL DEFAULT 0 COMMENT '后台代理用户id',
  `sku_id` bigint(0) NULL DEFAULT NULL COMMENT '商品id',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品标题',
  `image` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片',
  `price` double NULL DEFAULT NULL COMMENT '价格 单位元',
  `num` int(0) NULL DEFAULT NULL COMMENT '数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '购物车' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_cart
-- ----------------------------
INSERT INTO `tb_cart` VALUES (16, 1, 2, 0, 3, '蝴蝶兰4株', '/profile/upload/2021/04/09/1c646ee2-c978-4cff-a968-ce1f16ecf400.jpg', 388, 2);

-- ----------------------------
-- Table structure for tb_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_category`;
CREATE TABLE `tb_category`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '分类' ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '花' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_flower
-- ----------------------------
INSERT INTO `tb_flower` VALUES (1, 2, '一路上有你 [泰国进口] 真空玫瑰花，红玫瑰系列', '/profile/upload/2021/04/09/1c646ee2-c978-4cff-a968-ce1f16ecf400.jpg', '/profile/upload/2021/04/09/1c646ee2-c978-4cff-a968-ce1f16ecf400.jpg', 368, '情感美丽永恒', '全新', NULL, 0, NULL, NULL, '1', 1, 2, NULL, '2021-03-16 14:25:27', '2021-03-18 17:09:32', 0);
INSERT INTO `tb_flower` VALUES (2, 2, '你是唯一 [11枝新品 一心一意的爱] 卡罗拉红玫瑰11枝', '/profile/upload/2021/04/09/1c646ee2-c978-4cff-a968-ce1f16ecf400.jpg', '/profile/upload/2021/04/09/1c646ee2-c978-4cff-a968-ce1f16ecf400.jpg', 168, '情感美丽永恒', '全新', NULL, 0, NULL, NULL, '1', 1, 2, NULL, '2021-03-16 15:21:00', '2021-03-18 17:12:10', 0);
INSERT INTO `tb_flower` VALUES (3, 4, '蝴蝶兰4株', '/profile/upload/2021/04/09/1c646ee2-c978-4cff-a968-ce1f16ecf400.jpg', '/profile/upload/2021/04/09/1c646ee2-c978-4cff-a968-ce1f16ecf400.jpg', 388, '4种被称为“卡特莱女王”的蝴蝶兰越来越受到花迷的青睐', '全新', NULL, 0, NULL, NULL, '1', 1, 2, NULL, '2021-03-16 15:29:58', '2021-03-16 15:29:58', 0);
INSERT INTO `tb_flower` VALUES (4, 4, '吉祥如意', '/profile/upload/2021/04/09/1c646ee2-c978-4cff-a968-ce1f16ecf400.jpg', '/profile/upload/2021/04/09/1c646ee2-c978-4cff-a968-ce1f16ecf400.jpg', 218, '幸福美好', '全新', NULL, 0, NULL, NULL, '1', 1, 2, NULL, '2021-03-16 15:32:22', '2021-03-16 15:32:22', 0);
INSERT INTO `tb_flower` VALUES (5, 3, '一往情深', '/profile/upload/2021/04/09/1c646ee2-c978-4cff-a968-ce1f16ecf400.jpg', '/profile/upload/2021/04/09/1c646ee2-c978-4cff-a968-ce1f16ecf400.jpg', 328, '你的轻柔像阵微风，让我从容不迫，想让你知道，我对你始终一往情深。', '全新', NULL, 0, NULL, NULL, '1', 1, 2, NULL, '2021-03-16 15:36:00', '2021-03-18 17:10:54', 0);
INSERT INTO `tb_flower` VALUES (6, 1, '恋恋情深 [经典款式 简约设计] 11枝香槟玫瑰，白色多头百合2枝', '/profile/upload/2021/04/09/1c646ee2-c978-4cff-a968-ce1f16ecf400.jpg', '/profile/upload/2021/04/09/1c646ee2-c978-4cff-a968-ce1f16ecf400.jpg', 265, ' 喜欢像是一阵风，而爱你是细水长流', '全新', NULL, 0, NULL, NULL, '1', 1, 3, NULL, '2021-03-16 15:52:20', '2021-03-18 17:09:57', 0);
INSERT INTO `tb_flower` VALUES (7, 1, '狗尾巴花', '/profile/upload/2021/04/09/1c646ee2-c978-4cff-a968-ce1f16ecf400.jpg', '/profile/upload/2021/04/09/1c646ee2-c978-4cff-a968-ce1f16ecf400.jpg', 168, '偷偷的暗恋着你，就是对喜欢的人始终无法找到合适的机会表达自己的情感，就这样悄无声息的进行陪伴着那个人。这样坚忍的毅力很多人是无法做到的，所以也代表着一种默默奉献之情。', '全新', NULL, 0, NULL, NULL, '1', 1, 3, NULL, '2021-03-18 10:12:37', '2021-03-18 16:42:05', 0);
INSERT INTO `tb_flower` VALUES (8, 2, '留住好时光  [精选昆明A级花材] 粉绣球1枝，粉雪山玫瑰6枝', '/profile/upload/2021/04/09/1c646ee2-c978-4cff-a968-ce1f16ecf400.jpg', '/profile/upload/2021/04/09/1c646ee2-c978-4cff-a968-ce1f16ecf400.jpg', 66.5, ' 让每点阳光，洒于你脸庞；令你的微笑，比花更盛放！', '全新', NULL, 0, NULL, NULL, '1', 1, 3, NULL, '2021-03-18 16:45:56', '2021-03-18 16:59:11', 0);
INSERT INTO `tb_flower` VALUES (9, 1, 'dfd时代的', '/profile/upload/2021/04/09/1c646ee2-c978-4cff-a968-ce1f16ecf400.jpg', '/profile/upload/2021/04/09/1c646ee2-c978-4cff-a968-ce1f16ecf400.jpg', 10, '三种方法阿斯顿撒旦', '全新', 'asd三国杀根深蒂固士大夫大师傅', 0, NULL, '2021-04-09 08:00:00', '1', 1, 1, NULL, '2021-04-09 13:55:49', '2021-04-09 13:55:49', 0);
INSERT INTO `tb_flower` VALUES (10, 1, '阿萨大大', '/profile/upload/2021/04/09/68d97cc8-d86a-4e38-bcb2-2eb67ac32343.png', '/profile/upload/2021/04/09/1c646ee2-c978-4cff-a968-ce1f16ecf400.jpg', 100, '阿斯顿撒', '全新', 'sfasf税控盘看过是东方扣税的奋斗史\n的身份美女撒旦发射点发射点是电脑辐射的\n士大夫士大夫矛盾双方手动阀手动阀\n', 0, NULL, '2021-04-09 08:00:00', '1', 0, 1, NULL, '2021-04-09 14:04:44', '2021-04-09 14:04:44', 0);
INSERT INTO `tb_flower` VALUES (11, 1, '啊实打实打算', '/profile/upload/2021/04/09/1c646ee2-c978-4cff-a968-ce1f16ecf400.jpg', '/profile/upload/2021/04/09/1c646ee2-c978-4cff-a968-ce1f16ecf400.jpg', 900, '阿萨的倒十分', '全新', 'asdasfsdfsd我去额前问问微软微软我fdsfdsf俺认为人情味w\'qwq\n\n士大夫士大夫士大夫撒旦发射点发\nsdfsdfsdfsdf\n\n\n适当方式付款', 1, '18975678999', '2021-08-06 08:00:00', '1', 1, 2, 1, '2021-04-09 14:06:49', '2021-04-09 14:06:49', 0);
INSERT INTO `tb_flower` VALUES (12, 1, '摩西摩西呢', '/profile/upload/2021/04/14/78a2808f-08fb-4085-9c56-2b3c703b0f33.jpg', ',/profile/upload/2021/04/14/78a2808f-08fb-4085-9c56-2b3c703b0f33.jpg', 10, '你想那在哪', '全新', '那些那些毛线妈妈在吗在吗准考证摩西摩西妹子们摩西摩西奶香奶味妈妈在吗在吗在忙什么没怎么想没现金就是就是妹子们抓紧时间时间就是呢想你想你想你你说呢', 0, NULL, '2021-04-14 08:00:00', '2', 1, 1, NULL, '2021-04-14 14:28:06', '2021-04-14 14:28:06', 0);
INSERT INTO `tb_flower` VALUES (13, 3, '安定', '/profile/upload/2021/04/14/0c8578de-8a66-4d8d-82b7-4acfc2e62729.png', ',/profile/upload/2021/04/14/0c8578de-8a66-4d8d-82b7-4acfc2e62729.png,/profile/upload/2021/04/14/a85e89ad-abfd-42e4-86e1-cf811b669edd.png,/profile/upload/2021/04/14/81676026-efba-4c3c-b7d6-f73625235e0e.jpg', 10, '41码，黑色，男士运动', '全新', 'asdsdki技能释放速度爱斯福德男爵撒大沙发打赏暗室逢灯阿萨的撒', 1, '16827387232', '2021-07-09 08:00:00', '1', 1, 1, 1, '2021-04-14 18:36:16', '2021-04-14 18:36:16', 0);
INSERT INTO `tb_flower` VALUES (14, 2, '大概发个', '/profile/upload/2021/04/15/3ba02440-e5f3-4167-9774-8b441997aaaf.jpg', ',/profile/upload/2021/04/15/3ba02440-e5f3-4167-9774-8b441997aaaf.jpg,/profile/upload/2021/04/15/585b8ae1-5fc8-4589-a920-2a0004df8e5a.jpeg', 100, '撒旦发射点发射点发的广', '几乎全新', 'sdafsdf先不管负电荷负电荷豆腐干大概豆腐干豆腐干豆腐干豆腐干地方', 1, '13434543546', '2021-04-15 08:00:00', '1', 1, 1, 1, '2021-04-15 13:46:38', '2021-04-15 13:46:38', 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '收藏' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_flower_favorite
-- ----------------------------
INSERT INTO `tb_flower_favorite` VALUES (7, 3, 5, NULL);
INSERT INTO `tb_flower_favorite` VALUES (8, 3, 1, NULL);
INSERT INTO `tb_flower_favorite` VALUES (12, 3, 8, NULL);
INSERT INTO `tb_flower_favorite` VALUES (15, 3, 7, NULL);
INSERT INTO `tb_flower_favorite` VALUES (16, 3, 6, NULL);
INSERT INTO `tb_flower_favorite` VALUES (20, 1, 11, NULL);
INSERT INTO `tb_flower_favorite` VALUES (23, 1, 3, NULL);

-- ----------------------------
-- Table structure for tb_order
-- ----------------------------
DROP TABLE IF EXISTS `tb_order`;
CREATE TABLE `tb_order`  (
  `order_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单id',
  `total_pay` bigint(0) NULL DEFAULT NULL COMMENT '总金额 单位分',
  `actual_pay` bigint(0) NULL DEFAULT NULL COMMENT '实付金额 单位分',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `seller_id` bigint(0) NULL DEFAULT NULL COMMENT '卖家id',
  `background_agent_id` bigint(0) NULL DEFAULT 0 COMMENT '后台代理者',
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
-- Records of tb_order
-- ----------------------------
INSERT INTO `tb_order` VALUES ('1382156777302290432', 1104, 1114, '2021-04-14 10:20:30', '2021-04-08 14:42:08', 1, 0, 1, 'maple', 'maple', '18343743243', '四川省', '成都市', '阿萨区', '啊实打实的888', '', '4', 0);
INSERT INTO `tb_order` VALUES ('1382156777394565120', 900, 910, '2021-04-14 10:20:30', '2021-04-09 14:42:13', 1, 1, 1, 'maple', 'maple', '18343743243', '四川省', '成都市', '阿萨区', '啊实打实的888', '', '5', 0);
INSERT INTO `tb_order` VALUES ('1382156777432313856', 2322, 2332, '2021-04-14 10:20:30', '2021-04-10 14:42:17', 2, 0, 1, 'maple', 'maple', '18343743243', '四川省', '成都市', '阿萨区', '啊实打实的888', '', '2', 0);
INSERT INTO `tb_order` VALUES ('1382156777503617024', 198, 208, '2021-04-14 10:20:30', '2021-04-11 14:42:20', 3, 0, 1, 'maple', 'maple', '18343743243', '四川省', '成都市', '阿萨区', '啊实打实的888', '', '2', 0);
INSERT INTO `tb_order` VALUES ('1382158599303741440', 218, 228, '2021-04-14 10:27:44', '2021-04-12 14:42:23', 2, 0, 1, 'maple', 'maple', '18343743243', '四川省', '成都市', '阿萨区', '啊实打实的888', '', '2', 0);
INSERT INTO `tb_order` VALUES ('1382281035890298880', 388, 398, '2021-04-14 18:34:15', '2021-04-13 14:42:27', 2, 0, 1, 'maple', 'maple', '18343743243', '四川省', '成都市', '阿萨区', '啊实打实的888', '', '2', 0);
INSERT INTO `tb_order` VALUES ('1382571351843295232', 776, 786, '2021-04-15 13:47:52', '2021-04-14 14:42:30', 2, 0, 1, 'maple', 'maple', '18343743243', '四川省', '成都市', '阿萨区', '啊实打实的888', '', '2', 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 161 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单详情表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_order_detail
-- ----------------------------
INSERT INTO `tb_order_detail` VALUES (152, '1382156777302290432', 1, 3, '一路上有你 [泰国进口] 真空玫瑰花，红玫瑰系列', '', 368, '/profile/upload/2021/04/09/1c646ee2-c978-4cff-a968-ce1f16ecf400.jpg');
INSERT INTO `tb_order_detail` VALUES (153, '1382156777394565120', 11, 1, '啊实打实打算', '', 900, '/profile/upload/2021/04/09/1c646ee2-c978-4cff-a968-ce1f16ecf400.jpg');
INSERT INTO `tb_order_detail` VALUES (154, '1382156777432313856', 3, 3, '蝴蝶兰4株', '', 388, '/profile/upload/2021/04/09/1c646ee2-c978-4cff-a968-ce1f16ecf400.jpg');
INSERT INTO `tb_order_detail` VALUES (155, '1382156777432313856', 4, 3, '吉祥如意', '', 218, '/profile/upload/2021/04/09/1c646ee2-c978-4cff-a968-ce1f16ecf400.jpg');
INSERT INTO `tb_order_detail` VALUES (156, '1382156777432313856', 2, 3, '你是唯一 [11枝新品 一心一意的爱] 卡罗拉红玫瑰11枝', '', 168, '/profile/upload/2021/04/09/1c646ee2-c978-4cff-a968-ce1f16ecf400.jpg');
INSERT INTO `tb_order_detail` VALUES (157, '1382156777503617024', 8, 3, '留住好时光  [精选昆明A级花材] 粉绣球1枝，粉雪山玫瑰6枝', '', 66, '/profile/upload/2021/04/09/1c646ee2-c978-4cff-a968-ce1f16ecf400.jpg');
INSERT INTO `tb_order_detail` VALUES (158, '1382158599303741440', 4, 1, '吉祥如意', '', 218, '/profile/upload/2021/04/09/1c646ee2-c978-4cff-a968-ce1f16ecf400.jpg');
INSERT INTO `tb_order_detail` VALUES (159, '1382281035890298880', 3, 1, '蝴蝶兰4株', '', 388, '/profile/upload/2021/04/09/1c646ee2-c978-4cff-a968-ce1f16ecf400.jpg');
INSERT INTO `tb_order_detail` VALUES (160, '1382571351843295232', 3, 2, '蝴蝶兰4株', '', 388, '/profile/upload/2021/04/09/1c646ee2-c978-4cff-a968-ce1f16ecf400.jpg');

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
INSERT INTO `tb_order_status` VALUES ('1382156777302290432', 4, '2021-04-14 10:20:30', NULL, '2021-04-14 14:06:38', '2021-04-15 13:43:42', '2021-04-14 10:28:01');
INSERT INTO `tb_order_status` VALUES ('1382156777394565120', 5, '2021-04-14 10:20:30', '2021-04-15 14:00:05', '2021-04-15 14:00:31', '2021-04-15 14:00:44', '2021-04-15 14:05:42');
INSERT INTO `tb_order_status` VALUES ('1382156777432313856', 2, '2021-04-14 10:20:30', '2021-04-14 18:21:39', NULL, NULL, NULL);
INSERT INTO `tb_order_status` VALUES ('1382156777503617024', 2, '2021-04-14 10:20:30', '2021-04-14 18:32:13', NULL, NULL, NULL);
INSERT INTO `tb_order_status` VALUES ('1382158599303741440', 2, '2021-04-14 10:27:44', '2021-04-14 10:27:44', NULL, NULL, NULL);
INSERT INTO `tb_order_status` VALUES ('1382281035890298880', 2, '2021-04-14 18:34:15', '2021-04-14 18:34:15', NULL, NULL, NULL);
INSERT INTO `tb_order_status` VALUES ('1382571351843295232', 2, '2021-04-15 13:47:52', '2021-04-15 13:47:52', NULL, NULL, NULL);

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
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (1, 'maple', '876876546379', '大三', '123456', '18976618156', 'student', '/profile/upload/2021/04/12/aeb30bc8-adec-4cb9-b738-1b3f3427c007.jpg', '2021-03-10 15:51:21');
INSERT INTO `tb_user` VALUES (2, 'maple2', '654786547687', '大二', '123456', '13976618156', 'student', '/profile/upload/2021/04/12/aeb30bc8-adec-4cb9-b738-1b3f3427c007.jpg', '2021-03-10 16:06:24');
INSERT INTO `tb_user` VALUES (3, 'maple16', '987654327865', '大三', '123456', '16976618156', 'student', '/profile/upload/2021/04/12/aeb30bc8-adec-4cb9-b738-1b3f3427c007.jpg', '2021-03-18 18:34:11');
INSERT INTO `tb_user` VALUES (4, 'esetrdtr', NULL, NULL, '123456', '18997718156', 'student', '/profile/upload/2021/03/10/dbced90a-9594-4aa7-b228-05a482c26937.png', '2021-04-15 13:43:18');

SET FOREIGN_KEY_CHECKS = 1;
