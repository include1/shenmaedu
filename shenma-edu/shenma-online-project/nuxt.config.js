module.exports = {
  /*
  ** Headers of the page
  */
  head: {
    title: '神马教育',
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      { hid: 'description', name: '神马教育', content: '神马教育' }
    ],
    link: [
      // { rel: 'icon', type: 'image/x-icon', href: 'favicon.ico' }
    ]
  },
  /*
  ** Customize the progress bar color
  */
  loading: { color: '#3B8070' },
  /*
  ** Build configuration
  */
  build: {
    /*
    ** Run ESLint on save
    */
    vendor: ['element-ui'],
    extend (config, { isDev, isClient }) {
      if (isDev && isClient) {
        config.module.rules.push({
          enforce: 'pre',
          test: /\.(js|vue)$/,
          loader: 'eslint-loader',
          exclude: /(node_modules)/
        })
      }
    }
  },
  //swiper-plugin
  plugins:[
    {src:'~/plugins/nuxt-swiper-plugin.js',ssr:false},
    {src:'~/plugins/element-ui',ssr:false}

  ],
  css:[
    'swiper/dist/css/swiper.css',
    'element-ui/lib/theme-chalk/index.css'
  ],

}

