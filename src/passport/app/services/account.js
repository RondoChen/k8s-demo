const db = require('cloudoll').orm.mysql;
const tools = require('common-tools');
const errors = require('cloudoll').errors;
const myTools = require('../tools');

const me = module.exports = {

  register: async (regInfo0) => {
    var regInfo = {};
    regInfo.mobile = regInfo0.mobile;
    regInfo.email = regInfo0.email;
    regInfo.nick = regInfo0.nick;
    regInfo.password = regInfo0.password;
    regInfo.open_id = regInfo0.open_id;

    if (regInfo.mobile == '') {
      delete regInfo.mobile;
    }
    if (regInfo.email == '') {
      delete regInfo.email;
    }
    if (regInfo.nick == '') {
      delete regInfo.nick;
    }
    if (!regInfo.mobile && !regInfo.nick && !regInfo.email) {
      throw errors.WHAT_REQUIRE('手机，email或昵称');
    }

    if (regInfo.mobile && !tools.validateTools.isChinaMobile(regInfo.mobile)) {
      throw errors.CHINA_MOBILE_ILLEGAL;
    }
    if (regInfo.email && !tools.validateTools.isEmail(regInfo.email)) {
      throw errors.EMAIL_ILLEGAL;
    }
    if (regInfo.nick && (regInfo.nick.length < 3 || regInfo.length > 30)) {
      throw errors.WHAT_WRONG_LENGTH_RANGE('昵称', 3, 30);
    }
    if (regInfo.mobile) {
      var existMobile = await db.load("account", {
        where: 'mobile=?',
        params: [regInfo.mobile]
      });
      if (existMobile) {
        throw errors.WHAT_EXISTED("手机");
      }
    }

    if (regInfo.open_id) {
      var existMobile = await db.load("account", {
        where: 'open_id=?',
        params: [regInfo.open_id]
      });
      if (existMobile) {
        throw errors.WHAT_EXISTED("open_id");
      }
    }

    if (regInfo.email) {
      var existEmail = await db.load("account", {
        where: 'email=?',
        params: [regInfo.email]
      });
      if (existEmail) {
        throw errors.WHAT_EXISTED("邮箱");
      }
    }
    if (regInfo.nick) {
      var existNick = await db.load("account", {
        where: 'nick=?',
        params: [regInfo.nick]
      });
      if (existNick) {
        throw errors.WHAT_EXISTED("昵称");
      }

    }

    //regInfo.open_id = tools.stringTools.uuid(true);

    if (!regInfo.password) {
      regInfo.password = tools.stringTools.randomString(18);
    }

    var newPassword = tools.stringTools.genPassword(regInfo.password);
    regInfo.password = newPassword.password;
    regInfo.salt = newPassword.salt;

    var xid = await db.insert('account', regInfo);

    return await db.load("account", {
      where: "id=?",
      params: [xid.id],
      cols: "id, open_id, mobile, email, nick"
    });
    // delete regInfo.salt;
    // delete regInfo.password;
    // regInfo.id = xid.id;
    // regInfo.open_id = xid.open_id;

    // return regInfo;
  },
  loadByPassport: async (passport) => {
    if (!passport) {
      throw errors.WHAT_REQUIRE("帐号");
    }
    let where = 'nick=?';
    if (tools.validateTools.isEmail(passport)) {
      where = 'email=?';
    } else if (tools.validateTools.isChinaMobile(passport)) {
      where = 'mobile=?';
    }
    return await db.load("account", {
      where: where,
      params: [passport]
    });
  },
  loadById: async (id) => {
    var where = 'id=?';
    return await db.load("account", {
      where: where,
      params: [id]
    });
  },
  loadByOpenId: async (open_id) => {
    var where = 'open_id=?';
    let mine = await db.load("account", {
      where: where,
      params: [open_id]
    });
    delete mine.password;
    delete mine.salt;
    return mine;
  },
  loginByPassport: async (passport, password) => {
    if (!passport) {
      throw errors.WHAT_REQUIRE("帐号");
    }
    if (!password) {
      throw errors.WHAT_REQUIRE("密码");
    }
    var mine = await me.loadByPassport(passport);
    if (!mine) {
      throw errors.LOGIN_ERROR_BECAUSE('用户没找到，请重新输入登录凭证。');
    }

    var cptPassword = tools.stringTools.computePassword(password, mine.salt);

    if (cptPassword !== mine.password) {
      throw errors.LOGIN_ERROR;
    }
    delete mine.password;
    delete mine.salt;
    return mine;

  },
  checkPassportAvailable: async (passport) => {
    var mine = await me.loadByPassport(passport);
    if (mine) {
      throw errors.WHAT_EXISTED(passport);
    }
    return true;
  },

  getAllByTicket: async (ticket) => {
    if (!ticket) {
      throw errors.WHAT_REQUIRE("ticket");
    }
    var openId = myTools.getOpenId(ticket);

    var userInfo = await db.load("account", {
      where: "open_id=?",
      cols: ["id", "open_id", "account_type", "nick", "email", "mobile", "slogan", "avatar", "avatar_large"],
      params: [openId]
    });


    userInfo.devices = await db.take("device", {
      where: "account_id=?",
      params: [userInfo.id],
      cols: ["title", "sn", "mac", "code"]
    });
    return userInfo;
  },
  getInfoByTicket: async (ticket, pubKey) => {
    if (!ticket) {
      throw errors.WHAT_REQUIRE("ticket");
    }
    var openId = myTools.getOpenId(ticket, pubKey);

    var data = await db.load("account", {
      where: "open_id=?",
      cols: ["id", "open_id", "account_type", "nick", "email", "score", "mobile", "slogan", "avatar", "avatar_large"],
      params: [openId]
    });
    if (!data) {
      throw errors.WHAT_NOT_EXISTS('用户');
    }
    return data;
  },
  getInfoByOpenIds: async (openIds) => {
    if (!openIds || openIds.length <= 0) {
      throw errors.WHAT_REQUIRE("open_ids");
    }

    var data = await db.list("account", {
      where: "open_id in (?)",
      limit: 200,
      cols: ["id", "open_id", "account_type", "nick", "email", "mobile", "slogan", "avatar", "avatar_large"],
      params: [openIds]
    });
    return data;
  },
  grantGod: async (accountId) => {
    if (!accountId) {
      throw errors.WHAT_REQUIRE('id');
    }
    accountId = parseInt(accountId);
    var me = await db.loadById("account", accountId, ['id', 'account_type']);
    var updateData = { id: accountId };

    if ((me.account_type & 8) == 8) {
      updateData.account_type = updateData.account_type & ~8;
    } else {
      updateData.account_type = updateData.account_type | 8;
    }
    await db.update('account', updateData);
    return true;

  },
  update: async (regInfo) => {
    if (regInfo.mobile == '') {
      delete regInfo.mobile;
    }
    if (regInfo.email == '') {
      delete regInfo.email;
    }
    if (regInfo.nick == '') {
      delete regInfo.nick;
    }
    if (!regInfo.mobile && !regInfo.nick && !regInfo.email) {
      throw errors.WHAT_REQUIRE('手机，email或昵称');
    }

    if (regInfo.mobile) {
      if (!tools.validateTools.isChinaMobile(regInfo.mobile)) {
        throw errors.CHINA_MOBILE_ILLEGAL;
      }
    }
    if (regInfo.email) {
      if (!tools.validateTools.isEmail(regInfo.email)) {
        throw errors.EMAIL_ILLEGAL;
      }
    }
    if (regInfo.nick) {
      if (regInfo.nick.length < 3 || regInfo.length > 30) {
        throw errors.WHAT_WRONG_LENGTH_RANGE('昵称', 3, 30);
      }
    }

    if (regInfo.password && regInfo.password != "") {
      var newPassword = tools.stringTools.genPassword(regInfo.password);
      regInfo.password = newPassword.password;
      regInfo.salt = newPassword.salt;
    } else {
      delete regInfo.password;
    }

    //TODO: 这里没有去重
    delete regInfo.open_id;
    // delete regInfo.account_type;
    // delete regInfo.avatar;
    await db.update('account', regInfo);
    // if (!regInfo.id) {
    //   delete regInfo.id;
    // }
    delete regInfo.salt;
    delete regInfo.password;
    return regInfo;
  },
  changePassword: async (passport, oPass, nPass) => {
    var mine = await me.loadByPassport(passport);
    var cptPassword = tools.stringTools.computePassword(oPass, mine.salt);
    if (cptPassword !== mine.password) {
      throw errors.CUSTOM('原密码不对！如不记得原密码，请联系管理员');
    }
    const regInfo = { id: mine.id };

    var newPassword = tools.stringTools.genPassword(nPass);
    regInfo.password = newPassword.password;
    regInfo.salt = newPassword.salt;

    await db.update("account", regInfo);
    return true;

  }
};