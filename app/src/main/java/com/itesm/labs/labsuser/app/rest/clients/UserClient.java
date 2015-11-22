package com.itesm.labs.labsuser.app.rest.clients;

import com.itesm.labs.labsuser.app.rest.models.Auth;
import com.itesm.labs.labsuser.app.rest.models.LoginUser;
import com.itesm.labs.labsuser.app.rest.models.User;
import com.itesm.labs.labsuser.app.rest.services.UserService;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by mgradob on 10/25/15.
 */
public class UserClient {

    private UserService mUserService;

    public UserClient(UserService userService) {
        this.mUserService = userService;
    }

    public Observable<String> loginUser(final LoginUser loginUser) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                Auth auth = mUserService.loginUser(loginUser);

                subscriber.onNext(auth.getAuth_token());
                subscriber.onCompleted();
            }
        });
    }

    public Observable<User> getUser(final String token, final String userId) {
        return Observable.create(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> subscriber) {

                User user = mUserService.getUser(token, userId);

                subscriber.onNext(user);
                subscriber.onCompleted();
            }
        });
    }
}
