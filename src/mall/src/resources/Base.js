const resources = {
  "zh-CN": {
    SiteName: "玩具商店",
    Manage: "管理",
    Copyright: "版权所有 © 腾讯云容器产品中心 2021 | 京ICP备12007230号-3",
    LabelConfirmDelete: "您是否真的要删除？选择”确认/OK“删除记录。",
    TextDemoCaution: "请注意：这个电商程序是腾讯云训练营演示商店，不提供真实交易。",
    LabelNotShow: "下次不显示此警告"
  },
  "en": {
    SiteName: "Doll Shop",
    Manage: "Manage",
    Copyright: "Tencent Cloud © CloudNative Center 2021 | 京ICP备12007230号-3",
    TextDemoCaution: "Attention: This shop is an demo for Tencent CloudNative Camp",
    LabelNotShow: "Don't show next time"
  },
  R: (lang, res) => {
    if (!resources.hasOwnProperty(lang)) {
      resources[lang] = {};
    }
    Object.assign(resources[lang], res);
  }
};

/*** Label */
resources.R("zh-CN", {
  LabelCreate: "新建",
  LabelModify: "修改",
  LabelDelete: "删除",
  LabelQuery: "查询",
  LabelDetail: "详情",
  LabelSave: "保存",
  LabelPublish: "发表",
  LabelCancel: "取消",
  LabelReturn: "返回",
  LabelClose: "关闭",
  LabelCreateWhat: "新建%s",
  LabelModifyWhat: "修改%s",
  LabelDeleteWhat: "删除%s",
  LabelQueryWhat: "查询%s",
  LabelGreeting: "%s 您好！",
  LabelDefaultNick: "同学",
  LabelConfirmDelete: "真的要删除吗？"
});
resources.R("en", {
  LabelCreate: "Create",
  LabelModify: "Modify",
  LabelDelete: "Delete",
  LabelQuery: "Query",
  LabelDetail: "Detail",
  LabelSave: "Save",
  LabelPublish: "Publish",
  LabelCancel: "Cancel",
  LabelReturn: "Return",
  LabelClose: "Close",
  LabelCreateWhat: "Create %s",
  LabelModifyWhat: "Modify %s",
  LabelDeleteWhat: "Delete %s",
  LabelQueryWhat: "Query %s",
  LabelGreeting: "Hi %s",
  LabelDefaultNick: "Dude",
  LabelConfirmDelete: "Do you really wanna delete?"
});


/**** Menu Block */
resources.R("zh-CN", {
  MenuLogout: "登出",
  MenuLogin: "登录",
  MenuShopcart: "购物车",
  MenuProfile: "我的",
  MenuHome: "首页",
  Menu3C: "电子产品",
  MenuQQFamily: "QQFamily",
  MenuQQDoll: "QQ 公仔",
  MenuOffice: "办公用品",
  MenuGame: "游戏周边",
});

resources.R("en", {
  MenuLogout: "Logout",
  MenuLogin: "Login",
  MenuShopcart: "Shopcart",
  MenuProfile: "Profile",
  MenuHome: "Home",
  Menu3C: "3C",
  MenuQQFamily: "QQFamily",
  MenuQQDoll: "QQ Doll",
  MenuOffice: "Office",
  MenuGame: "Game Collection",
});

/** Error */
resources.R("zh-CN", {
  ErrorNeedChooseOneObject: "必须选择一个条目。",
});

resources.R("en", {
  ErrorNeedChooseOneObject: "At least one record should be chosen.",
});



export default resources;