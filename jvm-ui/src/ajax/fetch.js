/* jshint esversion: 6 */
/**
 *Axios and backend service data interaction layer encapsulation
 *It provides three interaction modes: fetch, get and post, and the underlying layer calls Axios method to realize it
 */
import axios from 'axios'
import {Notification} from 'element-ui'

const $ = axios.create({
  headers: {
    'X-Requested-With': 'XMLHttpRequest',
    'Content-type': 'application/json;charset=utf-8',
  },
  withCredentials: true
});
var pathConfig = {
  remotePath : "http://localhost:8888"
}
const hintTime = 1500
export default {
  fetch (url, option) {
    $.request({
      url: pathConfig.remotePath+url,
      method: option['type'] || 'get',
      data: option['data'] || {},
      params: option['data'] || {},
      responseType: option['dataType'] || 'json'
    })
    .then((response) => {
      let res = response.data
      if (typeof (res.error) === 'object') res.error = res.error['msg']
      if (res.ret === -1) {
        return Notification.error({title: 'Tips', message: res.msg, duration: hintTime})
      }
      if (option['success']) {
        option['success'](res.data)
      }
    })
    .catch((e) => {
      if (option['error']) {
        option['error'](e)
      } else {
        Notification.error({title: 'Tips', message: 'Service error, please try again later', duration: hintTime})
      }
    })
  },
  get (url, params, callback,isAllResult) {
    $.get( pathConfig.remotePath+url, {params: params})
      .then((response) => {
        this.success(response, callback,isAllResult)
      })
      .catch((e) => {
        Notification.error({title: 'Error', message: 'Service error, please try again later', duration: hintTime})
      })
  },
  post (url, data, callback, callback_error,isAllResult) {
    // 在浏览器中使用axios post请求时,data数据是form data
    $.post( pathConfig.remotePath+url, JSON.stringify(data))
      .then((response) => {
        this.success(response, callback, callback_error,isAllResult)
      })
      .catch((e) => {
        Notification.error({title: 'Error', message: 'Service error, please try again later', duration: hintTime})
      })
  },
  success (response, callback, callback_error,isAllResult) {
    let res = response.data;

    if (res.ret === -1) {
      Notification.error({title: 'Tips', message: res.error!=null ? error:res.msg , duration: hintTime})
      if (callback_error) return callback_error('callback_error')
    }

    if (callback) {
      if(isAllResult){
        callback(res)
      }else{
        callback(res.data)
      }
    }
  }
}
