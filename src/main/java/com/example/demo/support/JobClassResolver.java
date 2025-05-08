package com.example.demo.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

public class JobClassResolver {

    public static Class<?> resolveJobClass(String jobName, String basePackage) {
        Set<BeanDefinition> candidates = getCandidates(basePackage);
        List<Class<?>> matchingClasses = findMatchingClasses(candidates, jobName);

        validateMatchingClasses(matchingClasses, jobName);

        return matchingClasses.get(0); // 유일한 클래스를 반환
    }

    private static Set<BeanDefinition> getCandidates(String basePackage) {
        ClassPathScanningCandidateComponentProvider scanner =
            new ClassPathScanningCandidateComponentProvider(false);

        scanner.addIncludeFilter(new AnnotationTypeFilter(JobConfiguration.class));
        return scanner.findCandidateComponents(basePackage);
    }

    private static List<Class<?>> findMatchingClasses(Set<BeanDefinition> candidates, String jobName) {
        List<Class<?>> matchingClasses = new ArrayList<>();

        for (BeanDefinition candidate : candidates) {
            try {
                Class<?> clazz = loadClass(candidate);

                JobConfiguration annotation = clazz.getAnnotation(JobConfiguration.class);
                if (annotation != null && annotation.name().equalsIgnoreCase(jobName)) {
                    matchingClasses.add(clazz);
                }

            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Job Config Class가 존재하지 않습니다: " + candidate.getBeanClassName(), e);
            }
        }

        return matchingClasses;
    }

    private static Class<?> loadClass(BeanDefinition candidate) throws ClassNotFoundException {
        String className = candidate.getBeanClassName();
        return Class.forName(className);
    }

    private static void validateMatchingClasses(List<Class<?>> matchingClasses, String jobName) {
        if (matchingClasses.size() > 1) {
            throw new IllegalStateException("중복된 Job Config가 존재합니다: @JobConfiguration(name=\"" + jobName + "\")");
        }

        if (matchingClasses.isEmpty()) {
            throw new IllegalArgumentException("일치하는 Job Config가 존재하지 않습니다: @JobConfiguration(name=\"" + jobName + "\")");
        }
    }
}
