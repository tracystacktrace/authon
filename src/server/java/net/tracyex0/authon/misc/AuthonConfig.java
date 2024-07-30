package net.tracyex0.authon.misc;

import com.fox2code.foxloader.config.ConfigEntry;

public class AuthonConfig {

    @ConfigEntry(configName = "Amount of time (in seconds) to allow the player to login/register")
    public int waitingTime = 30;

    @ConfigEntry(configName = "Instantly kick on first faulty attempt")
    public boolean instantKick = true;

    @ConfigEntry(configName = "Allows IP sessions in a server")
    public boolean allowsSessions = false;

    /* Translation (localization) config fields */

    @ConfigEntry(configName = "local_login_notification")
    public String local_login_notification = "§6AuthOn: §bAuthorize in this server! /login <password>";

    @ConfigEntry(configName = "local_register_notification")
    public String local_register_notification = "§6AuthOn: §bRegister in this server! /register <password>";

    @ConfigEntry(configName = "local_auth_kick")
    public String local_auth_kick = "§cAuthOn: Too long! Try again?";

    @ConfigEntry(configName = "local_not_registered")
    public String local_not_registered = "§6AuthOn: §cYou're not registered! Register first!";

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

    @ConfigEntry(configName = "local_command_usage")
    public String local_command_usage = "§6AuthOn Usage: %s";

    @ConfigEntry(configName = "local_command_invalid")
    public String local_command_invalid = "§cInvalid syntax! Use %s";

    @ConfigEntry(configName = "local_session_success")
    public String local_session_success = "§6AuthOn: §aUsing session to auth, success!";

    @ConfigEntry(configName = "local_register_success")
    public String local_register_success = "§6AuthOn: §aSuccessfully registered! Welcome to the server!";

    @ConfigEntry(configName = "local_register_already")
    public String local_register_already = "§6AuthOn: §cAlready registered!";

    @ConfigEntry(configName = "local_changepwd_success")
    public String local_changepwd_success = "§6AuthOn: §aSuccessfully changed password!";

    @ConfigEntry(configName = "local_op_register_already")
    public String local_op_register_already = "§6AuthOn: §cThe user %s is already registered!";

    @ConfigEntry(configName = "local_op_register_success")
    public String local_op_register_success = "§6AuthOn: §aSuccessfully registered user %s!";

    @ConfigEntry(configName = "local_op_player_lack")
    public String local_op_player_lack = "§6AuthOn: §cPlayer not found!";

    @ConfigEntry(configName = "local_op_unregister_success")
    public String local_op_unregister_success = "§6AuthOn: §aSuccessfully unregistered user %s!";

    @ConfigEntry(configName = "local_op_changepwd_success")
    public String local_op_changepwd_success = "§6AuthOn: §aSuccessfully changed %s's password!";

    @ConfigEntry(configName = "local_bridge_kick_unregister")
    public String local_bridge_kick_unregister = "§cAuthOn: You have been unregistered!";
}
