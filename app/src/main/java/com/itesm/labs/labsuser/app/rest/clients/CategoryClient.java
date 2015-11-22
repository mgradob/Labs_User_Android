package com.itesm.labs.labsuser.app.rest.clients;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.rest.models.Category;
import com.itesm.labs.labsuser.app.rest.services.CategoryService;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by mgradob on 10/28/15.
 */
public class CategoryClient {

    private CategoryService mCategoryService;

    public CategoryClient(CategoryService mCategoryService) {
        this.mCategoryService = mCategoryService;
    }

    public Observable<ArrayList<Category>> getCategories(final String token, final String lab) {
        return Observable.create(new Observable.OnSubscribe<ArrayList<Category>>() {
            @Override
            public void call(Subscriber<? super ArrayList<Category>> subscriber) {
                ArrayList<Category> categories = mCategoryService.getCategories(token, lab);

                for (Category category : categories) {
                    switch (category.getId()) {
                        case 1:
                            category.setImageResource(R.drawable.resistencias);
                            break;
                        case 2:
                            category.setImageResource(R.drawable.capacitores);
                            break;
                        case 3:
                            category.setImageResource(R.drawable.equipo);
                            break;
                        case 4:
                            category.setImageResource(R.drawable.kits);
                            break;
                        case 5:
                            category.setImageResource(R.drawable.cables);
                            break;
                        case 6:
                            category.setImageResource(R.drawable.integrados);
                            break;
                        case 7:
                            category.setImageResource(R.drawable.diodos);
                            break;
                        case 8:
                            category.setImageResource(R.drawable.herramientas);
                            break;
                        case 9:
                            category.setImageResource(R.drawable.switches);
                            break;
                        case 10:
                            category.setImageResource(R.drawable.adaptadores);
                            break;
                        case 11:
                            category.setImageResource(R.drawable.displays);
                            break;
                        case 12:
                            category.setImageResource(R.drawable.inductores);
                            break;
                        case 13:
                            category.setImageResource(R.drawable.sensores);
                            break;
                        case 14:
                            category.setImageResource(R.drawable.motores);
                            break;
                        case 15:
                            category.setImageResource(R.drawable.potenciometro);
                            break;
                        case 16:
                            category.setImageResource(R.drawable.transformadores);
                            break;
                        case 17:
                            category.setImageResource(R.drawable.transistores);
                            break;
                        default:
                            category.setImageResource(R.drawable.ic_career);
                            break;
                    }
                }

                subscriber.onNext(categories);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Category> getCategory(final String token, final String lab, final int idCategory) {
        return Observable.create(new Observable.OnSubscribe<Category>() {
            @Override
            public void call(Subscriber<? super Category> subscriber) {
                Category category = mCategoryService.getCategory(token, lab, idCategory);

                subscriber.onNext(category);
                subscriber.onCompleted();
            }
        });
    }
}
