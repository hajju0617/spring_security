
-- 테이블 greengram2024_security.feed 구조 내보내기
CREATE TABLE IF NOT EXISTS `feed` (
                                      `feed_id` bigint(20) NOT NULL AUTO_INCREMENT,
    `writer_id` bigint(20) NOT NULL,
    `contents` varchar(1000) DEFAULT NULL,
    `location` varchar(30) DEFAULT NULL,
    `created_at` datetime NOT NULL DEFAULT current_timestamp(),
    `updated_at` datetime DEFAULT NULL ON UPDATE current_timestamp(),
    PRIMARY KEY (`feed_id`) USING BTREE
    ) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 테이블 데이터 greengram2024_security.feed:~37 rows (대략적) 내보내기
INSERT INTO `feed` (`feed_id`, `writer_id`, `contents`, `location`, `created_at`, `updated_at`) VALUES


-- 테이블 greengram2024_security.feed_comment 구조 내보내기
CREATE TABLE IF NOT EXISTS `feed_comment` (
                                              `feed_comment_id` bigint(20) NOT NULL AUTO_INCREMENT,
    `feed_id` bigint(20) NOT NULL,
    `user_id` bigint(20) NOT NULL,
    `COMMENT` varchar(1000) NOT NULL,
    `created_at` datetime NOT NULL DEFAULT current_timestamp(),
    `updated_at` datetime DEFAULT NULL ON UPDATE current_timestamp(),
    PRIMARY KEY (`feed_comment_id`) USING BTREE
    ) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 테이블 데이터 greengram2024_security.feed_comment:~20 rows (대략적) 내보내기
INSERT INTO `feed_comment` (`feed_comment_id`, `feed_id`, `user_id`, `COMMENT`, `created_at`, `updated_at`) VALUES

-- 테이블 greengram2024_security.feed_favorite 구조 내보내기
CREATE TABLE IF NOT EXISTS `feed_favorite` (
                                               `feed_id` bigint(20) NOT NULL,
    `user_id` bigint(20) NOT NULL,
    `created_at` datetime NOT NULL DEFAULT current_timestamp(),
    PRIMARY KEY (`feed_id`,`user_id`) USING BTREE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 테이블 데이터 greengram2024_security.feed_favorite:~40 rows (대략적) 내보내기
INSERT INTO `feed_favorite` (`feed_id`, `user_id`, `created_at`) VALUES

-- 테이블 greengram2024_security.feed_pics 구조 내보내기
CREATE TABLE IF NOT EXISTS `feed_pics` (
                                           `feed_pic_id` bigint(20) NOT NULL AUTO_INCREMENT,
    `feed_id` bigint(20) NOT NULL,
    `pic` varchar(50) NOT NULL,
    `created_at` datetime NOT NULL DEFAULT current_timestamp(),
    PRIMARY KEY (`feed_pic_id`) USING BTREE
    ) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 테이블 데이터 greengram2024_security.feed_pics:~56 rows (대략적) 내보내기
INSERT INTO `feed_pics` (`feed_pic_id`, `feed_id`, `pic`, `created_at`) VALUES

-- 테이블 greengram2024_security.user 구조 내보내기
CREATE TABLE IF NOT EXISTS `user` (
                                      `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
    `provider_type` varchar(10) NOT NULL DEFAULT 'LOCAL',
    `uid` varchar(20) NOT NULL,
    `upw` varchar(100) DEFAULT NULL,
    `nm` varchar(20) NOT NULL,
    `pic` varchar(100) DEFAULT NULL,
    `created_at` datetime NOT NULL DEFAULT current_timestamp(),
    `updated_at` datetime DEFAULT NULL ON UPDATE current_timestamp(),
    PRIMARY KEY (`user_id`) USING BTREE,
    UNIQUE KEY `uid` (`uid`) USING BTREE
    ) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 테이블 데이터 greengram2024_security.user:~9 rows (대략적) 내보내기
INSERT INTO `user` (`user_id`, `provider_type`, `uid`, `upw`, `nm`, `pic`, `created_at`, `updated_at`) VALUES


-- 테이블 greengram2024_security.user_follow 구조 내보내기
CREATE TABLE IF NOT EXISTS `user_follow` (
                                             `from_user_id` bigint(20) NOT NULL,
    `to_user_id` bigint(20) NOT NULL,
    `created_at` datetime NOT NULL DEFAULT current_timestamp(),
    PRIMARY KEY (`from_user_id`,`to_user_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 테이블 데이터 greengram2024_security.user_follow:~5 rows (대략적) 내보내기
INSERT INTO `user_follow` (`from_user_id`, `to_user_id`, `created_at`) VALUES


