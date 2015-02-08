// Generated code from Butter Knife. Do not modify!
package com.example.cabbot;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class BookCabActivity$$ViewInjector {
  public static void inject(Finder finder, final com.example.cabbot.BookCabActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230809, "field 'cancelView' and method 'cancelBooking'");
    target.cancelView = view;
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.cancelBooking();
        }
      });
    view = finder.findRequiredView(source, 2131230804, "field 'bookingPlaceText' and method 'changeAddress'");
    target.bookingPlaceText = (android.widget.TextView) view;
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.changeAddress();
        }
      });
    view = finder.findRequiredView(source, 2131230806, "field 'bookingTimeText'");
    target.bookingTimeText = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131230805, "field 'bookingDateText'");
    target.bookingDateText = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131230807, "field 'secondCabCheck'");
    target.secondCabCheck = (android.widget.CheckBox) view;
    view = finder.findRequiredView(source, 2131230808, "field 'remindMeLaterView' and method 'remindMeLater'");
    target.remindMeLaterView = view;
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.remindMeLater();
        }
      });
    view = finder.findRequiredView(source, 2131230810, "field 'bookView' and method 'bookCab'");
    target.bookView = view;
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.bookCab();
        }
      });
  }

  public static void reset(com.example.cabbot.BookCabActivity target) {
    target.cancelView = null;
    target.bookingPlaceText = null;
    target.bookingTimeText = null;
    target.bookingDateText = null;
    target.secondCabCheck = null;
    target.remindMeLaterView = null;
    target.bookView = null;
  }
}
