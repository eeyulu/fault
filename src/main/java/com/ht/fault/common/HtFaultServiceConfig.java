package com.ht.fault.common;

import com.ht.fault.common.interceptor.BaseInterceptor;
import com.ht.fault.common.interceptor.ExceptionIntoLogInterceptor;
import com.ht.fault.common.interceptor.SSOInterceptor;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.Const;
import com.jfinal.core.JFinal;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.template.Engine;
import com.jfinal.template.source.ClassPathSourceFactory;
import com.xxl.sso.core.util.JedisUtil;

/**
 * 本 demo 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法
 * 详见 JFinal 俱乐部: http://jfinal.com/club
 * 
 * API引导式配置	
 */
public  class HtFaultServiceConfig extends JFinalConfig {
	/**
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 * 
	 * 使用本方法启动过第一次以后，会在开发工具的 debug、run config 中自动生成
	 * 一条启动配置，可对该自动生成的配置再添加额外的配置项，例如 VM argument 可配置为：
	 * -XX:PermSize=64M -XX:MaxPermSize=256M
	 */
	public static void main(String[] args) {
		/**
		 * 特别注意：Eclipse 之下建议的启动方式
		 */
		JFinal.start("src/main/webapp", 8082, "/");
		
		/**
		 * 特别注意：IDEA 之下建议的启动方式，仅比 eclipse 之下少了最后一个参数
		 */
		// JFinal.start("src/main/webapp", 80, "/");
	}
	
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		
		// 加载少量必要配置，随后可用PropKit.get(...)获取值
		try {
			PropKit.use("a_little_config.txt");
			//默认10M,此处设置为最大100M
		    me.setMaxPostSize(10*Const.DEFAULT_MAX_POST_SIZE);
			me.setDevMode(PropKit.getBoolean("devMode", true));
//			me.setRenderFactory(new MyRenderFactory());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		
		try {
			me.add(new HtRoutes());//路由
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void configEngine(Engine me) {
//		me.addSharedFunction("/common/_layout.html");
//		me.addSharedFunction("/common/_paginate.html");
		
	}
	@Override
	public void configPlugin(Plugins me) {
		
		try {
			me.add(new EhCachePlugin());
			Prop p = PropKit.use("a_little_config.txt");
			// 配置 druid 数据库连接池插件
			DruidPlugin druidPlugin = new DruidPlugin(p.get("jdbcUrl"), p.get("user"), p.get("password").trim());
			//配置mysql驱动
			druidPlugin.setDriverClass("com.mysql.jdbc.Driver");
			me.add(druidPlugin);
			
			// 配置ActiveRecord插件
			ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
			// 配置数据库方言
			arp.setDialect(new MysqlDialect());
			arp.setShowSql(true);//这句话就是ShowSql
			// 配置属性名(字段名)大小写不敏感容器工厂
			arp.setContainerFactory(new CaseInsensitiveContainerFactory());
			
			Engine engine = arp.getEngine();
			engine.setSourceFactory(new ClassPathSourceFactory());
			//设置sql文件的路径
//	        arp.setBaseSqlTemplatePath("src/main/resources");
			//添加sql模板
			arp.addSqlTemplate("/template.sql");
			
			_MappingKit.mapping(arp);
			me.add(arp);
			
			//数据库（oracle）
			DruidPlugin dp2 = new DruidPlugin(p.get("oracleUrl"), p.get("oracleUser"), p.get("oraclePassword").trim());
			dp2.setDriverClass("oracle.jdbc.driver.OracleDriver");
			me.add(dp2); 
			ActiveRecordPlugin arp2 = new ActiveRecordPlugin("oracle",dp2);
			me.add(arp2);
			arp2.setDialect(new OracleDialect());
			arp2.setShowSql(true);
			arp2.setContainerFactory(new CaseInsensitiveContainerFactory());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	


	
	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		//me.addGlobalActionInterceptor(new LoginSessionInterceptor());
		me.addGlobalActionInterceptor(new BaseInterceptor());
		me.addGlobalActionInterceptor(new SSOInterceptor());
//		me.addGlobalActionInterceptor(new AuthInterceptor());
		me.add(new ExceptionIntoLogInterceptor());
	}
	
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		try {
			me.add(new HtFaultServiceHandler());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	
		
	}

	public void afterJFinalStart() {
		Prop p = PropKit.use("a_little_config.txt");
        // redis init
        JedisUtil.init(p.get("redisAddress"), p.get("redisPassword"));
	}
	
}
