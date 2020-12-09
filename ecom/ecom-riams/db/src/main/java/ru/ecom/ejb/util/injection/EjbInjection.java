package ru.ecom.ejb.util.injection;

import ru.ecom.ejb.services.script.IScriptService;

import javax.naming.InitialContext;

public class EjbInjection {

    public static EjbInjection getInstance(String appName) {
        return new EjbInjection(appName);
    }

    public static EjbInjection getInstance() {
        return new EjbInjection(null);
    }

    public EjbInjection(String appName) {
        if (appName != null && !appName.equals("")) {
            this.appName = appName;
        } else {
            EjbEcomConfig theConfig = EjbEcomConfig.getInstance();
            this.appName = theConfig.get("default.appname", "riams-app");

        }
    }

    private <T> T getService(Class<T> serviceClass, String suffix) {
        return (T) getService(serviceClass.getSimpleName(), suffix);
    }

    /**
     * Получение сервиса по интерфейсу
     *
     * @param <T>
     * @param interfaceSimpleName
     * @return
     */
    public Object getService(String interfaceSimpleName, String suffix) {
        String name = interfaceSimpleName.substring(1);
        try {
            InitialContext ctx = new InitialContext();
            try {
                // FIXME получение названия приложения

                //String appName = theConfig.get("default.appname","riams-app");

                String jndi = appName + "/" + name + "Bean/" + suffix;
                return ctx.lookup(jndi);
            } finally {
                ctx.close();
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка инициализации контекста: " + e.getMessage(), e);
        }
    }

    public <T> T getLocalService(Class<T> serviceClass) {
        return getService(serviceClass, "local");
    }

    public Object getLocalService(String interfaceSimpleName) {
        return getService(interfaceSimpleName, "local");
    }

    public <T> T getRemoteService(Class<T> serviceClass) {
        return getService(serviceClass, "remote");
    }

    public Object invoke(String serviceName, String methodName, Object[] args) {
        IScriptService scriptService = getLocalService(IScriptService.class);
        return scriptService.invoke(serviceName, methodName, args);
    }

    private final String appName;
}
