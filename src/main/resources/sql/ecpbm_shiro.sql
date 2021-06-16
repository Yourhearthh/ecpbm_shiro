/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 50617
 Source Host           : 192.168.5.33:3306
 Source Schema         : ecpbm_shiro

 Target Server Type    : MySQL
 Target Server Version : 50617
 File Encoding         : 65001

 Date: 16/06/2021 16:30:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail`  (
  `id` int(4) NOT NULL AUTO_INCREMENT COMMENT '订单明细id',
  `oid` int(4) NULL DEFAULT NULL COMMENT '订单id',
  `pid` int(4) NULL DEFAULT NULL COMMENT '产品id',
  `num` int(4) NULL DEFAULT NULL COMMENT '购买数量',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `pid`(`pid`) USING BTREE,
  INDEX `oid`(`oid`) USING BTREE,
  CONSTRAINT `order_detail_ibfk_1` FOREIGN KEY (`oid`) REFERENCES `order_info` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `order_detail_ibfk_2` FOREIGN KEY (`pid`) REFERENCES `product_info` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail` VALUES (1, 1, 1, 1);
INSERT INTO `order_detail` VALUES (2, 1, 2, 1);
INSERT INTO `order_detail` VALUES (3, 2, 4, 1);
INSERT INTO `order_detail` VALUES (4, 2, 5, 1);
INSERT INTO `order_detail` VALUES (5, 2, 8, 1);
INSERT INTO `order_detail` VALUES (6, 3, 25, 1);
INSERT INTO `order_detail` VALUES (8, 5, 4, 1);
INSERT INTO `order_detail` VALUES (9, 6, 9, 1);
INSERT INTO `order_detail` VALUES (10, 7, 23, 1);
INSERT INTO `order_detail` VALUES (11, 8, 2, 1);
INSERT INTO `order_detail` VALUES (12, 9, 1, 1);

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info`  (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `uid` int(4) NULL DEFAULT NULL,
  `status` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ordertime` datetime(0) NULL DEFAULT NULL,
  `orderprice` decimal(8, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `uid`(`uid`) USING BTREE,
  CONSTRAINT `order_info_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user_info` (`uid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO `order_info` VALUES (1, 1, '未付款', '2018-05-12 00:00:00', 10687.00);
INSERT INTO `order_info` VALUES (2, 2, '已付款', '2018-05-09 00:00:00', 12997.00);
INSERT INTO `order_info` VALUES (3, 3, '未付款', '2020-04-17 00:00:00', 300.00);
INSERT INTO `order_info` VALUES (5, 7, '未付款', '2020-04-17 00:00:00', 4799.00);
INSERT INTO `order_info` VALUES (6, 6, '未付款', '2020-04-17 00:00:00', 1699.00);
INSERT INTO `order_info` VALUES (7, 9, '未付款', '2020-04-17 00:00:00', 3000.00);
INSERT INTO `order_info` VALUES (8, 1, '???', '2020-04-25 22:29:38', 4199.00);
INSERT INTO `order_info` VALUES (9, 1, '未付款', '2020-04-25 22:31:27', 6488.00);

-- ----------------------------
-- Table structure for product_info
-- ----------------------------
DROP TABLE IF EXISTS `product_info`;
CREATE TABLE `product_info`  (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `code` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品编号',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `tid` int(4) NULL DEFAULT NULL COMMENT '商品类别',
  `brand` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品品牌',
  `pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品图片',
  `num` int(4) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '商品库存',
  `price` decimal(10, 0) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '商品小图',
  `intro` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '商品简介',
  `status` int(4) NULL DEFAULT 1 COMMENT '商品状态',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `tid`(`tid`) USING BTREE,
  CONSTRAINT `product_info_ibfk_1` FOREIGN KEY (`tid`) REFERENCES `type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of product_info
-- ----------------------------
INSERT INTO `product_info` VALUES (1, '1378538', 'AppleMJVE2CH/A', 1, 'APPLE', '1378538.jpg', 0100, 0000006488, 'Apple MacBook Air 13.3英寸笔记本电脑 银色(Core i5 处理器/4GB内存/128GB SSD闪存 MJVE2CH/A)', 1);
INSERT INTO `product_info` VALUES (2, '1309456', 'ThinkPadE450C(20EH0001CD)', 1, 'ThinkPad', '1309456.jpg', 0097, 0000004199, '联想（ThinkPad） 轻薄系列E450C(20EH0001CD)14英寸笔记本电脑(i5-4210U 4G 500G 2G独显 Win8.1)', 1);
INSERT INTO `product_info` VALUES (3, '1999938', '联想小新300经典版', 1, '联想（Lenovo）', '1999938.jpg', 0099, 0000004399, '联想（Lenovo）小新300经典版 14英寸超薄笔记本电脑（i7-6500U 4G 500G 2G独显 全高清屏 Win10）黑色', 1);
INSERT INTO `product_info` VALUES (4, '1466274', '华硕FX50JX', 1, '华硕（ASUS）', '1466274.jpg', 0100, 0000004799, '华硕（ASUS）飞行堡垒FX50J 15.6英寸游戏笔记本电脑（i5-4200H 4G 7200转500G GTX950M 2G独显 全高清）', 1);
INSERT INTO `product_info` VALUES (5, '1981672', '华硕FL5800', 1, '华硕（ASUS）', '1981672.jpg', 0100, 0000004999, '华硕（ASUS）FL5800 15.6英寸笔记本电脑 （i7-5500U 4G 128G SSD+1TB 2G独显 蓝牙 Win10 黑色）', 1);
INSERT INTO `product_info` VALUES (6, '1904696', '联想G50-70M', 1, '联想（Lenovo）', '1904696.jpg', 0012, 0000003499, '联想（Lenovo）G50-70M 15.6英寸笔记本电脑（i5-4210U 4G 500G GT820M 2G独显 DVD刻录 Win8.1）金属黑', 1);
INSERT INTO `product_info` VALUES (7, '751624', '美的BCD-206TM(E)', 2, ' 美的（Midea）', '751624.jpg', 0100, 0000001298, '美的(Midea) BCD-206TM(E) 206升 三门冰箱 节能保鲜 闪白银', 1);
INSERT INTO `product_info` VALUES (8, '977433', '美的BCD-516WKM(E)', 2, ' 美的（Midea）', '977433.jpg', 0100, 0000003199, '美的(Midea) BCD-516WKM(E) 516升 对开门冰箱 风冷无霜 电脑控温 纤薄设计 节能静音 （泰坦银）', 1);
INSERT INTO `product_info` VALUES (9, '1143562', '海尔BCD-216SDN', 2, ' 海尔（Haier）', '1143562.jpg', 0100, 0000001699, '海尔（Haier）BCD-216SDN 216升 三门冰箱 电脑控温 中门 宽幅变温 大冷冻能力低能耗更省钱', 1);
INSERT INTO `product_info` VALUES (10, '1560207', '海尔BCD-258WDPM', 2, ' 海尔（Haier）', '1560207.jpg', 0100, 0000002699, '海尔（Haier）BCD-258WDPM 258升 风冷无霜三门冰箱 除菌 3D立体环绕风不风干 中门5℃~-18℃变温室', 1);
INSERT INTO `product_info` VALUES (11, '1721668', '海信（Hisense）BCD-559WT/Q', 2, ' 海信（Hisense）', '1721668.jpg', 0100, 0000003499, '海信（Hisense）BCD-559WT/Q 559升 金色电脑风冷节能对开门冰箱', 1);
INSERT INTO `product_info` VALUES (12, '823125', '海信BCD-211TD/E', 2, ' 海信（Hisense）', 'C36AC3F473AFA925F328AC0C9539A01D.jpg', 0100, 0000001699, '海信（Hisense） BCD-211TD/E 211升 电脑三门冰箱 （亮金刚）', 1);
INSERT INTO `product_info` VALUES (15, '111111', 'HP1306', 10, NULL, '1721668.jpg', NULL, NULL, NULL, 1);
INSERT INTO `product_info` VALUES (20, '2501325', 'CCTV', 3, '包子', '1721668.jpg', 0020, 0000000300, '电视机不错', 1);
INSERT INTO `product_info` VALUES (21, '2125421', 'a', 1, 'a', '1721668.jpg', 0020, 0000001111, '111', 1);
INSERT INTO `product_info` VALUES (22, 'a', 'ac', 1, 'a', '1721668.jpg', 0020, 0000096022, 'dadddd', 0);
INSERT INTO `product_info` VALUES (23, 'a521551', '新数码相机', 5, '华为', '1721668.jpg', 0050, 0000003000, '不错', 1);
INSERT INTO `product_info` VALUES (24, '25615', '智能手机', 8, '华为', '1721668.jpg', 0020, 0000002000, '不错', 1);
INSERT INTO `product_info` VALUES (25, '12025462', '遥控器', 2, '华为', '1721668.jpg', 0020, 0000000300, '不错', 1);

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `available` bit(1) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `parent_id` bigint(20) NULL DEFAULT NULL,
  `parent_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `permission` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `resource_type` enum('menu','button') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, b'0', '商品管理', 0, '0/', 'productInfo:view', 'menu', 'productInfo/getProductPage');
INSERT INTO `sys_permission` VALUES (2, b'0', '商品添加', 1, '0/1', 'productInfo:add', 'button', 'productInfo/addProduct');
INSERT INTO `sys_permission` VALUES (3, b'0', '商品下架', 1, '0/1', 'productInfo:del', 'button', 'productInfo/deleteProduct');
INSERT INTO `sys_permission` VALUES (4, b'0', '获取在售商品', 1, '0/1', 'productInfo:onsale', 'button', 'productInfo/getSaleOnProduct');
INSERT INTO `sys_permission` VALUES (5, b'0', '获取全部商品类型', 1, '0/1', 'type:info', 'menu', 'type/getTypes');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `available` bit(1) NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, b'0', '管理员', 'admin');
INSERT INTO `sys_role` VALUES (2, b'0', 'VIP会员', 'vip');
INSERT INTO `sys_role` VALUES (3, b'0', 'test', 'test');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  INDEX `FK9q28ewrhntqeipl1t04kh1be7`(`role_id`) USING BTREE,
  INDEX `FKomxrs8a388bknvhjokh440waq`(`permission_id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (1, 1);
INSERT INTO `sys_role_permission` VALUES (1, 2);
INSERT INTO `sys_role_permission` VALUES (1, 3);
INSERT INTO `sys_role_permission` VALUES (1, 4);
INSERT INTO `sys_role_permission` VALUES (2, 5);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `uid` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  INDEX `FKhh52n8vd4ny9ff4x9fb8v65qx`(`role_id`) USING BTREE,
  INDEX `FKgkmyslkrfeyn9ukmolvek8b8f`(`uid`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (1, 2);
INSERT INTO `sys_user_role` VALUES (2, 2);

-- ----------------------------
-- Table structure for type
-- ----------------------------
DROP TABLE IF EXISTS `type`;
CREATE TABLE `type`  (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = gbk COLLATE = gbk_chinese_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of type
-- ----------------------------
INSERT INTO `type` VALUES (1, '电脑');
INSERT INTO `type` VALUES (2, '冰箱');
INSERT INTO `type` VALUES (3, '电视机');
INSERT INTO `type` VALUES (4, '洗衣机');
INSERT INTO `type` VALUES (5, '数码相机');
INSERT INTO `type` VALUES (7, '空调');
INSERT INTO `type` VALUES (8, '手机');
INSERT INTO `type` VALUES (10, '打印机');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `uid` int(4) NOT NULL AUTO_INCREMENT,
  `userName` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `realName` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `regDate` date NULL DEFAULT NULL,
  `status` int(4) NULL DEFAULT 1,
  `salt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (1, 'tom', 'b37f21b1deb91e15e02d86752d490961', '汤姆', '女', '江苏省苏州市吴中区', 'tom@123.com', '2013-07-14', 1, '8d78869f470951332959580424d4bf4f');
INSERT INTO `user_info` VALUES (2, 'john', 'bd2676616a594b6523665017bc526123', '约翰', '女', '江苏省南京市玄武区', 'wen@135.com', '2013-07-14', 1, '8d78869f470951332959580424d4bf4f');
INSERT INTO `user_info` VALUES (3, 'mmm', '2e6f1d57102b456d70aa72de188b550e', 'my', '男', '江苏省南京市玄武区', 'a@135.com', '2015-09-16', 1, '8d78869f470951332959580424d4bf4f');
INSERT INTO `user_info` VALUES (4, 'sj', '123456', 'sj', '男', '江苏省南京市玄武区', 'b@135.com', '2015-09-16', 0, '8d78869f470951332959580424d4bf4f');
INSERT INTO `user_info` VALUES (5, 'lxf', '123456', 'lxf', '男', '江苏省南京市玄武区', 'c@135.com', '2015-09-16', 1, '8d78869f470951332959580424d4bf4f');
INSERT INTO `user_info` VALUES (6, 'lj', '123456', 'lj', '男', '江苏省南京市玄武区', 'a@135.com', '2015-09-20', 1, '8d78869f470951332959580424d4bf4f');
INSERT INTO `user_info` VALUES (7, 'mybatis1', '123456', NULL, NULL, NULL, NULL, NULL, 1, '8d78869f470951332959580424d4bf4f');
INSERT INTO `user_info` VALUES (9, 'mybatis2', '123456', NULL, NULL, NULL, NULL, NULL, 1, '8d78869f470951332959580424d4bf4f');

SET FOREIGN_KEY_CHECKS = 1;
