package fit.soda.sodtabbar.model;

import java.util.List;

public class TabModel {
    public String tabBgColor;
    public int savedVersion;
    public List<TabListModel> tabList;
    //如果数据结构变化了导致代码和存储的数据不一致了，要改版本号强制使之前的数据失效
    public static final int VERSION = 1;

    @Override
    public String toString() {
        if (tabList == null) {
            return "no element";
        }
        StringBuilder sb = new StringBuilder();
        for (TabListModel item : tabList) {
            sb.append("\n").append("name:").append(item.title).append(",").append("red dot:").append(item.redDot).append("clickTime:").append(item.redDotClickTime).append(",").append("reddot update:").append(item.redDotUpdateTime).append("\n");
        }

        return sb.toString();
    }
}
