CREATE TABLE `chat_record`  (
                                `id` bigint(0) NOT NULL AUTO_INCREMENT,
                                `user_id` bigint(0) NOT NULL,
                                `message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
                                `role` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                                `create_time` datetime(0) NULL DEFAULT NULL,
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

CREATE TABLE user_profile
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    user_id BIGINT NOT NULL,

    nickname VARCHAR(64),

    gender VARCHAR(16),

    personality VARCHAR(64),

    speaking_style VARCHAR(64),

    interests TEXT,

    ai_summary TEXT,

    create_time DATETIME,

    update_time DATETIME
);
