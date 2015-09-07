/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2008-9-5 21:40:06                            */
/*==============================================================*/


drop table if exists admin;

drop table if exists cart;

drop table if exists cartselectedmer;

drop table if exists category;

drop table if exists member;

drop table if exists memberlevel;

drop table if exists merchandise;

drop table if exists news;

drop table if exists newscolumns;

drop table if exists newsrule;

drop table if exists orders;

drop table if exists traffic;

/*==============================================================*/
/* Table: admin                                                 */
/*==============================================================*/
create table admin
(
   ID                   int not null auto_increment,
   LoginName            varchar(20),
   LoginPwd             varchar(50),
   `Privileges`         varchar(50),
   primary key (ID)
)
comment='系统管理员表';

/*==============================================================*/
/* Table: cart                                                  */
/*==============================================================*/
create table cart
(
   ID                   int not null auto_increment,
   MemberID             int,
   Money                decimal(9,2),
   CartStatus           int,
   primary key (ID)
)
comment='购物车表';

/*==============================================================*/
/* Table: cartselectedmer                                       */
/*==============================================================*/
create table cartselectedmer
(
   ID                   int not null auto_increment,
   CartID               int,
   MerchandiseID        int,
   Number               int,
   Price                decimal(8,2),
   Money                decimal(9,2),
   primary key (ID)
)
comment='购物车商品选购记录表';

/*==============================================================*/
/* Table: category                                              */
/*==============================================================*/
create table category
(
   ID                   int not null auto_increment,
   ParentID             int,
   CateName             varchar(50),
   primary key (ID)
)
comment='商品分类表';

/*==============================================================*/
/* Table: member                                                */
/*==============================================================*/
create table member
(
   ID                   int not null auto_increment,
   MemberlevelID        int,
   LoginName            varchar(20),
   LoginPwd             varchar(50),
   MemberName           char(12),
   Phone                varchar(40),
   Address              varchar(100),
   Zip                  char(6),
   RegDate              datetime,
   LastDate             datetime,
   LoginTimes           int,
   EMail                varchar(100),
   Integral             int,
   primary key (ID)
)
comment='注册会员表';

/*==============================================================*/
/* Table: memberlevel                                           */
/*==============================================================*/
create table memberlevel
(
   ID                   int not null auto_increment,
   LevelName            varchar(20),   
   Integral           int,
   Favourable           int,
   primary key (ID)
)
comment='会员级别表';

/*==============================================================*/
/* Table: merchandise                                           */
/*==============================================================*/
create table merchandise
(
   ID                   int not null auto_increment,
   CategoryID           int,
   MerName              varchar(50),
   Price                decimal(8,2),
   SPrice               decimal(8,2),
   MerModel             varchar(40),
   Picture              varchar(100),
   Video                varchar(100),
   MerDesc              text,
   Manufacturer         varchar(50),
   LeaveFactoryDate     datetime,
   Special              int,
   HtmlPath             varchar(100),
   Status               int,
   primary key (ID)
)
comment='商品表';

/*==============================================================*/
/* Table: news                                                  */
/*==============================================================*/
create table news
(
   ID                   int not null auto_increment,
   ColumnsID            int,
   Title                varchar(50),
   Content              text,
   Abstract             varchar(400),
   KeyWords             varchar(200),
   IsPicNews            int,
   Picture              varchar(100),
   `From`               varchar(40),
   CDate                datetime,
   Author               char(12),
   Editor               char(12),
   Clicks               int,
   NewsType             int,
   Priority             int,
   red                  int,
   HtmlPath             varchar(100),
   Status               int,
   primary key (ID)
)
comment='资讯表';

/*==============================================================*/
/* Table: newscolumns                                           */
/*==============================================================*/
create table newscolumns
(
   ID                   int not null auto_increment,
   ParentID             int,
   ColumnCode           varchar(20),
   ColumnName           varchar(40),
   primary key (ID)
)
comment='资讯栏目表';

/*==============================================================*/
/* Table: newsrule                                              */
/*==============================================================*/
create table newsrule
(
   ID                   int not null auto_increment,
   ColumnsID            int,
   RuleName             varchar(100),
   Encode               char(10),
   URL                  varchar(100),
   MidEnd               text,
   MidBegin             text,
   ContentEnd           text,
   ContentBegin         text,
   FromEnd              text,
   FromBegin            text,
   AuthorEnd            text,
   AuthorBegin          text,
   TitleEnd             text,
   TitleBegin           text,
   ListEnd              text,
   ListBegin            text,
   primary key (ID)
)
comment='新闻采集规则表';

/*==============================================================*/
/* Table: orders                                                */
/*==============================================================*/
create table orders
(
   ID                   int not null auto_increment,
   MemberID             int,
   CartID               int,
   OrderNO              varchar(20),
   OrderDate            datetime,
   OrderStatus          int,
   primary key (ID)
)
comment='订单表';

/*==============================================================*/
/* Table: traffic                                               */
/*==============================================================*/
create table traffic
(
   ID                   int not null auto_increment,
   SourceURL            varchar(200),
   TargetURL            varchar(200),
   IP                   varchar(20),
   Area                 varchar(40),
   VisitDate            datetime,
   primary key (ID)
)
comment='流量统计表';

alter table cart add constraint FK_member_to_cart foreign key (MemberID)
      references member (ID) on delete restrict on update restrict;

alter table cartselectedmer add constraint FK_cart_to_cartselectedmer foreign key (CartID)
      references cart (ID) on delete restrict on update restrict;

alter table cartselectedmer add constraint FK_merchandise_to_cartselectedmer foreign key (MerchandiseID)
      references merchandise (ID) on delete restrict on update restrict;

alter table category add constraint FK_category_to_category foreign key (ParentID)
      references category (ID) on delete restrict on update restrict;

alter table member add constraint FK_memberlevel_to_member foreign key (MemberlevelID)
      references memberlevel (ID) on delete restrict on update restrict;

alter table merchandise add constraint FK_category_to_merchandise foreign key (CategoryID)
      references category (ID) on delete restrict on update restrict;

alter table news add constraint FK_newscolumns_to_news foreign key (ColumnsID)
      references newscolumns (ID) on delete restrict on update restrict;

alter table newscolumns add constraint FK_newscolumns_to_newscolumns foreign key (ParentID)
      references newscolumns (ID) on delete restrict on update restrict;

alter table newsrule add constraint FK_newscolumns_to_newsrule foreign key (ColumnsID)
      references newscolumns (ID) on delete restrict on update restrict;

alter table orders add constraint FK_member_to_orders foreign key (MemberID)
      references member (ID) on delete restrict on update restrict;

alter table orders add constraint FK_orders_to_cart foreign key (CartID)
      references cart (ID) on delete restrict on update restrict;

insert into admin(LoginName,LoginPwd,`Privileges`) values('admin','21232f297a57a5a743894a0e4a801fc3','10000000000000000000');

