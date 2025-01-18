/*==============================================================*/
/* Database name:  device_management                            */
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2025/1/16 11:53:02                           */
/*==============================================================*/


drop database if exists device_management;

/*==============================================================*/
/* Database: device_management                                  */
/*==============================================================*/
create database device_management;

use device_management;

/*==============================================================*/
/* Table: device                                                */
/*==============================================================*/
create table device
(
   id                   BIGINT not null comment '主键ID',
   name                 VARCHAR(100) not null comment '设备名称',
   code                 VARCHAR(50) not null comment '编号(IMEI)',
   secret               VARCHAR(100) comment '密码',
   sn                   VARCHAR(100) comment 'SN号',
   iccid                VARCHAR(100) comment '卡号',
   parent_sn            VARCHAR(100) default '0' comment '父设备SN',
   category             VARCHAR(50) comment '类别',
   group_id             BIGINT comment '组别ID',
   strategy_id          BIGINT comment '策略ID',
   status               VARCHAR(20) default '禁用' comment '状态(启用/禁用)',
   terminal             INT default 26 comment '终端',
   create_time          DATETIME comment '创建时间',
   update_time          DATETIME comment '更新时间',
   remark               TEXT comment '备注',
   primary key (id),
   unique key uk_code (code)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备表';

/*==============================================================*/
/* Table: device_group                                          */
/*==============================================================*/
create table device_group
(
   id                   BIGINT not null comment '主键ID',
   name                 VARCHAR(100) not null comment '组名称',
   parent_id            BIGINT default 0 comment '父组ID',
   create_time          DATETIME comment '创建时间',
   update_time          DATETIME comment '更新时间',
   primary key (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备组表';

/*==============================================================*/
/* Table: device_log                                            */
/*==============================================================*/
create table device_log
(
   id                   BIGINT not null comment '主键ID',
   device_id            BIGINT not null comment '设备ID',
   device_code          VARCHAR(50) not null comment '设备编号',
   log_time             DATETIME not null comment '日志时间',
   signal_strength      INT comment '信号强度',
   voltage              DECIMAL(10,2) comment '电压',
   count                INT comment '计数',
   status               VARCHAR(20) comment '状态',
   type                 VARCHAR(50) comment '类型',
   target               VARCHAR(100) comment '目标',
   create_time          DATETIME comment '创建时间',
   work_order_id        bigint,
   primary key (id),
   key idx_device_code (device_code),
   key idx_log_time (log_time)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备日志表';

/*==============================================================*/
/* Table: device_register                                       */
/*==============================================================*/
create table device_register
(
   id                   BIGINT not null auto_increment comment '主键ID',
   device_id            BIGINT not null comment '设备ID',
   data_type            VARCHAR(50) comment '数据类型',
   endpoint             VARCHAR(200) comment '接口地址',
   account              VARCHAR(100) comment '账号',
   token                VARCHAR(200) comment 'Token',
   create_time          DATETIME comment '创建时间',
   primary key (id),
   key idx_device_id (device_id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备注册表';

/*==============================================================*/
/* Table: strategy                                              */
/*==============================================================*/
create table strategy
(
   id                   BIGINT not null auto_increment comment '主键ID',
   name                 VARCHAR(100) not null comment '策略名称',
   start_time           TIME comment '开始时间',
   end_time             TIME comment '结束时间',
   light_min            INT comment '亮度最小值',
   light_max            INT comment '亮度最大值',
   status               VARCHAR(20) default '启用' comment '状态',
   create_time          DATETIME comment '创建时间',
   update_time          DATETIME comment '更新时间',
   primary key (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='策略表';

/*==============================================================*/
/* Table: work_order                                            */
/*==============================================================*/
create table work_order
(
   id                   BIGINT not null auto_increment comment '主键ID',
   device_id            BIGINT not null comment '设备ID',
   device_code          VARCHAR(50) not null comment '设备编号',
   node                 VARCHAR(100) comment '节点',
   content              TEXT comment '内容',
   status               VARCHAR(20) comment '状态(新工单/处理中/完成/历史)',
   create_time          DATETIME comment '创建时间',
   update_time          DATETIME comment '更新时间',
   primary key (id),
   key idx_device_code (device_code),
   key idx_status (status)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工单表';

alter table device add constraint FK_Reference_7 foreign key (group_id)
      references device_group (id) on delete restrict on update restrict;

alter table device add constraint FK_Reference_8 foreign key (strategy_id)
      references strategy (id) on delete restrict on update restrict;

alter table device_group add constraint fk_group_parent foreign key (parent_id)
      references device_group (id) on delete cascade on update cascade;

alter table device_log add constraint FK_Reference_9 foreign key (work_order_id)
      references work_order (id) on delete restrict on update restrict;

alter table device_log add constraint fk_log_device foreign key (device_id)
      references device (id) on delete cascade on update cascade;

alter table device_log add constraint fk_log_device_code foreign key (device_code)
      references device (code) on delete cascade on update cascade;

alter table device_register add constraint fk_register_device foreign key (device_id)
      references device (id) on delete cascade on update cascade;

alter table work_order add constraint fk_order_device foreign key (device_id)
      references device (id) on delete cascade on update cascade;

alter table work_order add constraint fk_order_device_code foreign key (device_code)
      references device (code) on delete cascade on update cascade;

