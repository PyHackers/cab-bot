// Generated code from Butter Knife. Do not modify!
package com.example.cabbot;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class LoginActivity$$ViewInjector {
  public static void inject(Finder finder, final com.example.cabbot.LoginActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230815, "field 'usernameTextView'");
    target.usernameTextView = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131230817, "field 'loginView' and method 'login'");
    target.loginView = view;
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.login();
        }
      });
    view = finder.findRequiredView(source, 2131230816, "field 'passwordTextView'");
    target.passwordTextView = (android.widget.TextView) view;
  }

  public static void reset(com.example.cabbot.LoginActivity target) {
    target.usernameTextView = null;
    target.loginView = null;
    target.passwordTextView = null;
  }
}
