const doll = require("cloudoll");
const url = require("url");
const accountService = require('../services/account');

module.exports = _ => {

  const auth = async (ctx, next) => {
    let urls = url.parse(ctx.url);
    let authCode = urls.pathname;
    authCode = authCode.toLowerCase();

    const ticket = ctx.qs.ticket || (ctx.request.body && ctx.request.body.ticket);
    if (ticket) {
      ctx.user = await accountService.getInfoByTicket(ticket, ctx.app.config.account.public_key);
    }

    delete ctx.request.body.ticket
    delete ctx.request.body.tenant_id

    if (authCode.indexOf('/admin') == 0 || authCode.indexOf('/tenant') == 0) {
      if (!ticket) {
        throw doll.errors.WHAT_REQUIRE("ticket");
      }
    }

    if (authCode.indexOf('/admin') == 0) {
      if ((ctx.user.account_type & 8) == 8) {
        await next();
      } else {
        throw doll.errors.NO_RIGHTS;
      }
    } else {
      await next();
    }
  };
  return auth;
};