package net.tracystacktrace.authon.config;

import com.fox2code.foxloader.config.ConfigEntry;

public class AuthonConfig {

    @ConfigEntry(configComment = "Amount of time (in seconds) to allow the player to login/register")
    public int waitingTime = 30;

    @ConfigEntry(configComment = "The minimal number of chars for the password. Should be 8 or more")
    public int minPassLength = 8;

    @ConfigEntry(configComment = "Instantly kick on first faulty attempt")
    public boolean instantKick = true;

    @ConfigEntry(configComment = "Allows IP sessions in a server")
    public boolean allowsSessions = false;

    @ConfigEntry(configComment = "Hide inventory content before auth is completed")
    public boolean hideInventoryContent = true;

    @ConfigEntry(configComment = "Locate player to underworld coordinates before auth is completed")
    public boolean hideCoordinates = true;

    /* Translation (localization) config fields */

    @ConfigEntry(configName = "local_login_notification")
    public String local_login_notification = "§7[§6AuthOn§7] §bAuthorize in this server: /login <password>";

    @ConfigEntry(configName = "local_register_notification")
    public String local_register_notification = "§7[§6AuthOn§7] §bRegister in this server: /register <password>";

    @ConfigEntry(configName = "local_auth_kick")
    public String local_auth_kick = "§7[§6AuthOn§7] §cToo long! Try again?";

    @ConfigEntry(configName = "local_not_registered")
    public String local_not_registered = "§7[§6AuthOn§7] §cYou're not registered! Register first!";

    @ConfigEntry(configName = "local_login_already")
    public String local_login_already = "§7[§6AuthOn§7] §cAlready logged in!";

    @ConfigEntry(configName = "local_login_success")
    public String local_login_success = "§7[§6AuthOn§7] §aSuccessfully logged in! Welcome back!";

    @ConfigEntry(configName = "local_db_unexpected")
    public String local_db_unexpected = "§7[§6AuthOn§7] §cA DB access error occurred while processing!";

    @ConfigEntry(configName = "local_password_wrong")
    public String local_password_wrong = "§7[§6AuthOn§7] §cWrong password!";

    @ConfigEntry(configName = "local_password_short")
    public String local_password_short = "§7[§6AuthOn§7] §cPassword too short (%d chars minimum)!";

    @ConfigEntry(configName = "local_command_usage")
    public String local_command_usage = "§7[§6AuthOn§7] §6AuthOn Usage: %s";

    @ConfigEntry(configName = "local_command_invalid")
    public String local_command_invalid = "§7[§6AuthOn§7] §cInvalid syntax! Use %s";

    @ConfigEntry(configName = "local_session_success")
    public String local_session_success = "§7[§6AuthOn§7] §aUsing session auth, welcome back!";

    @ConfigEntry(configName = "local_register_success")
    public String local_register_success = "§7[§6AuthOn§7] §aSuccessfully registered! Welcome to the server!";

    @ConfigEntry(configName = "local_register_already")
    public String local_register_already = "§7[§6AuthOn§7] §cAlready registered!";

    @ConfigEntry(configName = "local_changepwd_success")
    public String local_changepwd_success = "§7[§6AuthOn§7] §aSuccessfully changed password!";


    /* local op command translation handlers */

    @ConfigEntry(configName = "local_op_register_already")
    public String local_op_register_already = "§7[§6AuthOn§7] §cThe user %s is already registered!";

    @ConfigEntry(configName = "local_op_register_success")
    public String local_op_register_success = "§7[§6AuthOn§7] §aSuccessfully registered user %s!";

    @ConfigEntry(configName = "local_op_player_lack")
    public String local_op_player_lack = "§7[§6AuthOn§7] §cPlayer not found!";

    @ConfigEntry(configName = "local_op_unregister_success")
    public String local_op_unregister_success = "§7[§6AuthOn§7] §aSuccessfully unregistered user %s!";

    @ConfigEntry(configName = "local_op_changepwd_success")
    public String local_op_changepwd_success = "§7[§6AuthOn§7] §aSuccessfully changed %s's password!";

    @ConfigEntry(configName = "local_bridge_kick_unregister")
    public String local_bridge_kick_unregister = "§7[§6AuthOn§7] §cYou have been unregistered!";
}
