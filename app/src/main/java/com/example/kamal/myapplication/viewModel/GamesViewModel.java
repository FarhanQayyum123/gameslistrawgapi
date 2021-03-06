package com.example.kamal.myapplication.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.kamal.myapplication.model.GameItemDetailModel;
import com.example.kamal.myapplication.model.APIRequestParamModel;
import com.example.kamal.myapplication.model.GamesListModel;
import com.example.kamal.myapplication.model.APIResponseHttp;
import com.example.kamal.myapplication.network.APIInterface;
import com.example.kamal.myapplication.network.ApiClient;
import com.example.kamal.myapplication.repository.RetrofitRepository;
import com.example.kamal.myapplication.utils.Constants;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import retrofit2.HttpException;

public class GamesViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<GameItemDetailModel> itemObject = new MutableLiveData<>();
    private MutableLiveData<APIResponseHttp<ArrayList<GamesListModel>>> gamesListData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<String> errorListener = new MutableLiveData<>();
    public MutableLiveData<GamesListModel> clickedItemObject = new MutableLiveData<>();
    private RetrofitRepository retrofitRepo;
    private CompositeDisposable disposable;

    public GamesViewModel(Application application) {
        super(application);
        retrofitRepo = new RetrofitRepository();
        disposable = new CompositeDisposable();
    }

    public void callAPI(String tag, boolean isLoad, APIRequestParamModel paramObject) {
        APIInterface apiInterface = ApiClient.getClientAuthentication().create(APIInterface.class);
        if (isLoad)
            isLoading.setValue(true);

        if (tag.equalsIgnoreCase(Constants.GAMES_LIST)) // request API for games list
            disposable.add(retrofitRepo.callAPI(apiInterface.getGames(Constants.PAGE_SIZE, String.valueOf(paramObject.getPage())), getObserverList(tag, isLoad)));

        if (tag.equalsIgnoreCase(Constants.ITEM_DETAIL)) // request API for game item detail
            disposable.add(retrofitRepo.callAPI(apiInterface.getItemDetail(paramObject.getItemId()), getObserverDetail(tag, isLoad)));
    }


    // Observer for getting response through RXJava
    public <T> DisposableSingleObserver<APIResponseHttp<T>> getObserverList(String tag, boolean isLoad) {
        return new DisposableSingleObserver<APIResponseHttp<T>>() {
            @Override
            public void onSuccess(@NonNull APIResponseHttp<T> response) {
                // Update loader here
                if (isLoad)
                    isLoading.setValue(false);
                // Set games list data here
                if (tag.equalsIgnoreCase(Constants.GAMES_LIST))
                    gamesListData.setValue((APIResponseHttp<ArrayList<GamesListModel>>) response);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                // Update loader here
                if (isLoad)
                    isLoading.setValue(false);
                // Show error here that will receive from API in any case of exception
                errorListener.setValue(e.toString());
                // Here we can handle multiple type HTTP exceptions
                if (e instanceof HttpException) {
                    HttpException exception = (HttpException) e;
                    switch (exception.code()) {
                        case 400:
                            // Handle code 400
                            break;
                        case 500:
                            // Handle code 500
                            break;
                        default:
                            break;
                    }
                }
            }
        };
    }

    // Observer for getting response through RXJava
    public <T> DisposableSingleObserver<T> getObserverDetail(String tag, boolean isLoad) {
        return new DisposableSingleObserver<T>() {
            @Override
            public void onSuccess(@NonNull T response) {
                // Update loader here
                if (isLoad)
                    isLoading.setValue(false);
                // Set game item detail data here
                if (tag.equalsIgnoreCase(Constants.ITEM_DETAIL))
                    itemObject.setValue((GameItemDetailModel) response);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                // Update loader here
                if (isLoad)
                    isLoading.setValue(false);
                // Show error here that will receive from API in any case of exception
                errorListener.setValue(e.toString());
                // Here we can handle multiple type HTTP exceptions
                if (e instanceof HttpException) {
                    HttpException exception = (HttpException) e;
                    switch (exception.code()) {
                        case 400:
                            // Handle code 400
                            break;
                        case 500:
                            // Handle code 500
                            break;
                        default:
                            break;
                    }
                }
            }
        };
    }


    public MutableLiveData<APIResponseHttp<ArrayList<GamesListModel>>> gamesList() {
        return gamesListData;
    }

    public MutableLiveData<Boolean> isLoading() {
        return this.isLoading;
    }

    public MutableLiveData<String> errorListener() {
        return this.errorListener;
    }

    public MutableLiveData<GamesListModel> getClickedItemObject() {
        return this.clickedItemObject;
    }

    public MutableLiveData<GameItemDetailModel> getItemDetail() {
        return this.itemObject;
    }
}
