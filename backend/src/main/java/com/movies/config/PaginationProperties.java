package com.movies.config;

import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * A configuration class for pagination properties.
 * <p>
 * This class loads pagination-related configuration from properties files.
 * The properties are prefixed with "pagination".
 */
@Component
@ConfigurationProperties(prefix = "pagination")
public class PaginationProperties {

    /**
     * The default page size for pagination.
     */
    @Min(1)
    private int defaultPageSize = 10;

    /**
     * Gets the default page size for pagination.
     *
     * @return the default page size
     */
    public int getDefaultPageSize() {
        return defaultPageSize;
    }

    /**
     * Sets the default page size for pagination.
     *
     * @param defaultPageSize the default page size to set
     */
    public void setDefaultPageSize(int defaultPageSize) {
        this.defaultPageSize = defaultPageSize;
    }
}
