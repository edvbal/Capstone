package com.telesoftas.edvinas.onboardingedvblk.tutorial.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.telesoftas.edvinas.onboardingedvblk.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TutorialDetailsDetailsFragment extends Fragment implements TutorialDetailsView {
    public static final String KEY_TUTORIAL_DESCRIPTION = "key.description";
    public static final String KEY_TUTORIAL_IMAGE = "key.image";
    @BindView(R.id.imageViewTutorial) ImageView imageViewTutorial;
    @BindView(R.id.textViewTutorial) TextView textViewTutorial;
    private TutorialDetailsPresenter presenter;
    private Unbinder unbinder;

    public static Fragment newInstance(int tutorialImages, int tutorialDescriptions) {
        Bundle args = new Bundle();
        args.putInt(KEY_TUTORIAL_IMAGE, tutorialImages);
        args.putInt(KEY_TUTORIAL_DESCRIPTION, tutorialDescriptions);
        TutorialDetailsDetailsFragment fragment = new TutorialDetailsDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new TutorialDetailsPresenterImpl();
        presenter.takeView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_tutorial, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        presenter.onViewCreated();
    }

    @Override
    public void setViews() {
        imageViewTutorial.setImageResource(getArguments().getInt(KEY_TUTORIAL_IMAGE));
        textViewTutorial.setText(getArguments().getInt(KEY_TUTORIAL_DESCRIPTION));
    }

    @Override
    public void onDestroy() {
        presenter.dropView();
        unbinder.unbind();
        super.onDestroy();
    }
}
