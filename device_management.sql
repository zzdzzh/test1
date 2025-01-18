-- 创建数据库
CREATE DATABASE IF NOT EXISTS device_management DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE device_management;

-- 设备表
CREATE TABLE IF NOT EXISTS device (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    name VARCHAR(100) NOT NULL COMMENT '设备名称',
    code VARCHAR(50) NOT NULL COMMENT '编号(IMEI)',
    secret VARCHAR(100) COMMENT '密码',
    sn VARCHAR(100) COMMENT 'SN号',
    iccid VARCHAR(100) COMMENT '卡号',
    parent_sn VARCHAR(100) DEFAULT '0' COMMENT '父设备SN',
    category VARCHAR(50) COMMENT '类别',
    group_id BIGINT COMMENT '组别ID',
    strategy_id BIGINT COMMENT '策略ID',
    status VARCHAR(20) DEFAULT '禁用' COMMENT '状态(启用/禁用)',
    terminal INT DEFAULT 26 COMMENT '终端',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    remark TEXT COMMENT '备注',
    PRIMARY KEY (id),
    UNIQUE KEY uk_code (code),
    CONSTRAINT fk_device_group FOREIGN KEY (group_id) REFERENCES device_group (id) ON DELETE SET NULL ON UPDATE CASCADE,
    CONSTRAINT fk_device_strategy FOREIGN KEY (strategy_id) REFERENCES strategy (id) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备表';

-- 设备组表
CREATE TABLE IF NOT EXISTS device_group (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    name VARCHAR(100) NOT NULL COMMENT '组名称',
    parent_id BIGINT DEFAULT 0 COMMENT '父组ID',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    PRIMARY KEY (id),
    CONSTRAINT fk_group_parent FOREIGN KEY (parent_id) REFERENCES device_group (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备组表';

-- 策略表
CREATE TABLE IF NOT EXISTS strategy (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    name VARCHAR(100) NOT NULL COMMENT '策略名称',
    start_time TIME COMMENT '开始时间',
    end_time TIME COMMENT '结束时间',
    light_min INT COMMENT '亮度最小值',
    light_max INT COMMENT '亮度最大值',
    status VARCHAR(20) DEFAULT '启用' COMMENT '状态',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='策略表';

-- 设备日志表
CREATE TABLE IF NOT EXISTS device_log (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    device_id BIGINT NOT NULL COMMENT '设备ID',
    device_code VARCHAR(50) NOT NULL COMMENT '设备编号',
    log_time DATETIME NOT NULL COMMENT '日志时间',
    signal_strength INT COMMENT '信号强度',
    voltage DECIMAL(10,2) COMMENT '电压',
    count INT COMMENT '计数',
    status VARCHAR(20) COMMENT '状态',
    type VARCHAR(50) COMMENT '类型',
    target VARCHAR(100) COMMENT '目标',
    create_time DATETIME COMMENT '创建时间',
    PRIMARY KEY (id),
    INDEX idx_device_code (device_code),
    INDEX idx_log_time (log_time),
    CONSTRAINT fk_log_device FOREIGN KEY (device_id) REFERENCES device (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_log_device_code FOREIGN KEY (device_code) REFERENCES device (code) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备日志表';

-- 工单表
CREATE TABLE IF NOT EXISTS work_order (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    device_id BIGINT NOT NULL COMMENT '设备ID',
    device_code VARCHAR(50) NOT NULL COMMENT '设备编号',
    node VARCHAR(100) COMMENT '节点',
    content TEXT COMMENT '内容',
    status VARCHAR(20) COMMENT '状态(新工单/处理中/完成/历史)',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    PRIMARY KEY (id),
    INDEX idx_device_code (device_code),
    INDEX idx_status (status),
    CONSTRAINT fk_order_device FOREIGN KEY (device_id) REFERENCES device (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_order_device_code FOREIGN KEY (device_code) REFERENCES device (code) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工单表';

-- 设备注册表
CREATE TABLE IF NOT EXISTS device_register (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    device_id BIGINT NOT NULL COMMENT '设备ID',
    data_type VARCHAR(50) COMMENT '数据类型',
    endpoint VARCHAR(200) COMMENT '接口地址',
    account VARCHAR(100) COMMENT '账号',
    token VARCHAR(200) COMMENT 'Token',
    create_time DATETIME COMMENT '创建时间',
    PRIMARY KEY (id),
    INDEX idx_device_id (device_id),
    CONSTRAINT fk_register_device FOREIGN KEY (device_id) REFERENCES device (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备注册表'; 