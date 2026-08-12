package com.heavenscode.rac.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Rac.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link tech.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final Liquibase liquibase = new Liquibase();

    private final MobileAppWebhook mobileAppWebhook = new MobileAppWebhook();

    private final MobileAppInvoicePdf mobileAppInvoicePdf = new MobileAppInvoicePdf();

    // jhipster-needle-application-properties-property

    public Liquibase getLiquibase() {
        return liquibase;
    }

    public MobileAppWebhook getMobileAppWebhook() {
        return mobileAppWebhook;
    }

    public MobileAppInvoicePdf getMobileAppInvoicePdf() {
        return mobileAppInvoicePdf;
    }

    // jhipster-needle-application-properties-property-getter

    public static class Liquibase {

        private Boolean asyncStart;

        public Boolean getAsyncStart() {
            return asyncStart;
        }

        public void setAsyncStart(Boolean asyncStart) {
            this.asyncStart = asyncStart;
        }
    }

    public static class MobileAppWebhook {

        private String url;

        private String key;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }

    public static class MobileAppInvoicePdf {

        private String filePath;

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }
    }
    // jhipster-needle-application-properties-property-class
}
