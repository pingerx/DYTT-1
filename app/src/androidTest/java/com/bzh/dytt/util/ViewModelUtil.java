package com.bzh.dytt.util;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

/**
 * Creates a one off view model factory for the given view model instance.
 */
public class ViewModelUtil {
    private ViewModelUtil() {
    }

    public static <TT extends ViewModel> ViewModelProvider.Factory createFor(final TT model) {
        return new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                if (modelClass.isAssignableFrom(model.getClass())) {
                    //noinspection unchecked
                    return (T) model;
                }
                throw new IllegalArgumentException("unexpected model class " + modelClass);
            }
        };
    }
}
