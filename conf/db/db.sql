-- ------------------------
-- Table structure for note
-- ------------------------
DROP TABLE IF EXISTS `note`;
CREATE TABLE `note` -- 笔记信息表
(
    `id`          VARCHAR(32)  NOT NULL, -- 笔记id
    `title`       VARCHAR(256) NOT NULL, -- 笔记标题
    `content`     TEXT    DEFAULT NULL,  -- 笔记内容，TEXT值是一个文本字符串，使用数据库编码（UTF-8、UTF-16BE 或 UTF-16LE）存储
    `weight`      INTEGER DEFAULT 0,     -- 笔记权重，会优先根据权重排序
    `del_flag`    CHAR(1) DEFAULT '0',   -- 删除标识，0-正常，1-删除
    `create_time` INTEGER DEFAULT 0,     -- 创建时间（时间戳）
    `update_time` INTEGER DEFAULT 0,     -- 修改时间（时间戳）
    PRIMARY KEY (`id`)
);


-- -------------------------
-- Table structure for event
-- -------------------------
DROP TABLE IF EXISTS `event`;
CREATE TABLE `event` -- 事件信息表
(
    `id`          VARCHAR(32)  NOT NULL,     -- 事件id
    `title`       VARCHAR(256) NOT NULL,     -- 事件标题
    `url`         VARCHAR(256) DEFAULT '',   -- 事件链接地址
    `content`     TEXT         DEFAULT NULL, -- 事件内容
    `start_time`  INTEGER      NOT NULL,     -- 事件开始时间（时间戳）
    `end_time`    INTEGER      NOT NULL,     -- 事件结束时间（时间戳）
    `del_flag`    CHAR(1)      DEFAULT '0',  -- 删除标识，0-正常，1-删除
    `create_time` INTEGER      DEFAULT 0,    -- 创建时间（时间戳）
    `update_time` INTEGER      DEFAULT 0,    -- 修改时间（时间戳）
    PRIMARY KEY (`id`)
);

CREATE INDEX idx_event_startTime ON event (`start_time`);

/*
CREATE INDEX idx_event_startTime ON event (`startTime`);
DROP INDEX idx_event_startTime;
PRAGMA INDEX_LIST('event');
*/

-- -------------------------
-- Table structure for gantt
-- -------------------------
DROP TABLE IF EXISTS `gantt`;
CREATE TABLE `gantt` -- 甘特图信息表
(
    `id`          VARCHAR(32)  NOT NULL,     -- 甘特图id
    `name`        VARCHAR(256) NOT NULL,     -- 甘特图名称
    `desc`        VARCHAR(256) DEFAULT '',   -- 甘特图描述
    `values`      TEXT         DEFAULT NULL, -- 甘特图值集合
    `content`     TEXT         DEFAULT NULL, -- 甘特图内容
    `weight`      INTEGER      DEFAULT 0,    -- 甘特图权重，会优先根据权重排序
    `del_flag`    CHAR(1)      DEFAULT '0',  -- 删除标识，0-正常，1-删除
    `create_time` INTEGER      DEFAULT 0,    -- 创建时间（时间戳）
    `update_time` INTEGER      DEFAULT 0,    -- 修改时间（时间戳）
    PRIMARY KEY (`id`)
);

