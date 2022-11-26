module.exports = {
  appName: 'passport',
  groupName: 'xyz',
  middleware: ['auth'],
  debug: false,
  security: {
    csrf: {
      enable: false,
    },
  },
  keys: "whocares",
  address: {
    max_count: 200
  },
  account: {
    expire_in: 7200, //默认过期时间 24 小时,
    public_key: 'iloveyou_XYZ_cloud666', //这个是加密的key，无状态的 ticket
    dynamic_password_expire_in: 60000 //默认过期时间
  }
}