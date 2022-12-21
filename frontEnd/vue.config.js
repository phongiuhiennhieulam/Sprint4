const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  configureWebpack: {
    devtool: 'source-map'
  },
  devServer: {
    allowedHosts: 'all',
    host: 'localhost',
    port: 8080,
  }
})

