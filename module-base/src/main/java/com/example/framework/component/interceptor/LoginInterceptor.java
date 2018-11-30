package com.example.framework.component.interceptor;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.app.RouteUtils;


/**
 * Created by Administrator on 2017/12/6 0006.
 * 添加拦截器的时候，建议clean再打包运行，不然会出现，无效的情况
 * <p>
 * 切记一个项目里面priority的值保证唯一，不然会出毛病
 * priority的值越小越先执行
 */
@Interceptor(priority = 1, name = "登录拦截")
public class LoginInterceptor implements IInterceptor {

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        if (postcard.getGroup().equals(GroupUtils.NEED_LOGIN)) {

            /**
             * 判断如果没有登录跳转到登录界面
             *
             */
            ARouter.getInstance().build(RouteUtils.User_RegisterActivity).navigation();

        } else {
            /**
             *
             * 拦截之后可以添加参数
             *
             */
            postcard.withString("extra", "我是在拦截器中附加的参数");
            callback.onContinue(postcard);

        }
    }

    @Override
    public void init(Context context) {

    }
}
