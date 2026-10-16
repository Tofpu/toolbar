package com.tofpu.toolbar.core;

import com.tofpu.toolbar.core.util.PlatformVersion;
import com.tofpu.toolbar.api.PlatformMetadata;

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
