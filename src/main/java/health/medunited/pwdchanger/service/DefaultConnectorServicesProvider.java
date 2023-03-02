package health.medunited.pwdchanger.service;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import health.medunited.pwdchanger.config.UserConfig;
import health.medunited.pwdchanger.provider.AbstractConnectorServicesProvider;

@ApplicationScoped
public class DefaultConnectorServicesProvider extends AbstractConnectorServicesProvider {

    @Inject
    UserConfig userConfig;

    @PostConstruct
    void init() {
        initializeServices();
    }

    @Override
    public UserConfig getUserConfig() {
        return userConfig;
    }

}
