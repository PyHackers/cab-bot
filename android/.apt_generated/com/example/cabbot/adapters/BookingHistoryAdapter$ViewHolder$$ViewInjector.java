// Generated code from Butter Knife. Do not modify!
package com.example.cabbot.adapters;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class BookingHistoryAdapter$ViewHolder$$ViewInjector {
  public static void inject(Finder finder, final com.example.cabbot.adapters.BookingHistoryAdapter.ViewHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230828, "field 'pickupTimeText'");
    target.pickupTimeText = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131230823, "field 'bookingImage'");
    target.bookingImage = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131230821, "field 'titleText'");
    target.titleText = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131230826, "field 'pickupPlaceText'");
    target.pickupPlaceText = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131230824, "field 'eventTypeImage'");
    target.eventTypeImage = (android.widget.ImageView) view;
  }

  public static void reset(com.example.cabbot.adapters.BookingHistoryAdapter.ViewHolder target) {
    target.pickupTimeText = null;
    target.bookingImage = null;
    target.titleText = null;
    target.pickupPlaceText = null;
    target.eventTypeImage = null;
  }
}
