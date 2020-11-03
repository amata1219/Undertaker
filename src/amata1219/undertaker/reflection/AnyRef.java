package amata1219.undertaker.reflection;

import java.lang.reflect.*;
import java.util.Objects;
import java.util.function.Function;

public class AnyRef {

    private static final AnyRef NONE = null;

    public final Class<?> type;
    private final Object instance;

    public AnyRef(Class<?> type, Object instance) {
        this.type = type;
        this.instance = instance;
    }

    public <T> T instance() {
        return (T) instance;
    }

    public <T> AnyRef flatMap(Function<T, AnyRef> mapper) {
        return instance == null ? NONE : mapper.apply(instance());
    }

    public <T> AnyRef map(Function<T, Object> mapper) {
        return flatMap(i -> Reflect.on(mapper.apply((T) i)));
    }

    public AnyRef set(String name, Object value) {
        try {
            accessibleField(name).set(instance, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    public AnyRef update(String name, Function<Object, Object> updater) {
        Field field = accessibleField(name);
        try {
            field.set(instance, updater.apply(field.get(instance)));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    public <T> T value(String name) {
        return field(name).instance();
    }

    public AnyRef field(String name) {
        Field field = accessibleField(name);
        try {
            return Reflect.on(field.getType(), field.get(instance));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Field accessibleField(String name) {
        return searchUpwardsForMember(type -> {
            try {
                return type.getDeclaredField(name);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    public AnyRef call(String name, Object... args) {
        Class<?>[] types = new Class[args.length];
        for (int i = 0; i < args.length; i++) types[i] = args[i].getClass();
        Object returned = null;
        try {
            returned = accessibleMethod(name, types).invoke(instance, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return returned == null ? NONE : Reflect.on(returned);
    }

    public void run(String name, Object... args) {
        call(name, args);
    }

    private Method accessibleMethod(String name, Class<?>... types) {
        return searchUpwardsForMember(type -> {
            try {
                return type.getDeclaredMethod(name, types);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    private <M extends AccessibleObject> M searchUpwardsForMember(Function<Class<?>, M> getter) {
        Class<?> current = type;
        try {
            return accessiblize(getter.apply(current));
        } catch (Exception ex) {
            while (true) {
                current = current.getSuperclass();
                if (current == null) throw ex;
                try {
                    return accessiblize(getter.apply(current));
                } catch (Exception ignored) {

                }
            }
        }
    }

    public AnyRef newInstance(Object... args) {
        Class<?>[] types = new Class[args.length];
        for (int i = 0; i < args.length; i++) types[i] = args[i].getClass();
        Object object = null;
        try {
            object = accessibleConstructor(types).newInstance(args);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return Reflect.on(Objects.requireNonNull(object).getClass(), object);
    }

    private Constructor<?> accessibleConstructor(Class<?>... types) {
        Constructor<?> constructor = null;
        try {
            constructor = type.getDeclaredConstructor(types);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return accessiblize(Objects.requireNonNull(constructor));
    }

    private <A extends AccessibleObject> A accessiblize(A object) {
        object.setAccessible(true);
        return object;
    }

}
