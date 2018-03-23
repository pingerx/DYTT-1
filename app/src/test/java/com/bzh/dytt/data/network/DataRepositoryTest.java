package com.bzh.dytt.data.network;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.bzh.dytt.DataRepository;
import com.bzh.dytt.data.HomeType;
import com.bzh.dytt.data.db.AppDatabase;
import com.bzh.dytt.data.db.HomeAreaDao;
import com.bzh.dytt.data.db.HomeItemDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DataRepositoryTest {


    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private DataRepository mDataRepository;

    @Mock
    private HomeItemDao mHomeItemLocalDao;

    @Mock
    private HomeAreaDao mHomeAreaDao;

    @Mock
    private DyttService mDyttService;

    @Mock
    private AppDatabase mAppDataBase;

    @Before
    public void setupItemRepository() throws IOException {
        MockitoAnnotations.initMocks(this);

//        mDataRepository = DataRepository.getInstance(mDyttService, mAppDataBase);
    }

    @After
    public void destroyItemRepository() {
        mDataRepository = null;
    }

    @Test
    public void getHomeItems() {

        when(mAppDataBase.homeItemDAO()).thenReturn(mHomeItemLocalDao);

        verify(mHomeItemLocalDao, times(1)).getItemsByType(HomeType.NEWEST);
    }

    @Test
    public void getHomeAreas() throws Exception {
        verify(mHomeAreaDao, times(1)).getAreas();
    }

//    @Test
//    public void getItems_requestHomeNewestFromDB() {
//
//        MutableLiveData<List<HomeItem>> itemsByType = new MediatorLiveData<>();
//
//        when(mHomeItemLocalDao.getItemsByType(HomeType.NEWEST)).thenReturn(itemsByType);
//
//        LiveData<Resource<List<HomeItem>>> items = mDataRepository.getHomeItems(HomeType.NEWEST);
//
//        items.observeForever(new Observer<Resource<List<HomeItem>>>() {
//            @Override
//            public void onChanged(@Nullable Resource<List<HomeItem>> listResource) {
//                System.out.println("Update UI");
//            }
//        });
//
//        itemsByType.setValue(new ArrayList<HomeItem>());
//
//        // Then items are loaded from the db
//        verify(mHomeItemLocalDao, times(1)).getItemsByType(HomeType.NEWEST);
//    }
}
