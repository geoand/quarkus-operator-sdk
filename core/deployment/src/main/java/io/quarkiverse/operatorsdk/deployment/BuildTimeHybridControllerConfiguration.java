package io.quarkiverse.operatorsdk.deployment;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.jboss.jandex.AnnotationInstance;
import org.jboss.jandex.AnnotationValue;

import io.quarkiverse.operatorsdk.common.ConfigurationUtils;
import io.quarkiverse.operatorsdk.runtime.BuildTimeControllerConfiguration;
import io.quarkiverse.operatorsdk.runtime.BuildTimeOperatorConfiguration;

class BuildTimeHybridControllerConfiguration {

    private final BuildTimeOperatorConfiguration operatorConfiguration;
    private final BuildTimeControllerConfiguration externalConfiguration;
    private final AnnotationInstance controllerAnnotation;

    public BuildTimeHybridControllerConfiguration(
            BuildTimeOperatorConfiguration operatorConfiguration, BuildTimeControllerConfiguration externalConfiguration,
            AnnotationInstance controllerAnnotation) {
        this.operatorConfiguration = operatorConfiguration;
        this.externalConfiguration = externalConfiguration;
        this.controllerAnnotation = controllerAnnotation;
    }

    boolean generationAware() {
        return ConfigurationUtils.extract(
                externalConfiguration,
                controllerAnnotation, c -> c.generationAware,
                "generationAwareEventProcessing",
                AnnotationValue::asBoolean,
                () -> operatorConfiguration.generationAware.orElse(true));
    }

    Set<String> namespaces() {
        HashSet<String> namespaces = null;
        if (controllerAnnotation != null) {
            namespaces = Optional.ofNullable(controllerAnnotation.value("namespaces"))
                    .map(v -> new HashSet<>(Arrays.asList(v.asStringArray())))
                    .orElse(null);
        }

        if (externalConfiguration != null) {
            Optional<List<String>> overrideNamespaces = externalConfiguration.namespaces;
            if (overrideNamespaces.isPresent()) {
                namespaces = new HashSet<>(overrideNamespaces.get());
            }
        }

        return namespaces;
    }
}
