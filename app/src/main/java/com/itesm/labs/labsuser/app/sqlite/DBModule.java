//package com.itesm.labs.labsuser.app.sqlite;
//
//import com.itesm.labs.labsuser.app.user.adapters.ComponentRecyclerAdapter;
//import com.itesm.labs.labsuser.app.sqlite.dao.CartDAO;
//import com.itesm.labs.labsuser.app.sqlite.dao.CategoriesDAO;
//import com.itesm.labs.labsuser.app.sqlite.dao.ComponentDAO;
//import com.itesm.labs.labsuser.app.sqlite.dao.HistoryDAO;
//import com.itesm.labs.labsuser.app.sqlite.dao.UserDAO;
//import com.itesm.labs.labsuser.app.user.views.activities.UserMainActivity;
//
//import javax.inject.Singleton;
//
//import dagger.Module;
//import dagger.Provides;
//
///**
// * Created by mgradob on 12/6/15.
// */
//@Module(
//        complete = false,
//        library = true,
//        injects = {
//                UserMainActivity.class,
//                ComponentRecyclerAdapter.class
//        }
//)
//public class DBModule {
//
//    public DBModule() {
//    }
//
//    //region SQLite
//    @Provides
//    @Singleton
//    CategoriesDAO providesCategoriesDAO() {
//        return new CategoriesDAO();
//    }
//
//    @Provides
//    @Singleton
//    ComponentDAO providesComponentDAO() {
//        return new ComponentDAO();
//    }
//
//    @Provides
//    @Singleton
//    CartDAO providesCartDAO() {
//        return new CartDAO();
//    }
//
//    @Provides
//    @Singleton
//    UserDAO providesUserDAO() {
//        return new UserDAO();
//    }
//
//    @Provides
//    @Singleton
//    HistoryDAO providesHistoryDAO(){
//        return new HistoryDAO();
//    }
//    //endregion
//}