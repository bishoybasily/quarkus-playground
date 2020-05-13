package com.gmail.bishoybasily.quarkus;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.UUIDGenerator;

import javax.persistence.Id;
import java.lang.reflect.Field;

public class IdGenerator extends UUIDGenerator {

    public final static String
            NAME = "IdGenerator",
            CLASS = "com.gmail.bishoybasily.quarkus.IdGenerator";

    @Override
    public final String generate(SharedSessionContractImplementor session, Object object) throws HibernateException {

        try {

            Field field = null;
            Class<?> cls = object.getClass();
            found:
            while (!ObjectUtils.isEmpty(cls)) {
                for (Field f : cls.getDeclaredFields()) {
                    boolean annotatedWithId = f.isAnnotationPresent(Id.class);
                    boolean isString = f.getType().getSuperclass().isAssignableFrom(String.class);
                    if (annotatedWithId && isString) {
                        field = f;
                        break found;
                    }
                }
                cls = cls.getSuperclass();
            }

            if (ObjectUtils.isEmpty(field))
                throw new RuntimeException();

            field.setAccessible(true);

            String id = (String) field.get(object);

            if (ObjectUtils.isEmpty(id))
                return session.createNativeQuery("SELECT uuid()").list().iterator().next().toString();

            return id;

        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

}
