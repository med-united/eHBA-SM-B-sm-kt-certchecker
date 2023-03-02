package health.medunited.pwdchanger.event;

import health.medunited.pwdchanger.config.UserConfigurations;

public class SaveSettingsEvent {
    private UserConfigurations userConfigurations;

    public SaveSettingsEvent() {

    }

    public SaveSettingsEvent(UserConfigurations userConfigurations) {
        setUserConfigurations(userConfigurations);
    }

    public UserConfigurations getUserConfigurations() {
        return this.userConfigurations;
    }

    public void setUserConfigurations(UserConfigurations userConfigurations) {
        this.userConfigurations = userConfigurations;
    }
}
