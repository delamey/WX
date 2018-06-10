package com.example.dell.wx;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@RequiresApi(api = Build.VERSION_CODES.M)
public class fragmentsecond extends Fragment {
//    public showPro mCallback;
//    public interface showPro {
//         void showProByName(String name);
//    }
//    public void setmCallback(showPro Callback){
//        this.mCallback=Callback;
//    }
    @BindView(R.id.fragmentSecond)
    ListView fragmentSecond;
    Unbinder unbinder;
    private List<String> contactList=new ArrayList<>();
    private ArrayAdapter<String> stringArrayAdapter;
    public Handler fragmenthandler;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentsecond, container, false);
        unbinder = ButterKnife.bind(this, view);
        stringArrayAdapter=new ArrayAdapter<String>(MyApplication.getContext(),android.R.layout.simple_list_item_1,contactList);
        fragmentSecond.setAdapter(stringArrayAdapter);
        contactList.add("123456");
        contactList.add("456789");
        fragmentSecond.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name=contactList.get(i);
               Second second= (Second) getActivity();
               fragmenthandler=second.handler;
                Message message=new Message();
                message.what=1;
                message.obj=name;
                fragmenthandler.sendMessage(message);

            }
        });
        return view;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
