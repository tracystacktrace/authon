package net.tracyex0.authon.misc;

import com.fox2code.foxloader.config.ConfigEntry;

/**
 * :3
 */
public class AuthonConfig {

    @ConfigEntry(configName = "Amount of warn messages before kicking the player")
    public int warnInterval = 5;

    @ConfigEntry(configName = "Amount of time (in seconds) to allow the player to register")
    public int registationTime = 30;

    @ConfigEntry(configName = "Instantly kick on first faulty attempt")
    public boolean instantKick = true;

    @ConfigEntry(configName = "Allow an experimental (actually deprecated) caching method")
    public boolean dbCache = true;

    /* Translation (localization) config fields */

    @ConfigEntry(configName = "local_already_logged")
    public String local_already_logged = "&cError: Already logged-in!";

    @ConfigEntry(configName = "local_not_logged")
    public String local_not_logged = "&cError: Not logged in!";

    @ConfigEntry(configName = "local_already_reg")
    public String local_already_reg = "&cError: Username is already registered!";

    @ConfigEntry(configName = "local_not_registered")
    public String local_not_registered = "&cError: Username is not registered!";

    @ConfigEntry(configName = "local_change_pwd")
    public String local_change_pwd = "&cInfo: Password successfully changed!";

    @ConfigEntry(configName = "local_session_success")
    public String local_session_success = "&cInfo: Logged in by latest session data!";

    @ConfigEntry(configName = "local_login_success")
    public String local_login_success = "&cInfo: Successful login!";

    @ConfigEntry(configName = "local_register_success")
    public String local_register_success = "&cInfo: Successful registration! Welcome to our server!";

    @ConfigEntry(configName = "local_unregister_success")
    public String local_unregister_success = "&cInfo: Successfully unregistered the user!";

    @ConfigEntry(configName = "local_password_wrong")
    public String local_password_wrong = "&cError: Wrong password!";

    @ConfigEntry(configName = "local_logout_success")
    public String local_logout_success = "&cInfo: Successfully logged out!";


    @ConfigEntry(configName = "local_register_ask")
    public String local_register_ask = "&cPlease, register by using /register <password>";
    
    @ConfigEntry(configName = "local_login_ask")
    public String local_login_ask = "&cPlease, log in by using /login <password>";
}
