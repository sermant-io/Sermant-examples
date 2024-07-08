package io.sermant.template;

import io.sermant.core.plugin.agent.declarer.AbstractPluginDeclarer;
import io.sermant.core.plugin.agent.declarer.InterceptDeclarer;
import io.sermant.core.plugin.agent.matcher.ClassMatcher;
import io.sermant.core.plugin.agent.matcher.MethodMatcher;

/**
 * 模板字节码增强声明
 *
 * @author luanwenfei
 * @since 2023-01-15
 */
public class TemplateDeclarer extends AbstractPluginDeclarer {
    @Override
    public ClassMatcher getClassMatcher() {
        // 匹配需要拦截的类，通过全限定名匹配
        return ClassMatcher.nameEquals("com.huaweicloud.template.Controller");
    }

    @Override
    public InterceptDeclarer[] getInterceptDeclarers(ClassLoader classLoader) {
        return new InterceptDeclarer[]{
                // 匹配需要拦截的方法，通过名称进行匹配
                InterceptDeclarer.build(MethodMatcher.nameEquals("sayHello"), new TemplateInterceptor())
        };
    }

    @Override
    public boolean isEnabled() {
        return super.isEnabled();
    }
}
