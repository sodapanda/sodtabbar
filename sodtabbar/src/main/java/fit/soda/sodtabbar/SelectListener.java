package fit.soda.sodtabbar;

import fit.soda.sodtabbar.model.TabListModel;

public interface SelectListener {
    boolean select(TabListModel model);

    void afterSelect(String contextType);
}
