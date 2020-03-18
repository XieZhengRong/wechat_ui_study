package com.ikudot.wechatuistudy.dialog;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.viewbinding.ViewBinding;

import com.ikudot.wechatuistudy.R;
import com.ikudot.wechatuistudy.Util.Utils;
import com.ikudot.wechatuistudy.databinding.PublicPhotoDialogBinding;

public class PublicPhotoDialogFragment extends DialogFragment {
    PublicPhotoDialogBinding binding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Dialog);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = PublicPhotoDialogBinding.inflate(inflater);
        bindEvent();
        setWindowAttributes(getDialog().getWindow());
        return binding.getRoot();
    }

    private void bindEvent() {
        binding.cancel.setOnClickListener(v -> dismiss());
    }


    private void setWindowAttributes(Window window) {
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setWindowAnimations(R.style.CommonDialogStyle);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        wlp.height = Utils.getScreenSize(getContext()).y ;
        window.setAttributes(wlp);
    }
}
