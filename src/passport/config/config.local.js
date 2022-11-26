module.exports = {
  debug: true,
  mysql: {
    connectionLimit: 10,
    host: process.env.DB_HOST || '127.0.0.1',
    user: process.env.DB_USERNAME || 'root',
    port: 3306,
    password: process.env.DB_PASSWORD || 'password',
    database: process.env.DB_DATABASE || 'passport'
  },
}