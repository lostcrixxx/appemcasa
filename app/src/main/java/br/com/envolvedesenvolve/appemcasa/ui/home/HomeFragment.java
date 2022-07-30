package br.com.envolvedesenvolve.appemcasa.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import br.com.envolvedesenvolve.appemcasa.R;
import br.com.envolvedesenvolve.appemcasa.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private static HomeFragment instance;
    private FragmentHomeBinding binding;
    //    HomeViewModel homeViewModel;
    View view;
    TextView textView;

    public static HomeFragment getInstance() {
        if (instance == null) {
            instance = new HomeFragment();
        }
        return instance;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        instance = this;

        view = inflater.inflate(R.layout.fragment_home, container, false);
        textView = view.findViewById(R.id.text_home);

        textView.setText("");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void updateText(String text) {
        Log.e("TAG", "get updateText: " + text);
//        binding = FragmentHomeBinding.inflate(inflater, container, false);
//        textView = binding.textHome;
        textView = view.findViewById(R.id.text_home);
        textView.setText(text);
//        root.adgetText().observe(getViewLifecycleOwner(), obs_getsAnswer);
//        obs_getsAnswer.onChanged(text);

//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                textView.setText(text);
//            }
//        });
//        binding.textHome.setText(text);
    }

//    private Observer<String> obs_getsAnswer = new Observer<String>() {
//        @Override
//        public void onChanged(@Nullable String sAnswer) {
//            Log.e("TAG", "get updateText: " + sAnswer);
//            textView.setText(sAnswer);
//        }
//    };
}