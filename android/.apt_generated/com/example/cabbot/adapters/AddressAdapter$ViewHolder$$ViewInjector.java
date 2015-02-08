// Generated code from Butter Knife. Do not modify!
package com.example.cabbot.adapters;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class AddressAdapter$ViewHolder$$ViewInjector {
  public static void inject(Finder finder, final com.example.cabbot.adapters.AddressAdapter.ViewHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230821, "field 'titleText'");
    target.titleText = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131230820, "field 'favImage'");
    target.favImage = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131230822, "field 'addressText'");
    target.addressText = (android.widget.TextView) view;
  }

  public static void reset(com.example.cabbot.adapters.AddressAdapter.ViewHolder target) {
    target.titleText = null;
    target.favImage = null;
    target.addressText = null;
  }
}
