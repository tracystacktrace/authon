package net.tracyex0.authon.misc;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * @deprecated A temporary solution just to make the mod's configuration work! Will be removed in ReIndev 2.9 release.
 */
@Deprecated
public class AdvancedConfigProcessor {

    public static void processConfig(
            @NotNull Object o,
            @NotNull File file
    ) {
        if(file.exists()) {
            tryRead(o, file);
            return;
        }
        tryWrite(o, file);
    }

    static void tryWrite(Object o, File file) {
        try(FileOutputStream stream = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(stream, StandardCharsets.UTF_8)
        ) {
            Properties configValues = new Properties();

            for(Field field : o.getClass().getFields()) {
                configValues.put(field.getName(), field.get(o).toString());
            }
            configValues.store(writer, "AuthOn configuration file");

        }catch (IOException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    static void tryRead(Object o, File file) {
        try(FileInputStream stream = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {

            Properties configValues = new Properties();
            configValues.load(reader);

            for(Field field : o.getClass().getFields()) {
                field.set(o, parseObject(
                        field.getType(), configValues.getOrDefault(field.getName(), field.get(o))
                ));
            }
        } catch (IOException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    static Object parseObject(Class<?> type, Object o) {
        if(type.isInstance(o)) {
            return o;
        }
        if(o instanceof String) {
            String a = (String) o;

            if(a.equals("true")) return true;
            if(a.equals("false")) return false;

            try {
                return Integer.parseInt(a);
            }catch (NumberFormatException e) {
                return a;
            }
        }
        throw new RuntimeException();
    }
}
