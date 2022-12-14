const tools = require("../../tools");
const accountService = require('../../services/account');
const doll = require('cloudoll');

/*
 * 无状态的登录管理
 *
 */
var AccountStateless = {
  init: async ctx => {
    const pwd = ctx.qs.password || tools.genRdmStr(8);
    const info = {
      email: "admin@arche.cloud",
      mobile: "13000000000",
      nick: "admin",
      password: pwd
    }
    const userInfo = await accountService.register(info);
    await accountService.grantGod(userInfo.id);
    info.tips = "请记住您的密码，使用 email，手机或者昵称，任意一个即可登录。";
    ctx.body = (info);
  },
  $checkPassport: async ctx => {
    const form = ctx.request.body;
    const passport = form.passport;
    ctx.body = (await accountService.checkPassportAvailable(passport));
  },
  $login: async ctx => {
    const form = ctx.request.body;
    const passport = form.passport;
    const password = form.password;
    const expires_in = form.expires_in;

    const mine = await accountService.loginByPassport(passport, password);

    const openId = mine.open_id;
    const tick = tools.makeTicket(openId, expires_in, ctx.app.config);
    tick.nick = mine.nick;
    tick.open_id = openId;
    tick.accout_type = mine.account_type;
    tick.avatar = mine.avatar;
    // tick.tenants = await accountService.listMyTenants(mine.id);
    ctx.body = (tick)
  },
  $register: async ctx => {
    const form = ctx.request.body;
    const mine = await accountService.register(form);
    const openId = mine.open_id;

    const tick = tools.makeTicket(openId, null, ctx.app.config);
    tick.nick = mine.nick;
    tick.open_id = openId;
    ctx.body = (tick);
  },
  refreshTicket: async ctx => {
    const qs = ctx.qs;
    const ticket = qs.ticket;

    // console.log(ticket);
    const openId = tools.getOpenId(ticket, ctx.app.config.account.public_key);
    const tick = tools.makeTicket(openId, null, ctx.app.config);
    ctx.body = (tick);
  },
  info: async (ctx) => {
    const qs = ctx.qs;
    const ticket = qs.ticket;

    let rtn = await accountService.getInfoByTicket(ticket, ctx.app.config.account.public_key);
    // rtn.tenants = await accountService.listMyTenants(rtn.id);
    ctx.body = (rtn);
  },
  devices: async (ctx) => {
    const qs = ctx.qs;
    const ticket = qs.ticket;
    const rtn = await accountService.getDevicesByTicket(ticket, ctx.app.config.account.public_key);
    ctx.body = (rtn);
  },
  rights: async (ctx) => {
    const qs = ctx.qs;
    const ticket = qs.ticket;
    const service = qs.service;
    const rtn = await accountService.getRightsByTicket(ticket, service, ctx.app.config.account.public_key);
    ctx.body = (rtn);

  },
  $changePassword: async (ctx) => {
    const form = ctx.request.body;
    const rtn = await accountService.changePassword(form.passport, form.password, form.new_password);
    ctx.body = (rtn);
  }
};


module.exports = AccountStateless;