package net.tracyex0.authon.misc;

import com.fox2code.foxloader.config.ConfigEntry;

/**
 * :3
 */
public class AuthonConfig {

    @ConfigEntry(configName = "Amount of warn messages before kicking the player")
    public int warnInterval = 5;

    @ConfigEntry(configName = "Amount of time (in seconds) to allow the player to login/register")
    public int waitingTime = 30;

    @ConfigEntry(configName = "Instantly kick on first faulty attempt")
    public boolean instantKick = true;

    /* Translation (localization) config fields */

    @ConfigEntry(configName = "local_login_notification")
    public String local_login_notification = "§6AuthOn: §bAuthorize in this server! /login <password>";

    @ConfigEntry(configName = "local_register_notification")
    public String local_register_notification = "§6AuthOn: §bRegister in this server! /register <password>";

    @ConfigEntry(configName = "local_auth_kick")
    public String local_auth_kick = "§cAuthOn: Too long! Try again?";

    @ConfigEntry(configName = "local_not_registered")
    public String local_not_registered = "§6AuthOn: §cYou\'re not registered! Register first!";

    @ConfigEntry(configName = "local_login_already")
    public String local_login_already = "§6AuthOn: §cAlready logged in!";

    @ConfigEntry(configName = "local_login_success")
    public String local_login_success = "§6AuthOn: §aSuccessfully logged in! Welcome back!";

    @ConfigEntry(configName = "local_db_unexpected")
    public String local_db_unexpected = "§6AuthOn: §cA DB access error occurred while processing!";

    @ConfigEntry(configName = "local_password_wrong")
    public String local_password_wrong = "§6AuthOn: §cWrong password!";

    @ConfigEntry(configName = "local_password_short")
    public String local_password_short = "§6AuthOn: §cPassword too short!";
}
