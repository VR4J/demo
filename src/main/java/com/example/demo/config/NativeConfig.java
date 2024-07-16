package com.example.demo.config;

import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeHint;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.thymeleaf.expression.Lists;

@Configuration
@ImportRuntimeHints(NativeConfig.class)
public class NativeConfig implements RuntimeHintsRegistrar {

    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        hints.reflection().registerType(Lists.class, TypeHint.builtWith(MemberCategory.INVOKE_PUBLIC_METHODS));
    }
}
