// 运行时配置

import imgUrl from '@/assets/logo.png';

// 全局初始化数据配置，用于 Layout 用户信息和权限初始化
// 更多信息见文档：https://umijs.org/docs/api/runtime-config#getinitialstate

import Footer from '@/components/layout/footer/Footer';
import UserController from '@/services/user/UserController';
import { getToken, setToken } from '@/utils/token';
import { message } from 'antd';
import { AxiosResponse } from 'axios';

export async function getInitialState() {
  if (
    location.pathname ===
    (process.env.NODE_ENV === 'production' ? '/admin/login' : '/login')
  ) {
    // 强行跳登录页
    const token = getToken();
    if (token) {
      const result = await UserController.getUserInfoApi();
      if (result) {
        // 不仅有 token，而且 token 是有效的，那就直接跳转到首页
        message.warning('请先退出后在登录').then(() => {
          location.href =
            process.env.NODE_ENV === 'production' ? '/admin' : '/';
        });
      }
    }
  } else {
    // 强行要跳内部页面，得看看 token 有没有效
    const res = await UserController.getUserInfoApi();

    const data = res.data;

    if (data) {
      // 说明有 token，并且 token 有效

      return {
        name: data.nickname,
        avatar: data.avatar,
        adminInfo: data,
      };
    } else {
      // token 验证失败，跳转至登录
      localStorage.removeItem('adminToken');

      location.href =
        process.env.NODE_ENV === 'production' ? '/admin/login' : '/login';
      message.warning('请重新登录');
    }
  }
}

export const layout = () => {
  return {
    logo: imgUrl,
    footerRender: () => <Footer />,
    menu: {
      locale: false,
    },
    logout: () => {
      // 删除本地 token
      localStorage.removeItem('adminToken');
      // 跳转到登录页面
      location.href = '/admin/login';
      message.success('退出登录成功');
    },
  };
};

// 配置请求响应拦截器
export const request = {
  timeout: 5000,
  // 请求拦截器
  requestInterceptors: [
    function (url: string, options: any) {
      // 从本地获取 token
      const token = getToken();
      if (token) {
        options.headers['Authorization'] = 'Bearer ' + token;
      }
      return { url, options };
    },
  ],
  // 响应拦截器
  responseInterceptors: [
    async function (response: AxiosResponse) {
      if (response.headers.authorization) {
        setToken(response.headers.authorization);
      }
      return response;
    },
  ],
};
