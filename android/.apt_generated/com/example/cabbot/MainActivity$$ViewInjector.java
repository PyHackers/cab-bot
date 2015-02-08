// Generated code from Butter Knife. Do not modify!
package com.example.cabbot;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class MainActivity$$ViewInjector {
  public static void inject(Finder finder, final com.example.cabbot.MainActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230819, "field 'listView'");
    target.listView = (android.widget.ListView) view;
  }

  public static void reset(com.example.cabbot.MainActivity target) {
    target.listView = null;
  }
}
