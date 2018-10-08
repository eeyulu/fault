package com.ht.fault.common.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.xxl.sso.core.conf.Conf;
import com.xxl.sso.core.user.XxlUser;
import com.xxl.sso.core.util.SsoLoginHelper;

/**
 * 
 * 请求参数处理
 */
public class SSOInterceptor implements Interceptor {

	public void intercept(Invocation inv) {
		Controller controller = inv.getController();
		controller.getResponse().setHeader("Access-Control-Allow-Origin", "*");

		HttpServletRequest req = controller.getRequest();
		HttpServletResponse res = controller.getResponse();
		
		String ssoServer = PropKit.get("ssoServer");;
        String link = req.getRequestURL().toString();
        // login filter
        XxlUser xxlUser = null;

        // valid cookie user
        String cookieSessionId = SsoLoginHelper.getSessionIdByCookie(req);
        xxlUser = SsoLoginHelper.loginCheck(cookieSessionId);

        // valid param user, client login
        if (xxlUser == null) {

            // remove old cookie
            SsoLoginHelper.removeSessionIdInCookie(req, res);

            // set new cookie
            String paramSessionId = req.getParameter(Conf.SSO_SESSIONID);
            if (paramSessionId != null) {
                xxlUser = SsoLoginHelper.loginCheck(paramSessionId);
                if (xxlUser != null) {
                    SsoLoginHelper.setSessionIdInCookie(res, paramSessionId);
                }
            }
        }

        // valid login fail
        if (xxlUser == null) {
            // redirect logout
            String loginPageUrl = ssoServer.concat(Conf.SSO_LOGIN)
                    + "?" + Conf.REDIRECT_URL + "=" + link;

            try {
				res.sendRedirect(loginPageUrl);
			} catch (IOException e) {
				e.printStackTrace();
			}
            inv.getController().renderJson();
            return;
        }

        // ser sso user
        req.setAttribute(Conf.SSO_USER, xxlUser);

        // already login, allow
		inv.invoke();
	

	}

}
