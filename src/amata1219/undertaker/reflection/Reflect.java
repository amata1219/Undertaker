package amata1219.undertaker.reflection;

public class Reflect {

    public static AnyRef on(Class<?> type, Object instance) {
        return new AnyRef(type, instance);
    }

    public static AnyRef on(String className, Object instance) {
        try {
            return on(Class.forName(className), instance);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static AnyRef on(Object instance) {
        Class<?> type = instance.getClass();
        if (String.class.equals(type)) return on((String) instance, null);
        else if (Class.class.equals(type)) return on((Class<?>) instance, null);
        else return on(type, instance);
    }

    public static AnyRef onNMS(String className) {
        return on(MinecraftPackage.NET_MINECRAFT_SERVER.toString(), className);
    }

    public static AnyRef onOBC(String className) {
        return on(MinecraftPackage.ORG_BUKKIT_CRAFTBUKKIT.toString(), className);
    }

}
