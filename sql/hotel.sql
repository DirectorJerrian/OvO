-- MySQL dump 10.13  Distrib 5.7.19, for macos10.12 (x86_64)
--
-- Host: 127.0.0.1    Database: Hotel
-- ------------------------------------------------------
-- Server version	5.7.19-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `Coupon`
--
use hotel;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS `Coupon`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Coupon`
(
    `id`             int(11)      NOT NULL AUTO_INCREMENT,
    `description`    varchar(255) DEFAULT NULL,
    `hotelId`        int(11)      DEFAULT '-1',
    `couponType`     int(11)      NOT NULL,
    `couponName`     varchar(255) NOT NULL,
    `target_money`   int(11)      DEFAULT NULL,
    `discount`       double       DEFAULT NULL,
    `status`         int(11)      DEFAULT NULL,
    `start_time`     datetime     DEFAULT NULL,
    `end_time`       datetime     DEFAULT NULL,
    `discount_money` int(11)      DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Coupon`
--
BEGIN;
/*!40000 ALTER TABLE `Coupon`
    DISABLE KEYS */;
INSERT INTO `Coupon`
VALUES (2, '满500-100优惠', 2, 3, '满减优惠券', 500, 0, 1, NULL, NULL, 100),
        (21, '满500-100优惠', 3, 3, '满减优惠券', 500, 0, 1, NULL, NULL, 100),
        (22, '满600-120优惠', 2, 3, '满减优惠券', 600, 0, 1, NULL, NULL, 120),
        (23, '满800-150优惠', 3, 3, '满减优惠券', 800, 0, 1, NULL, NULL, 150),
        (24, '满1000-180优惠', 1, 3, '满减优惠券', 1000, 0, 1, NULL, NULL, 180),
        (25, '满2000-200优惠', 3, 3, '满减优惠券', 2000, 0, 1, NULL, NULL, 200),
        (26, '满500-100优惠', 1, 3, '满减优惠券', 500, 0, 1, NULL, NULL, 100),
        (27, '订满多间有优惠', 1, 2, '多间优惠', 600, 0, 1, NULL, NULL, 200),
        (28, '订满多间有优惠', 2, 2, '多间优惠', 600, 0, 1, NULL, NULL, 200),
        (29, '订满多间有优惠', 3, 2, '多间优惠', 600, 0, 1, NULL, NULL, 200),
        (30, '订满多间有优惠', 2, 2, '多间优惠', 800, 0, 1, NULL, NULL, 300);
/*!40000 ALTER TABLE `Coupon`
    ENABLE KEYS */;
COMMIT;

--
-- Table structure for table `Hotel`
--

DROP TABLE IF EXISTS `Hotel`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Hotel`
(
    `id`               int(11)      NOT NULL AUTO_INCREMENT,
    `hotelName`        varchar(255) NOT NULL,
    `hotelDescription` varchar(255)  DEFAULT NULL,
    `address`          varchar(255)  DEFAULT NULL,
    `bizRegion`        varchar(255)  DEFAULT NULL,
    `hotelStar`        varchar(255)  DEFAULT NULL,
    `phoneNum`         varchar(255)  DEFAULT NULL,
    `rate`             double        DEFAULT NULL,
    `manager_id`       int(11)       DEFAULT NULL,
    `cover`            varchar(1023) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Hotel`
--

BEGIN;
/*!40000 ALTER TABLE `Hotel`
    DISABLE KEYS */;
INSERT INTO `Hotel`
VALUES (1, '北京唐拉雅秀酒店', '北京唐拉雅秀酒店地处中央政务和金融商务区，紧邻金融街，可便捷前往天安门广场、西单商业中心。酒店位置优越，可将首都长安街壮观景致尽收眼底。', '北京 西城区 西长安街复兴门外大街19号', 'XiDan', 'Five', 1829373819, 4.5, 1, 'http://dimg11.c-ctrip.com/images/hotel/72000/71783/aac7d7c18fa243989344873453425103_R_550_412.jpg'),
       (2, '儒家酒店', '欢迎您入住', '南京市鼓楼区珠江路268号', 'XiDan', 'Four', 1829373819, 4.8, 6, null),
       (3, '秋果S1924酒店', '　秋果S1924酒店(北京五棵松店)处于北京海淀区五棵松商圈核心区域内，北临长安街，交通网络四通八达，毗邻解放军总医院及万事达体育馆，位于地铁1号线五棵松西南出口。', '北京 海淀区 复兴路32号院二区37号楼', 'XiDan', 'Four', 1829553719, 4.8, 1, 'http://dimg11.c-ctrip.com/images/200b13000000v8ft65653_R_550_412.jpg'),
       (21, '南泉酒店', '上海南泉大酒店地处繁华的浦东陆家嘴金融贸易中心区域内，上海新国际博览中心、上海浦东科技馆等都环绕周围。', '上海浦东新区南泉路899号', 'XiDan', 'Three', 1829373819, 4.2, 21, 'http://dimg11.c-ctrip.com/images/hotel/53000/52741/941abc93388f496cb660691cf8b48bde_R_550_412.jpg'),
       (22, '浦东丽思卡尔顿酒店', '上海浦东丽思卡尔顿酒店地处陆家嘴国金中心的顶端楼层，俯瞰黄浦江及外滩美景，距东方明珠电视塔、金茂大厦、上海环球金融中心、上海中心和上海海洋水族馆约500米, 信步可达。', '上海浦东新区陆家嘴世纪大道8号上海国金中心', 'PuDong', 'Five', 1829553719, 4.7, 21,'http://dimg13.c-ctrip.com/images/200h1c000001cwiaxDD13_R_550_412.jpg'),
       (23, '上海北外滩亚朵酒店', '上海北外滩亚朵酒店位于上海“黄金三角”之一的北外滩商圈，交通便利，商业配套完善，离地铁4号线、12号线大连路站1、5号口约350米，地铁约15分钟可到达外滩、城隍庙、南京路步行街、东方明珠、环球金融中心等知名景点，地铁7站可到达上海新国际博览中心，10站可达上海迪士尼乐园。', '上海杨浦区长阳路555号', 'PuDong', 'Four', 1829373819, 4.9, 21,'http://dimg12.c-ctrip.com/images/200v1b000001ajxrt0748_R_550_412.jpg'),
       (24, '上海万达瑞华酒店', '上海万达瑞华酒店坐落于闻名遐迩的上海外滩——中山东二路，外观及内部设计分别来自英国著名建筑事务所Foster+Partners和万达设计院，以Art Deco和海派元素作为主要设计风格。', '上海黄浦区中山东二路538号', 'PuDong', 'Five', 1829553719, 4.9, 21, 'http://dimg11.c-ctrip.com/images/200u0k000000c9j37749C_R_550_412.jpg'),
       (25, '上海豫园宜必思酒店', '上海豫园宜必思酒店位于昼锦路，酒店出门左行，便是享誉中外的豫园，其间近、现代商业店铺密布其间——南翔小笼、小杨生煎、上海老饭店的八宝鸭、油爆虾等传统风味数不胜数。出门往右，毗邻地铁10号线豫园站3号口，更有南京路、淮海路商业中心、新天地酒吧区、上海动物园等新上海人气聚集点，省去车马劳顿，让您近距离感受大上海风情。', ' 上海黄浦区昼锦路85号', 'PuDong', 'Three', 1829373819, 4.7, 21, 'http://dimg11.c-ctrip.com/images/hotel/80000/79899/1a19f217092b4b5cbca31fb8db600bbc_R_550_412.jpg'),
       (26, '上海虹桥机场佰翔花园酒店', '酒店集住宿、餐饮、休闲等服务为一体，设有百余间不同类型的精选客房，全日制餐厅、特色花园下午茶、中餐厅包厢等餐饮配套齐全，无论商务出行与旅游都能得到身体与心灵的放松。', '长宁区虹桥路2780号', 'PuDong', 'Four', 1829553719, 4.7, 21, 'http://dimg13.c-ctrip.com/images/20051b000001boo4n2CF1_R_550_412.jpg'),
       (27, '南京古南都饭店', '南京古南都饭店位于南京市的中心地带——新街口地区，与江苏省五台山体育中心隔街相，是一家豪华商务饭店。饭店附近集中了南京大学、南京师范大学、河海大学等全国著名高等学府及多家金融商贸以及南京市儿童医院，省人民医院、省中医院等机构；周边餐饮、娱乐、休闲及健身场所林立。饭店地理位置优越，人文气氛浓郁；交通便利，闹中取静。 ', '南京鼓楼区广州路208号', 'XinJieKou', 'FIve', 1829373819, 4.7, 22, 'http://dimg11.c-ctrip.com/images/200d070000002en2811DC_R_550_412.jpg'),
       (28, '南京琴川·熙舍酒店 ', '南京琴川·熙舍酒店，原建筑为明孝烈皇后故居，百年老宅翻建，位于热门景区老门东， 紧邻中华门、夫子庙、明城墙、大报恩寺，烟火喧嚣；院子内有颗近400年的古银杏，讲述历代繁荣。', '南京 秦淮区 大荷花巷19-1 ', 'XinJieKou', 'Four', 1829553719, 4.8, 22, 'http://dimg10.c-ctrip.com/images/200i1e000001f7ddz6FDE_R_550_412.jpg'),
       (101, '神仙酒店', '欢迎您入住', '南京市栖霞区珠江路268号', '1343296078', 'Four', 1829553719, 5, 6, null);
/*!40000 ALTER TABLE `Hotel`
    ENABLE KEYS */;
COMMIT;

--
-- Table structure for table `OrderList`
--

DROP TABLE IF EXISTS `OrderList`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OrderList`
(
    `id`           int(11) NOT NULL AUTO_INCREMENT,
    `userId`       int(11)        DEFAULT NULL,
    `hotelId`      int(11)        DEFAULT NULL,
    `hotelName`    varchar(255)   DEFAULT NULL,
    `checkInDate`  varchar(255)   DEFAULT NULL,
    `checkOutDate` varchar(255)   DEFAULT NULL,
    `roomType`     varchar(255)   DEFAULT NULL,
    `roomNum`      int(255)       DEFAULT NULL,
    `peopleNum`    int(255)       DEFAULT NULL,
    `haveChild`    tinytext,
    `createDate`   varchar(255)   DEFAULT NULL,
    `price`        decimal(65, 0) DEFAULT NULL,
    `clientName`   varchar(255)   DEFAULT NULL,
    `phoneNumber`  varchar(255)   DEFAULT NULL,
    `orderState`   varchar(255)   DEFAULT NULL,
    `rate`         int(11)        default NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 14
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OrderList`
--

BEGIN ;
/*!40000 ALTER TABLE `OrderList`
    DISABLE KEYS */;
INSERT INTO `OrderList`
VALUES (1, 5, 2, '儒家酒店', '2020-06-01', '2022-06-01', 'Family', 1, 2,1,'2020-06-01',123,'123','1231311','已预订',null),
(2, 5, 2, '儒家酒店', '2020-06-01', '2020-06-02', 'Family', 1, 2,1,'2020-06-01',123,'123','1231311','已预订',null),
(101, 5, 2, '儒家酒店', '2020-06-01', '2022-06-02', 'Family', 1, 2,1,'2020-06-01',123,'123','1231311','已预订',null),
(102, 5, 2, '儒家酒店', '2020-06-01', '2020-06-02', 'Family', 1, 2,1,'2020-06-01',123,'123','1231311','已预订',null),
(3, 5, 2, '儒家酒店', '2020-06-28', '2020-06-02', 'BigBed', 1, 2,1,'2020-06-01',500,'123','1231311','已预订',null),
(71, 5, 21, '南泉酒店', '2020-06-28', '2020-08-02', 'BigBed', 1, 2,1,'2020-06-01',500,'123','1231311','已入住',null),
(72, 5, 22, '浦东丽思卡尔顿酒店', '2020-06-28', '2020-08-02', 'DoubleBed', 1, 2,1,'2020-06-01',500,'123','1231311','已入住',null),
(73, 5, 23, '上海北外滩亚朵酒店店', '2020-06-28', '2020-08-02', 'DoubleBed', 1, 2,1,'2020-06-01',500,'123','1231311','已入住',null),
(74, 5, 24, '上海万达瑞华酒店', '2020-06-28', '2020-08-02', 'BigBed', 1, 2,1,'2020-06-01',500,'123','1231311','已入住',null),
(75, 5, 25, '上海豫园宜必思酒店', '2020-06-28', '2020-08-02', 'BigBed', 1, 2,1,'2020-06-01',500,'123','1231311','已入住',null),
(76, 5, 26, '上海虹桥机场佰翔花园酒店', '2020-06-28', '2020-08-02', 'Family', 1, 2,1,'2020-06-01',500,'123','1231311','已评价',3),
(77, 5, 27, '南京古南都饭店', '2020-06-28', '2020-08-02', 'Family', 1, 2,1,'2020-06-01',500,'123','1231311','已评价',4),
(78, 5, 28, '南京琴川·熙舍酒店 ', '2020-06-28', '2020-08-02', 'BigBed', 1, 2,1,'2020-06-01',500,'123','1231311','已评价',5)
;
/*!40000 ALTER TABLE `OrderList`
    ENABLE KEYS */;
COMMIT ;
--
-- Table structure for table `Room`
--

DROP TABLE IF EXISTS `Room`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Room`
(
    `id`       int(11) NOT NULL AUTO_INCREMENT,
    `price`    double      DEFAULT NULL,
    `curNum`   int(11)     DEFAULT NULL,
    `total`    int(11)     DEFAULT NULL,
    `hotel_id` int(11)     DEFAULT NULL,
    `roomType` varchar(50) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Room`
--

BEGIN;
/*!40000 ALTER TABLE `Room`
    DISABLE KEYS */;
INSERT INTO `Room`
VALUES (2, 199, 20, 20, 1, 'BigBed'),
       (3, 299, 30, 30, 1, 'DoubleBed'),
       (4, 399, 10, 10, 1, 'Family'),
       (6, 399, 10, 10, 2, 'Family'),
       (21, 327, 19, 20, 21, 'BigBed'),
       (22, 298, 30, 30, 21, 'DoubleBed'),
       (23, 499, 10, 10, 21, 'Family'),
       (24, 329, 10, 10, 22, 'Family'),
       (25, 212, 20, 20, 22, 'BigBed'),
       (26, 252, 29, 30, 22, 'DoubleBed'),
       (27, 345, 10, 10, 23, 'BigBed'),
       (28, 753, 10, 10, 23, 'Family'),
       (29, 569, 19, 20, 23, 'DoubleBed'),
       (30, 369, 30, 30, 24, 'DoubleBed'),
       (31, 479, 10, 10, 24, 'Family'),
       (32, 599, 9, 10, 24, 'BigBed'),
       (33, 462, 19, 20, 25, 'BigBed'),
       (34, 654, 30, 30, 25, 'DoubleBed'),
       (35, 879, 9, 10, 26, 'Family'),
       (36, 423, 9, 10, 27, 'Family'),
       (37, 212, 19, 20, 28, 'BigBed'),
       (38, 321, 30, 30, 29, 'DoubleBed');
/*!40000 ALTER TABLE `Room`
    ENABLE KEYS */;
COMMIT;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User`
(
    `id`          int(11)     NOT NULL AUTO_INCREMENT,
    `email`       varchar(255)   DEFAULT NULL,
    `password`    varchar(11) NOT NULL,
    `username`    varchar(255)   DEFAULT NULL,
    `phonenumber` varchar(255)   DEFAULT NULL,
    `credit`      double(255, 0) DEFAULT NULL,
    `usertype`    varchar(255)   DEFAULT NULL,
    `avatar`      varchar(1023)  DEFAULT NULL,
    `VIP`         int(11)        DEFAULT 0,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

BEGIN;
/*!40000 ALTER TABLE `User`
    DISABLE KEYS */;
INSERT INTO `User`
VALUES (1, '111@qq.com', '123456', NULL, NULL, NULL, 'HotelManager', NULL, 0),
       (4, '1012681@qq.com', '123456', '测试一号', '12345678919', 100, 'Client', NULL, 0),
       (5, '123@qq.com', '123456', '测试二号', '12345678911', 100, 'Client', NULL, 0),
       (6, '333@qq.com', '123456', NULL, NULL, NULL, 'HotelManager', NULL, 0),
       (10, '555@qq.com', '000000', 'Manager', '8003823823', NULL, 'Manager', NULL, 0),
       (8, '666@qq.com', '123456', 'Marketingstaff', NULL, NULL, 'Marketingstaff', NULL, 0),
       (21, '888@qq.com', '123456', 'PuDongHotelManager', '8003823823', NULL, 'HotelManager', NULL, 0),
       (22, '999@qq.com', '123456', 'XinJieKouHotelManager', '8003823823', NULL, 'HotelManager', NULL, 0);
/*!40000 ALTER TABLE `User`
    ENABLE KEYS */;
COMMIT;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2020-04-12 10:08:42
SET FOREIGN_KEY_CHECKS = 1;

/* edit by Hewenbing*/
DROP TABLE IF EXISTS `CreditChangeList`;
CREATE TABLE `CreditChangeList` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `operationDate` varchar(255) DEFAULT NULL,
  `orderId` int(11) DEFAULT NULL,
  `operation` varchar(255) DEFAULT NULL,
  `credit_delta` double(255,0) DEFAULT NULL,
  `credit_result` double(255,0) DEFAULT NULL,
  primary key (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/* edit by Hewenbing*/
DROP TABLE IF EXISTS `VIPCardList`;
CREATE TABLE `VIPCardList`
(
    `cardId`     int(11) NOT NULL AUTO_INCREMENT,
    `userId`     int(11)        DEFAULT NULL,
    `vip_credit` double(255, 0) DEFAULT NULL,
    `level`      int(11)        DEFAULT NULL,
    `card_type`  varchar(255)   DEFAULT NULL,
    `savings`    double(255, 0) DEFAULT NULL,
    `info`       varchar(255)   DEFAULT NULL,
    primary key (`cardId`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 6
  DEFAULT CHARSET = utf8;

/* edit by Hewenbing*/
DROP TABLE IF EXISTS `Voucher`;
CREATE TABLE `Voucher`
(
    `id`             int(11) not null auto_increment,
    `userId`         int(11)      default null,
    `description`    varchar(255) default null,
    `discount`       double       default 1,
    `target_money`   int(11)      default null,
    `discount_money` int(11)      default null,
    `number`         int(11)      default 0,
    `status`         varchar(255) default null,
    primary key (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 8
  DEFAULT CHARSET = utf8;

BEGIN;
/*!40000 ALTER TABLE `Voucher`
    DISABLE KEYS */;
INSERT INTO `Voucher`
VALUES (1,null,'新人优惠券',0.9,600,200,1,'未添加'),
       (2,null,'双十一优惠券',0.8,500,40,1,'未添加');
/*!40000 ALTER TABLE `Voucher`
    ENABLE KEYS */;
COMMIT;