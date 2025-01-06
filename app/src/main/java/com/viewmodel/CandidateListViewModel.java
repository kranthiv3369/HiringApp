package com.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hiring.model.CandidateModel;
import com.hiring.network.NetworkServiceApi;
import com.hiring.util.FilterAndSortUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CandidateListViewModel extends AndroidViewModel {
    private final MutableLiveData<List<CandidateModel>> fetchList = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>(); // For error messages
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();


    public CandidateListViewModel(Application application) {
        super(application);
        fetchData();
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<List<CandidateModel>> getFetchList() {
        return fetchList;
    }

    public LiveData<String> getErrorMessage() { // Observe this in the UI
        return errorMessage;
    }

    private void fetchData() {

        isLoading.postValue(true); // Start loading

        NetworkServiceApi instance = NetworkServiceApi.getInstance();
        Call<List<CandidateModel>> retrofitCall = instance.getFetchItems();
        retrofitCall.enqueue(new Callback<List<CandidateModel>>() {
            @Override
            public void onResponse(Call<List<CandidateModel>> call, Response<List<CandidateModel>> response) {

                isLoading.postValue(false); // Stop loading

                if (response.isSuccessful() && response.body() != null) {
                    List<CandidateModel> items = response.body();
                    items = FilterAndSortUtil.processItems(items);
                    fetchList.postValue(items);
                } else {
                    errorMessage.postValue("Failed to fetch data. Error code: " + response.code());
                }

            }

            @Override
            public void onFailure(Call<List<CandidateModel>> call, Throwable t) { isLoading.postValue(false); // Stop loading
                errorMessage.postValue("Error: " + t.getMessage());
            }
        });

    }
}
