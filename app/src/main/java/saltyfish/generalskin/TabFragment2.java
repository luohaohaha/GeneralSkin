package saltyfish.generalskin;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import saltyfish.library.SaltyfishSideBar;
import saltyfish.library.base.SaltyfishBaseFragment;
import saltyfish.library.utils.SaltyfishUtilTool;

/**
 * Project: GeneralSkin<br/>
 * Package: saltyfish.generalskin<br/>
 * ClassName: TabFragment2<br/>
 * Description: ${TODO} <br/>
 * Date: 2016/10/12 9:33 <br/>
 * <p>
 * Author 昊<br/>
 * Version 1.0<br/>
 * since JDK 1.6<br/>
 * <p>
 * Copyright (c) 2016 luohaohaha@gmail.com All rights reserved
 */

public class TabFragment2 extends SaltyfishBaseFragment {

    private MyAdapter myAdapter;

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.tab2,container,false);
        String s="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        List<String> strings = Collections.synchronizedList(new LinkedList<String>());
        for (int i=0,length=s.length();i<length;i++){
            strings.add(String.valueOf(s.charAt(i)));
        }
        SaltyfishSideBar sideBar = (SaltyfishSideBar) view.findViewById(R.id.sidebar);
        sideBar.setStringSideData(strings);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<String>  items = Collections.synchronizedList(new LinkedList<String>());
        int i=0;
        for (;;){
            if(20== i++)
                break;
            items.add("item"+i);

        }
        myAdapter =new MyAdapter(items);
        recyclerView.setAdapter(myAdapter);
        return view;
    }

    @Override
    protected void ChangeSkin() {
       for (int i=0,count=recyclerView.getChildCount();i<count;i++){
           View child = recyclerView.getChildAt(i);
           SaltyfishUtilTool.setRipple(child);
       }
        super.ChangeSkin();
    }

    private class MyAdapter extends RecyclerView.Adapter {

        private List<String> items;

        public MyAdapter(List<String> items) {
            this.items = items;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(getActivity()).inflate(android.R.layout.simple_list_item_1,parent,false);
            itemView.setBackgroundColor(Color.WHITE);

            SaltyfishUtilTool.setRipple(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            ((MyViewHolder) holder).setData(items.get(position));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    };

    private class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView= (TextView) itemView.findViewById(android.R.id.text1);

        }

        public void setData(String s){
            textView.setText(s);
        }
    }
}
