const doll = require('cloudoll');

class AppBootHook {
  constructor(app) {
    this.app = app;
  }

  configWillLoad() {
    const mysql = doll.orm.mysql;
    mysql.mysql = require('mysql');
    mysql.debug = this.app.config.debug;
    mysql.connect(this.app.config.mysql);
  }
}

module.exports = AppBootHook;