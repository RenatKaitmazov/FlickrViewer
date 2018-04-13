package lz.renatkaitmazov.flickrviewer.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lz.renatkaitmazov.data.device.ConnectivityChecker;
import lz.renatkaitmazov.data.device.IConnectivityChecker;

/**
 * Dependencies related to the device are provided here.
 *
 * @author Renat Kaitmazov
 */
@Module
public final class DeviceModule {

  @Provides
  @Singleton
  static IConnectivityChecker provideConnectivityChecker(Context context) {
    return new ConnectivityChecker(context);
  }
}
