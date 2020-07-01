package chat.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * @author sbrf-Smirnov-VA
 * @created on 09.03.2020
 */
public class HibernateExecutor<T> {

    private Session session;
    private Transaction tx;

    public T execute(HibernateAction<T> action) {
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            tx = session.beginTransaction();
            T result = action.execute(session);
            tx.commit();
            return result;
        } catch (Throwable t) {
            try {
                if (tx != null) {
                    tx.rollback();
                }
            } catch (RuntimeException rbe) {
                System.out.println("Couldn’t roll back transaction");
            }
            throw t;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

}
