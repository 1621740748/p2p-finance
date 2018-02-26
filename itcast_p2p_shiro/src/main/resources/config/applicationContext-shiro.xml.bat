<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:aop="http://www.springframework.org/schema/aop"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:jdbc="http://www.springframework.org/schema/jdbc" xmlns:tx="http://www.springframework.org/schema/tx"
	xmlns:jpa="http://www.springframework.org/schema/data/jpa" xmlns:task="http://www.springframework.org/schema/task"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.1.xsd">


	<bean id="shiroFilter" class="org.apache.shiro.spring.web.ShiroFilterFactoryBean">
		<property name="securityManager" ref="securityManager" />
		<property name="loginUrl" value="/login"></property>
		<property name="successUrl" value="/index.html"></property>
		<property name="unauthorizedUrl" value="/error.jsp"></property>
		<property name="filterChainDefinations">
			<value>
				/login.jsp*=anon
				/js*=anon
				/error.jsp*=anon
				/user/login*=anon
				/book/find*=perms["find"]
				/book/add*=roles["admin"]
				/success.jsp*=authc
			</value>
		</property>
	</bean>

	<bean id="securityManager" class="org.apache.shiro.web.mgt.DefaultWebSecurityManager">
		<property name="realm" ref="p2pRealm"></property>
	</bean>

	<bean id="lifecycle" class="org.apache.shiro.spring.LifecycleBeanPostProcessor"/>
	<bean
		class="org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator"
		depends-on="lifecycle">
		<property name="proxyTargetClass" value="true"></property>
	</bean>
	<bean
		class="org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor">
		<property name="securityManager" ref="securityManager" />
	</bean>

	<bean id="p2pRealm" class="cn.itcast.realm.P2pRealm"></bean>
</beans>