package com.mrcd.xrouter;

import com.mrcd.xrouter.routers.BottomTabActivityRouter;
import com.mrcd.xrouter.routers.HttpsDemoActivityRouter;
import com.mrcd.xrouter.routers.PermissionActivityRouter;

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

  public HttpsDemoActivityRouter httpsDemoActivity() {
    return new HttpsDemoActivityRouter();
  }

  public PermissionActivityRouter permissionActivity() {
    return new PermissionActivityRouter();
  }
}
