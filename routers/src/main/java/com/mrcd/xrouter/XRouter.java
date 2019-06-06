package com.mrcd.xrouter;

import com.mrcd.xrouter.routers.BottomTabActivityRouter;

public final class XRouter {
  public static XRouter INSTANCE = new XRouter();

  private XRouter() {
  }

  public static XRouter getInstance() {
    return INSTANCE;
  }

  public BottomTabActivityRouter bottomTabActivity() {
    return new BottomTabActivityRouter();
  }
}
