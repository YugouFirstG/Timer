package com.example.timer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.timer.Adapter.HabitAdapter;
import com.example.timer.Interfaces.IViewType;
import com.example.timer.Interfaces.QuickMultiSupport;
import com.example.timer.Model.MultiBean;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HabitFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HabitFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView mRecycleView;
    private List<IViewType> mData = new ArrayList<>();
    private QuickMultiSupport<IViewType> mQuickSupport;

    public HabitFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HabitFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HabitFragment newInstance(String param1, String param2) {
        HabitFragment fragment = new HabitFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_habit, container, false);
        initFragment(view);
        return view;
    }

    private void initFragment(View view) {
        initData();
        mRecycleView = view.findViewById(R.id.recycle_list2);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecycleView.setAdapter(new HabitAdapter(this.getContext(), mData));
    }

    private void initData() {
        mData.clear();
        for (int i = 0; i < 199; i++) {
            MultiBean bean = new MultiBean();
            bean.name = "mData----" + i;
            mData.add(bean);
        }
        mQuickSupport = new QuickMultiSupport<IViewType>() {
            @Override
            public int getViewTypeCount() {
                return 0;
            }

            @Override
            public int getLayoutId(IViewType data) {
                if (data instanceof MultiBean) {
                    return R.layout.item_habit;
                }

                return 0;
            }

            @Override
            public int getItemViewType(IViewType data) {
                return 0;
            }

            @Override
            public boolean isSpan(IViewType data) {
                return false;
            }
        };

    }
}
