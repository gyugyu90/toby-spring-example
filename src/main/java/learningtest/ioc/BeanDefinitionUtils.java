package learningtest.ioc;

import org.springframework.context.support.GenericApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class BeanDefinitionUtils {
    public static void printBeanDefinitions(GenericApplicationContext gac) {
        List<List<String>> roleBeanInfos = new ArrayList<>();
        roleBeanInfos.add(new ArrayList<>());
        roleBeanInfos.add(new ArrayList<>());
        roleBeanInfos.add(new ArrayList<>());

        for (String name : gac.getBeanDefinitionNames()) {
            int role = gac.getBeanDefinition(name).getRole();
            List<String> beanInfos = roleBeanInfos.get(role);
            beanInfos.add(role + "\t" + name + "\t" + gac.getBean(name).getClass().getName());
        }

        for (List<String> beanInfos : roleBeanInfos) {
            for (String beanInfo : beanInfos) {
                System.out.println(beanInfo);
            }
        }
    }

}
