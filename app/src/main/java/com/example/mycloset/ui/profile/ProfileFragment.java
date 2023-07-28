package com.example.mycloset.ui.profile;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mycloset.R;
import com.example.mycloset.data.AppDatabase;
import com.example.mycloset.data.daos.GarmentDao;
import com.example.mycloset.data.entities.Garment;
import com.example.mycloset.databinding.FragmentProfileBinding;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Action;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kotlinx.coroutines.flow.Flow;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    private FragmentProfileBinding binding;
    public Flowable<List<Garment>> listLiveData;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        binding = FragmentProfileBinding.inflate(inflater, container, false);


//        AppDatabase db = AppDatabase.get(getContext());
//        GarmentDao garmentDao = db.garmentDao();
//
//        ListenableFuture<List<Garment>> listListenableFuture = garmentDao.selectAll();
//        Futures.addCallback(
//                listListenableFuture,
//                new FutureCallback<List<Garment>>() {
//                    public void onSuccess(List<Garment> result) {
//                        // handle success
//                        String s = "";
//                        for (Garment g: result) {
//                            s += g + "\n";
//                        }
////                        result.forEach(t -> binding.textView7.setText(t.toString()));
//                        binding.textView7.setText(s);
//                    }
//
//                    public void onFailure(@NonNull Throwable thrown) {
//                        // handle failure
//                    }
//                },
//                // causes the callbacks to be executed on the main (UI) thread
//                getContext().getMainExecutor()
//        );


        return binding.getRoot();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



//        listSingle.subscribeOn(Schedulers.io()).subscribe(clothes -> {
//            modifyTextView(clothes.toString());
////            binding.textView7.setText(clothes.toString());
//        }, Throwable::printStackTrace);


//        Garment garment = new Garment();
//        garment.brand = "Ropa";
//        garment.category = "Camiseta";
//        Garment garment2 = new Garment();
//        garment2.brand = "Mic";
//        garment2.category = "Moc";
//        garmentDao.insertAll(garment, garment2);
//        listLiveData = garmentDao.selectAll();
//        List<Garment> clothes = listLiveData.getValue();
//        if (clothes != null)
//            binding.textView7.setText(clothes.toString());



//        view.getContext().activ.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Garment garment = new Garment();
//                garment.brand = "Ropa";
//                garment.category = "Camiseta";
////                binding.textView7.setText(garmentDao.insertAll(garment)+"");
//                binding.textView7.setText(garmentDao.selectAll().toString());
//            }
//        });

//
//        listLiveData = garmentDao.selectAll();
//        Flowable<List<Garment>> listFlowable = listLiveData.doOnComplete(new Action() {
//            @Override
//            public void run() throws Exception {
//                binding.textView7.setText("hola");
//            }
//        });

//        garmentDao.insertAll(garment);
//        List<Garment> clothes = garmentDao.selectAll();
//        List<Garment> clothes = db.garmentDao().selectAll();
//        binding.textView7.setText(clothes.toString());
    }

    private void testObserve(List<Garment> garments) {
//        garments =
        if (garments == null)
            binding.textView7.setText("null");
        else
            binding.textView7.setText(garments.toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}