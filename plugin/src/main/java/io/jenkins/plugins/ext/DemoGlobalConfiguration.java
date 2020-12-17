package io.jenkins.plugins.ext;

import hudson.Extension;
import hudson.util.FormValidation;
import io.jenkins.plugins.ext.util.HttpUtils;
import jenkins.model.GlobalConfiguration;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

import java.io.IOException;

/**
 * @author huangchengqian
 * @date 2020-12-10 12:45
 **/
@Extension(ordinal = 101)
public final class DemoGlobalConfiguration extends
        GlobalConfiguration {

    private String domain;

    public DemoGlobalConfiguration() {
        this.load();
    }

    @DataBoundConstructor
    public DemoGlobalConfiguration(String domain) {
        this.domain = domain;
    }

    @DataBoundSetter
    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Override
    public boolean configure(StaplerRequest req, JSONObject formData)
            throws FormException {
        req.bindParameters(this);
        this.domain = formData.getString("domain");
        save();
        return super.configure(req, formData);
    }

    @Override
    public String getDisplayName() {
        return "Console output demo";
    }

    public String getDomain() {
        return domain;
    }

    public FormValidation doCheckDomain(
            @QueryParameter String domain) {
        try {
            HttpUtils.httpGet(domain + "/health");
        } catch (IOException e) {
            return FormValidation.error(e.getMessage());
        }
        return FormValidation.ok("domain validation successful");
    }

    public static DemoGlobalConfiguration get() {
        return GlobalConfiguration.all().get(DemoGlobalConfiguration.class);
    }
}