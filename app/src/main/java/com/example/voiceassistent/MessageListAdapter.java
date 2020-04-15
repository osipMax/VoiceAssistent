package com.example.voiceassistent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter{
    private static final int ASSISTANT_TYPE = 0;
    private static final int USER_TYPE = 1;

    public List<Message> messageList = new ArrayList<>();


    private Context context;
    private RecyclerView recyclerView;
    private Animation enlargeAnim, shrinkAnim;
    public MessageListAdapter(Context context, RecyclerView view) {
        this.context=context;
        this.recyclerView=view;
    }
    private int lastPosition = 0;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if(viewType == ASSISTANT_TYPE) {
//            v=LayoutInflater.from(parent.getContext()).inflate()
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.assistant_message, parent, false);
        }
        else
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_message, parent, false);
        return new MessageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MessageViewHolder) holder).bind(messageList.get(position));
        setAnimation(holder.itemView, position);
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
//            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);

//            enlargeAnim = AnimationUtils.loadAnimation(context, R.anim.enlarge);
//            shrinkAnim = AnimationUtils.loadAnimation(context, R.anim.shrink);
//            animation.setAnimationListener(animationEnlargeListener);
//            animation.setAnimationListener(animationShrinkListener);
//            viewToAnimate.startAnimation(enlargeAnim);

            TranslateAnimation anim = new TranslateAnimation(-300, 0, 0 ,0);
            anim.setDuration(300);
            anim.setFillAfter(true);
            viewToAnimate.startAnimation(anim);
            lastPosition = position;
        }
        else if(position == lastPosition-1) {
            TranslateAnimation anim = new TranslateAnimation(300, 0, 0 ,0);
            anim.setDuration(300);
            anim.setFillAfter(true);
            viewToAnimate.startAnimation(anim);
//            lastPosition = position;
//            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
//           Animation animation = AnimationUtils.makeInChildBottomAnimation(context);
//            viewToAnimate.startAnimation(animation);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public int getItemViewType(int index) {
        Message msg = messageList.get(index);
        if(msg.isSend)
            return USER_TYPE;
        else return ASSISTANT_TYPE;
    }


//  анимация увеличения
    Animation.AnimationListener animationEnlargeListener = new Animation.AnimationListener() {

        @Override
        public void onAnimationEnd(Animation animation) {
            // когда анимация увеличения заканчивается,
            // то запускаем анимацию уменьшения
//            recyclerView.startAnimation(shrinkAnim);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        @Override
        public void onAnimationStart(Animation animation) {
        }
    };

    Animation.AnimationListener animationShrinkListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationEnd(Animation animation) {
            // когда анимация уменьшения заканчивается,
            // то начинаем анимацию увеличения
//            recyclerView.startAnimation(enlargeAnim);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        @Override
        public void onAnimationStart(Animation animation) {
        }
    };

//  анимация сдвига

}
