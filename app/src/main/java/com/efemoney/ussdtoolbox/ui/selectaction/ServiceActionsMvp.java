package com.efemoney.ussdtoolbox.ui.selectaction;

import com.efemoney.ussdtoolbox.base.BasePresenter;
import com.efemoney.ussdtoolbox.base.BaseView;
import com.efemoney.ussdtoolbox.data.model.Action;
import com.efemoney.ussdtoolbox.data.model.Service;

import java.util.List;

/**
 * Created by moyinoluwa on 7/30/16.
 */
public interface ServiceActionsMvp {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean visible);

        void showGenericError(String message);

        void showTitle(Service service);

        void showActions(List<Action> actionList);

        void showActionFields(Action action);
    }

    interface Presenter extends BasePresenter<View> {

        void onActionSelected(Action action);
    }
}
