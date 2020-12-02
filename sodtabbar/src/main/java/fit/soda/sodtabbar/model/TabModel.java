package fit.soda.sodtabbar.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TabModel {
    @SerializedName("tabBgColor")
    public String tabBgColor;
    @SerializedName("savedVersion")
    public int savedVersion;
    @SerializedName("tabList")
    public List<TabListModel> tabList;
    //如果数据结构变化了导致代码和存储的数据不一致了，要改版本号强制使之前的数据失效
    public transient static final int VERSION = 1;

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

    @SuppressLint("ApplySharedPref")
    public void writeToLocale(Context context) {
        //序列化之后存入sp
        this.savedVersion = VERSION;
        Gson gson = new Gson();
        String jsonStr = "";
        try {
            jsonStr = gson.toJson(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (jsonStr == null || "".equals(jsonStr)) {
            return;
        }
        SharedPreferences sp = context.getSharedPreferences("fit.soda.sodtabbar",
                Context.MODE_PRIVATE);
        sp.edit().putString("tabmodel", jsonStr).commit();
    }

    public static TabModel readFromLocalOrDefault(Context context, TabModel defaultModel) {
        SharedPreferences sp = context.getSharedPreferences("fit.soda.sodtabbar",
                Context.MODE_PRIVATE);

        String jsonStr = sp.getString("tabmodel", "");
        if (jsonStr == null || "".equals(jsonStr)) {
            return defaultModel;
        }

        Gson gson = new Gson();
        TabModel model = gson.fromJson(jsonStr, TabModel.class);
        if (model == null || model.savedVersion != VERSION) {
            model = defaultModel;
        }
        return model;
    }
}
