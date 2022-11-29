const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function (app) {
  app.use(
    createProxyMiddleware('/api/passport', {
      target: process.env.SERVICE_PASSPORT,
      // target: 'http://127.0.0.1:5000',
      changeOrigin: true,
      ws: false,
      secure: false,
      pathRewrite: {
        '^/api/passport': '/'
      }
    })
  );


  app.use(
    createProxyMiddleware('/api/product', {
      target: process.env.SERVICE_PRODUCT,
      changeOrigin: true,
      ws: false,
      secure: false,
      pathRewrite: {
        '^/api/product': '/'
      }
    })
  );

  app.use(
    createProxyMiddleware('/api/review', {
      target: process.env.SERVICE_REVIEW,
      changeOrigin: true,
      ws: false,
      secure: false,
      pathRewrite: {
        '^/api/review': '/'
      }
    })
  );

  app.use(
    createProxyMiddleware('/api/shopcart', {
      // target: 'http://www.xyz-shop.com:6000',
      target: process.env.SERVICE_SHOPCART,
      changeOrigin: true,
      ws: false,
      secure: false,
      pathRewrite: {
        '^/api/shopcart': '/'
      }
    })
  );

  app.use(
    createProxyMiddleware('/api/order', {
      target: process.env.SERVICE_ORDER,
      changeOrigin: true,
      ws: false,
      secure: false,
      pathRewrite: {
        '^/api/order': '/'
      }
    })
  );
}