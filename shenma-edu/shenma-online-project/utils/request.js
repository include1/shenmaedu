import axios from 'axios'
import Cookie from 'js-cookie'
import {MessageBox} from "element-ui";
//创建axios实例

const service = axios.create({
  baseURL:'http://localhost:9000',//使用nginx(ip+端口)代理，请求转发
  timeout:10000//请求超时时间
})
//可以携带token
//service.defaults.withCredentials=true;
//请求拦截器
service.interceptors.request.use(
  config => {
    let token = Cookie.get("token")
    if (token) {
      config.headers['token'] = token // 让每个请求携带自定义token 请根据实际情况自行修改
      config.headers['uId'] = Cookie.get('uId')
    }
    return config
  },
  error => {
    // Do something with request error
    Promise.reject(error)
  }
)
//响应拦截其设置
service.interceptors.response.use(
  response=>{
     if(response.data.code === 20000||response.data.code === 40000){
       return response
     }
  }
)


export  default  service
