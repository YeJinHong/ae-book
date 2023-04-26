module.exports = {
  devServer: {
      proxy: {
          '/api': {
              target: 'http://localhost:8082', // 개발서버
              changeOrigin: true
          }
      }
  }
}