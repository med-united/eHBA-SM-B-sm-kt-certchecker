package health.medunited.pwdchanger.config;

import health.medunited.pwdchanger.config.UserConfigurations;

public class UserConfigurationsUpdateEvent {

    private final UserConfigurations configurations;

    public UserConfigurationsUpdateEvent(UserConfigurations configurations) {
        this.configurations = configurations;
    }

    public UserConfigurations getConfigurations() {
        return configurations;
    }
}
