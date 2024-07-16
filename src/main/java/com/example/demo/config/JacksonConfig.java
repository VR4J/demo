package com.example.demo.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.SneakyThrows;
import org.springframework.aot.hint.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@ImportRuntimeHints(JacksonConfig.class)
public class JacksonConfig implements RuntimeHintsRegistrar {

    private static final String ROOT_PACKAGE = "com.example.demo";
    private static final String PACKAGE_SEPARATOR = ".";
    private static final String FOLDER_SEPARATOR = "/";

    @Override
    public void registerHints(RuntimeHints hints, ClassLoader loader) {
        List<TypeReference> builders = getClasses(loader, ROOT_PACKAGE).stream()
                .filter(clazz -> clazz.getAnnotationsByType(JsonPOJOBuilder.class).length > 0)
                .map(TypeReference::of)
                .toList();

        hints
                .reflection()
                .registerTypes(builders, TypeHint.builtWith(MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.INVOKE_PUBLIC_METHODS))
                .registerType(JsonGenerator.class, MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS, MemberCategory.INVOKE_PUBLIC_METHODS);
    }

    public Set<Class<?>> getClasses(ClassLoader loader, String name) {
        InputStream stream = loader.getResourceAsStream(
                name.replaceAll("[" + PACKAGE_SEPARATOR + "]", FOLDER_SEPARATOR)
        );

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        List<String> lines = reader.lines().toList();

        Stream<Class<?>> current = lines.stream()
                .filter(this::isClassFile)
                .map(line -> getClass(line, name));

        Stream<Class<?>> nested = lines.stream()
                .filter(this::isPackageFolder)
                .map(String::trim)
                .map(child -> setChildPackageName(name, child))
                .map(pName -> getClasses(loader, pName))
                .flatMap(Set::stream);

        return Stream.concat(current, nested).collect(Collectors.toSet());
    }

    @SneakyThrows
    private Class<?> getClass(String className, String packageName) {
        return Class.forName(packageName + PACKAGE_SEPARATOR + className.substring(0, className.lastIndexOf(PACKAGE_SEPARATOR)));
    }

    private boolean isClassFile(String path) {
        return path.endsWith(".class");
    }

    private boolean isPackageFolder(String path) {
        return ! isClassFile(path);
    }

    private String setChildPackageName(String parent, String child) {
        return parent + PACKAGE_SEPARATOR + child;
    }
}
