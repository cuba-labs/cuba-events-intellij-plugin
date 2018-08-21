package org.strangeway.cubaevents;

import com.intellij.usages.Usage;

public interface Filter {
    boolean shouldShow(Usage usage);
}