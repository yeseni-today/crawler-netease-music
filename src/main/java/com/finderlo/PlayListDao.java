package com.finderlo;

import com.finderlo.model.PlayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Created by Finderlo on 2016/11/27.
 */
public class PlayListDao {

    SessionFactory sessionFactory = Factory.sessionFactory();


    public void saveOrUpdate(PlayList playList) {
        Session session =  sessionFactory.openSession();
        session.beginTransaction();
        try {
            session.saveOrUpdate(playList);
            session.getTransaction().commit();
        }catch (Exception e){
            session.getTransaction().rollback();
            Util.handleException(playList.getTable_id());
        }finally {
            session.close();
        }
    }
}
