// Generated code from Butter Knife. Do not modify!
package com.example.cabbot;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class AccountActivity$$ViewInjector {
  public static void inject(Finder finder, final com.example.cabbot.AccountActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230801, "field 'usernameText'");
    target.usernameText = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131230802, "field 'slackBar'");
    target.slackBar = (android.widget.SeekBar) view;
  }

  public static void reset(com.example.cabbot.AccountActivity target) {
    target.usernameText = null;
    target.slackBar = null;
  }
}
