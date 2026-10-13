package io.github.tofpu;

import io.github.tofpu.util.PlatformVersion;
import io.tofpu.toolbar.PlatformMetadata;

public class PlatformMetadataImpl implements PlatformMetadata {
    public static final PlatformVersion.NumberedVersion MINIMUM_DUAL_WIELDING_VERSION = PlatformVersion.NumberedVersion.ofMajor(9);
    private final PlatformVersion platformVersion;

    public PlatformMetadataImpl(PlatformVersion platformVersion) {
        this.platformVersion = platformVersion;
    }

    @Override
    public boolean supportsDualWielding() {
        return platformVersion.is(PlatformVersion.Comparison.GREATER_THAN_OR_EQUAL, MINIMUM_DUAL_WIELDING_VERSION);
    }
}
