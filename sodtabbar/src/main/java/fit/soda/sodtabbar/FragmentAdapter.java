package fit.soda.sodtabbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import fit.soda.sodtabbar.model.TabModel;


public interface FragmentAdapter {
    Fragment getFragment(String router);

    FragmentManager getFragmentManager();

    void saveModel(TabModel model);

    void logger(String log);
}
