package io.quarkiverse.operatorsdk.runtime;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;

@ConfigRoot(name = "operator-sdk", phase = ConfigPhase.BUILD_AND_RUN_TIME_FIXED)
public class BuildTimeOperatorConfiguration {

    /**
     * Maps a controller name to its configuration.
     */
    @ConfigItem
    public Map<String, BuildTimeControllerConfiguration> controllers;

    /**
     * The optional CRD-related configuration options
     */
    @ConfigItem
    public CRDConfiguration crd;

    /**
     * Whether controllers should only process events if the associated resource generation has
     * increased since last reconciliation, otherwise will process all events. Sets the default value
     * for all controllers.
     */
    @ConfigItem(defaultValue = "true")
    public Optional<Boolean> generationAware;

    /**
     * Whether Role-Based Access Control (RBAC) resources should be generated in the kubernetes
     * manifests.
     */
    @ConfigItem(defaultValue = "false")
    public Boolean disableRbacGeneration;

    /**
     * Whether the operator should be automatically started or not. Mostly useful for testing
     * scenarios.
     */
    @ConfigItem
    public Optional<Boolean> startOperator;

    /**
     * Whether the injected Kubernetes client should be stopped when the operator is stopped.
     */
    @ConfigItem(defaultValue = "true")
    public Boolean closeClientOnStop;

    /**
     * Whether the operator should stop if an informer error (such as one caused by missing / improper
     * RBACs) occurs during startup.
     */
    @ConfigItem(defaultValue = "true")
    public Boolean stopOnInformerErrorDuringStartup;

    /**
     * Whether to fail or emit a debug-level (warning-level when misalignment is at the minor or above version level) log when
     * the extension detects that there are misaligned versions.
     * <p/>
     * The following versions are checked for alignment:
     * <ul>
     * <li>declared Quarkus version used to build the extension vs. actually used Quarkus version at runtime</li>
     * <li>Fabric8 client version used by JOSDK vs. actually used Fabric8 client version</li>
     * <li>Fabric8 client version used by Quarkus vs. actually used Fabric8 client version</li>
     * </ul>
     */
    @ConfigItem(defaultValue = "false")
    public Boolean failOnVersionCheck;

    /**
     * The list of profile names for which leader election should be activated. This is mostly useful for testing scenarios
     * where leader election behavior might lead to issues.
     */
    @ConfigItem(defaultValue = "prod")
    public List<String> activateLeaderElectionForProfiles;
}
