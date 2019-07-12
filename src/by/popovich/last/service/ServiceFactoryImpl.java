package by.popovich.last.service;

import by.popovich.last.dao.Transaction;
import by.popovich.last.dao.TransactionFactory;
import by.popovich.last.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceFactoryImpl implements ServiceFactory {
    private static Logger LOGGER = LogManager
            .getLogger(ServiceFactoryImpl.class);

    private static final Map<Class<? extends Service>,
            Class<? extends ServiceImpl>> SERVICES = new ConcurrentHashMap<>();

    static {
        SERVICES.put(ExerciseService.class, ExerciseServiceImpl.class);
        SERVICES.put(UserService.class, UserServiceImpl.class);
        SERVICES.put(GroupService.class, GroupServiceImpl.class);
        SERVICES.put(PriceService.class, PriceServiceImpl.class);
        SERVICES.put(SubscriptionService.class, SubscriptionServiceImpl.class);
        SERVICES.put(UserInfoService.class, UserInfoServiceImpl.class);
    }

    private TransactionFactory factory;

    public ServiceFactoryImpl(TransactionFactory factory) throws PersistentException {
        this.factory = factory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <Type extends Service> Type getService(Class<Type> key) throws PersistentException {
        Class<? extends ServiceImpl> value = SERVICES.get(key);
        if(value != null) {
            try {
                ClassLoader classLoader = value.getClassLoader();
                Class<?>[] interfaces = {key};
                Transaction transaction = factory.createTransaction();
                ServiceImpl service = value.newInstance();
                service.setTransaction(transaction);
                InvocationHandler handler = new ServiceInvocationHandlerImpl(service);
                return (Type) Proxy.newProxyInstance(classLoader, interfaces, handler);
            } catch(PersistentException e) {
                throw e;
            } catch(InstantiationException | IllegalAccessException e) {
                LOGGER.error("It is impossible to instance service class", e);
                throw new PersistentException(e);
            }
        }
        return null;
    }

    @Override
    public void close() {
        factory.close();
    }
}
