package learningtest.jdk.proxy;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.util.PatternMatchUtils;

public class NameMatchClassMethodPointcut extends NameMatchMethodPointcut {

    public void setMappedClassName(String mappedClassName) {
        // 모든 클래스를 다 허용하던 디폴트 클래스 필터를
        // 프로퍼티로 받은 클래스 이름을 이용해서 필터를 만들어 덮어씌운다.
        this.setClassFilter(new SimpleClassFilter(mappedClassName));
    }

    static class SimpleClassFilter implements ClassFilter {

        String mappedName;

        private SimpleClassFilter(String mappedName) {
            this.mappedName = mappedName;
        }

        @Override
        public boolean matches(Class<?> aClass) {
            // matches => *name, name*, *name*
            return PatternMatchUtils.simpleMatch(mappedName, aClass.getSimpleName());
        }
    }

}
